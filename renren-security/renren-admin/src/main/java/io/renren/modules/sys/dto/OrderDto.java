package io.renren.modules.sys.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/9/5 9:47
 */
@Data
public class OrderDto {
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 订单支付金额
     */
    private BigDecimal orderPrice;
    /**
     * 订单优惠金额
     */
    private BigDecimal orderDiscount;
    /**
     * 订单优惠类型 0：无优惠 1：优惠券  2：美团券 3：优惠券+美团
     */
    private Integer orderDiscountType;
}
