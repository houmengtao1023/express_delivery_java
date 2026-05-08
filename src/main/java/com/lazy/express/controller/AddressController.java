package com.lazy.express.controller;

import com.lazy.express.common.R;
import com.lazy.express.dto.AddressDTO;
import com.lazy.express.entity.Address;
import com.lazy.express.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @descrption: AddressController 地址簿接口
 * @author: lazy
 * @date: 2026-05-08 14:00
 */
@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/list")
    public R<List<Address>> list(@RequestParam(required = false) String keyword) {
        return R.ok(addressService.list(keyword));
    }

    @GetMapping("/{id}")
    public R<Address> get(@PathVariable Long id) {
        return R.ok(addressService.getById(id));
    }

    @PostMapping
    public R<Address> save(@RequestBody @Valid AddressDTO dto) {
        return R.ok(addressService.save(dto));
    }

    @PutMapping("/{id}/default")
    public R<Void> setDefault(@PathVariable Long id) {
        addressService.setDefault(id);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> remove(@PathVariable Long id) {
        addressService.remove(id);
        return R.ok();
    }
}
