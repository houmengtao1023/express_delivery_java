package com.lazy.express.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @descrption: CreateOrderDTO 下单入参
 * @author: lazy
 * @date: 2026-05-08 14:00
 */
@Data
public class CreateOrderDTO {

    @NotNull(message = "寄件人地址不能为空")
    private Long senderId;

    @NotNull(message = "收件人地址不能为空")
    private Long receiverId;

    @NotBlank(message = "服务类型不能为空")
    private String serviceType;

    /** 服务点自寄时必填 */
    private String stationType;

    private Long stationId;

    /** 上门取件时必填，例：今天 一小时内 */
    private String pickupTime;

    @NotBlank(message = "付款方式不能为空")
    private String payType;
}
