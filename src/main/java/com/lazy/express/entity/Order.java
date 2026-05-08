package com.lazy.express.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @descrption: Order 寄件订单
 * @author: lazy
 * @date: 2026-05-08 14:00
 */
@Data
public class Order {

    private Long id;

    private String no;

    private String status;

    private Long senderId;
    private String senderName;
    private String senderPhone;
    private String senderProvince;
    private String senderCity;
    private String senderDetail;

    private Long receiverId;
    private String receiverName;
    private String receiverPhone;
    private String receiverProvince;
    private String receiverCity;
    private String receiverDetail;

    /** pickup / station */
    private String serviceType;

    /** 网点类型，仅 station 模式下存在 */
    private String stationType;
    private Long stationId;
    private String stationName;
    private String stationAddress;

    /** 期望上门时间描述：今天 一小时内 */
    private String pickupTime;

    /** 寄付现结 / 寄方月结 / 到付 */
    private String payType;

    private BigDecimal price;

    private String createTime;
}
