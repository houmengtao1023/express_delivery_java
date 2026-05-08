package com.lazy.express.service;

import com.lazy.express.common.BizException;
import com.lazy.express.dto.CreateOrderDTO;
import com.lazy.express.dto.EstimateDTO;
import com.lazy.express.entity.Address;
import com.lazy.express.entity.Order;
import com.lazy.express.entity.Station;
import com.lazy.express.vo.EstimateVO;
import com.lazy.express.vo.PickupSlotsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * @descrption: OrderService 订单服务（运费预估/时间槽/下单/列表）
 * @author: lazy
 * @date: 2026-05-08 14:00
 */
@Service
public class OrderService {

    private static final DateTimeFormatter DT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private static final BigDecimal PICKUP_BASE = new BigDecimal("12.00");
    private static final BigDecimal STATION_BASE = new BigDecimal("9.00");

    private final Map<Long, Order> store = new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong(0);

    @Autowired
    private AddressService addressService;

    @Autowired
    private StationService stationService;

    public PickupSlotsVO pickupSlots() {
        PickupSlotsVO vo = new PickupSlotsVO();
        List<String> days = Arrays.asList("今天", "明天", "后天");
        List<List<String>> slots = new ArrayList<>();

        List<String> today = new ArrayList<>();
        today.add("一小时内");
        today.addAll(buildHourSlots(12, 21));
        slots.add(today);
        slots.add(buildHourSlots(9, 21));
        slots.add(buildHourSlots(9, 21));

        vo.setDays(days);
        vo.setSlots(slots);
        return vo;
    }

    public EstimateVO estimate(EstimateDTO dto) {
        addressService.getById(dto.getSenderId());
        addressService.getById(dto.getReceiverId());
        BigDecimal base = "station".equals(dto.getServiceType()) ? STATION_BASE : PICKUP_BASE;

        EstimateVO vo = new EstimateVO();
        vo.setPrice(base);
        vo.setFirstWeight(base);
        vo.setDesc("首重1kg，续重每kg加收5元（演示价格）");
        return vo;
    }

    public Order create(CreateOrderDTO dto) {
        Address sender = addressService.getById(dto.getSenderId());
        Address receiver = addressService.getById(dto.getReceiverId());

        if ("pickup".equals(dto.getServiceType())) {
            if (dto.getPickupTime() == null || dto.getPickupTime().isEmpty()) {
                throw new BizException("请选择上门取件时间");
            }
        } else if ("station".equals(dto.getServiceType())) {
            if (dto.getStationType() == null || dto.getStationType().isEmpty()) {
                throw new BizException("请选择网点类型");
            }
        } else {
            throw new BizException("不支持的服务类型");
        }

        Order order = new Order();
        order.setId(idGen.incrementAndGet());
        order.setNo(buildOrderNo());
        order.setStatus("待揽收");

        order.setSenderId(sender.getId());
        order.setSenderName(sender.getName());
        order.setSenderPhone(sender.getPhone());
        order.setSenderProvince(sender.getProvince());
        order.setSenderCity(sender.getCity());
        order.setSenderDetail(sender.getDetail());

        order.setReceiverId(receiver.getId());
        order.setReceiverName(receiver.getName());
        order.setReceiverPhone(receiver.getPhone());
        order.setReceiverProvince(receiver.getProvince());
        order.setReceiverCity(receiver.getCity());
        order.setReceiverDetail(receiver.getDetail());

        order.setServiceType(dto.getServiceType());
        order.setPayType(dto.getPayType());

        if ("pickup".equals(dto.getServiceType())) {
            order.setPickupTime(dto.getPickupTime());
        } else {
            order.setStationType(dto.getStationType());
            Station station = resolveStation(dto.getStationId(), dto.getStationType());
            if (station != null) {
                order.setStationId(station.getId());
                order.setStationName(station.getName());
                order.setStationAddress(station.getAddress());
            }
        }

        EstimateDTO est = new EstimateDTO();
        est.setSenderId(dto.getSenderId());
        est.setReceiverId(dto.getReceiverId());
        est.setServiceType(dto.getServiceType());
        order.setPrice(estimate(est).getPrice());

        order.setCreateTime(LocalDateTime.now().format(DT_FORMATTER));
        store.put(order.getId(), order);
        return order;
    }

    public List<Order> list() {
        return store.values().stream()
                .sorted(Comparator.comparing(Order::getId).reversed())
                .collect(Collectors.toList());
    }

    public Order detail(Long id) {
        Order order = store.get(id);
        if (order == null) {
            throw new BizException("订单不存在");
        }
        return order;
    }

    private List<String> buildHourSlots(int start, int end) {
        List<String> list = new ArrayList<>();
        for (int hour = start; hour < end; hour++) {
            list.add(String.format("%02d:00-%02d:00", hour, hour + 1));
        }
        return list;
    }

    private Station resolveStation(Long stationId, String stationType) {
        if (stationId != null) {
            return stationService.listByType(null).stream()
                    .filter(s -> stationId.equals(s.getId()))
                    .findFirst()
                    .orElse(stationService.nearest(stationType));
        }
        return stationService.nearest(stationType);
    }

    private String buildOrderNo() {
        long ts = System.currentTimeMillis();
        String tail = String.valueOf(ts);
        return "JDK" + tail.substring(Math.max(0, tail.length() - 12));
    }
}
