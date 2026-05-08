package com.lazy.express.service;

import com.lazy.express.common.BizException;
import com.lazy.express.dto.AddressDTO;
import com.lazy.express.entity.Address;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * @descrption: AddressService 地址簿服务（内存存储）
 * @author: lazy
 * @date: 2026-05-08 14:00
 */
@Service
public class AddressService {

    private final Map<Long, Address> store = new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong(0);

    @PostConstruct
    public void init() {
        Address a1 = new Address();
        a1.setId(idGen.incrementAndGet());
        a1.setName("侯梦涛");
        a1.setPhone("13100005920");
        a1.setProvince("河北省");
        a1.setCity("保定市唐县");
        a1.setDetail("王京镇西沿村");
        a1.setTag("家");
        a1.setIsDefault(true);
        store.put(a1.getId(), a1);

        Address a2 = new Address();
        a2.setId(idGen.incrementAndGet());
        a2.setName("侯梦涛");
        a2.setPhone("15122226268");
        a2.setProvince("北京");
        a2.setCity("朝阳区");
        a2.setDetail("东湖街道叶青大厦D座4层");
        a2.setTag("公司");
        a2.setIsDefault(false);
        store.put(a2.getId(), a2);
    }

    public List<Address> list(String keyword) {
        List<Address> all = new ArrayList<>(store.values());
        all.sort(Comparator.comparing(Address::getId));
        if (keyword == null || keyword.isEmpty()) {
            return all;
        }
        String k = keyword.toLowerCase();
        return all.stream()
                .filter(a -> matchKeyword(a, k))
                .collect(Collectors.toList());
    }

    public Address getById(Long id) {
        Address a = store.get(id);
        if (a == null) {
            throw new BizException("地址不存在");
        }
        return a;
    }

    public Address save(AddressDTO dto) {
        Address entity;
        if (dto.getId() != null && store.containsKey(dto.getId())) {
            entity = store.get(dto.getId());
            BeanUtils.copyProperties(dto, entity, "id");
        } else {
            entity = new Address();
            BeanUtils.copyProperties(dto, entity);
            entity.setId(idGen.incrementAndGet());
            store.put(entity.getId(), entity);
        }
        if (Boolean.TRUE.equals(entity.getIsDefault())) {
            store.values().forEach(a -> {
                if (!a.getId().equals(entity.getId())) {
                    a.setIsDefault(false);
                }
            });
        }
        return entity;
    }

    public void remove(Long id) {
        Address removed = store.remove(id);
        if (removed == null) {
            throw new BizException("地址不存在");
        }
        if (Boolean.TRUE.equals(removed.getIsDefault())) {
            store.values().stream()
                    .min(Comparator.comparing(Address::getId))
                    .ifPresent(a -> a.setIsDefault(true));
        }
    }

    public void setDefault(Long id) {
        if (!store.containsKey(id)) {
            throw new BizException("地址不存在");
        }
        store.values().forEach(a -> a.setIsDefault(a.getId().equals(id)));
    }

    private boolean matchKeyword(Address a, String key) {
        StringBuilder sb = new StringBuilder();
        sb.append(safe(a.getName())).append(safe(a.getPhone()))
                .append(safe(a.getProvince())).append(safe(a.getCity()))
                .append(safe(a.getDetail())).append(safe(a.getTag()));
        return sb.toString().toLowerCase().contains(key);
    }

    private String safe(String s) {
        return s == null ? "" : s;
    }
}
