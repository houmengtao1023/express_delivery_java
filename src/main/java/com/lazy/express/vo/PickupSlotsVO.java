package com.lazy.express.vo;

import lombok.Data;

import java.util.List;

/**
 * @descrption: PickupSlotsVO 取件时间段
 * @author: lazy
 * @date: 2026-05-08 14:00
 */
@Data
public class PickupSlotsVO {

    /** 日期标签：今天/明天/后天 */
    private List<String> days;

    /** 与 days 一一对应的时间段列表 */
    private List<List<String>> slots;
}
