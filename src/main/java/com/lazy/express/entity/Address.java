package com.lazy.express.entity;

import lombok.Data;

/**
 * @descrption: Address 地址簿实体
 * @author: lazy
 * @date: 2026-05-08 14:00
 */
@Data
public class Address {

    private Long id;

    private String name;

    private String phone;

    private String province;

    private String city;

    private String detail;

    private String tag;

    private Boolean isDefault;
}
