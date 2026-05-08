package com.lazy.express.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @descrption: EstimateDTO 运费预估入参
 * @author: lazy
 * @date: 2026-05-08 14:00
 */
@Data
public class EstimateDTO {

    @NotNull(message = "寄件人地址不能为空")
    private Long senderId;

    @NotNull(message = "收件人地址不能为空")
    private Long receiverId;

    /** pickup / station */
    @NotBlank(message = "服务类型不能为空")
    private String serviceType;
}
