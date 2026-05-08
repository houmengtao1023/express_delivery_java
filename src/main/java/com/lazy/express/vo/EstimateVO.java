package com.lazy.express.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @descrption: EstimateVO 运费预估返回
 * @author: lazy
 * @date: 2026-05-08 14:00
 */
@Data
public class EstimateVO {

    /** 预估总价 */
    private BigDecimal price;

    /** 首重价格 */
    private BigDecimal firstWeight;

    /** 服务说明 */
    private String desc;
}
