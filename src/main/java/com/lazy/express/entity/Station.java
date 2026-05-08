package com.lazy.express.entity;

import lombok.Data;

/**
 * @descrption: Station 服务网点
 * @author: lazy
 * @date: 2026-05-08 14:00
 */
@Data
public class Station {

    private Long id;

    /** 网点类型：便民站 / 营业部 */
    private String type;

    private String name;

    /** 距离描述，如 777m */
    private String distance;

    private String address;
}
