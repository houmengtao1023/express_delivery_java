package com.lazy.express.controller;

import com.lazy.express.common.R;
import com.lazy.express.dto.CreateOrderDTO;
import com.lazy.express.dto.EstimateDTO;
import com.lazy.express.entity.Order;
import com.lazy.express.service.OrderService;
import com.lazy.express.vo.EstimateVO;
import com.lazy.express.vo.PickupSlotsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @descrption: OrderController 寄件订单接口
 * @author: lazy
 * @date: 2026-05-08 14:00
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/pickup-slots")
    public R<PickupSlotsVO> pickupSlots() {
        return R.ok(orderService.pickupSlots());
    }

    @PostMapping("/estimate")
    public R<EstimateVO> estimate(@RequestBody @Valid EstimateDTO dto) {
        return R.ok(orderService.estimate(dto));
    }

    @PostMapping
    public R<Order> create(@RequestBody @Valid CreateOrderDTO dto) {
        return R.ok(orderService.create(dto));
    }

    @GetMapping("/list")
    public R<List<Order>> list() {
        return R.ok(orderService.list());
    }

    @GetMapping("/{id}")
    public R<Order> detail(@PathVariable Long id) {
        return R.ok(orderService.detail(id));
    }
}
