package io.renren.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/8/28 16:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "订单实体")
public class OrderDto {
    private long id;
    /**
     * 订单编号
     */
    @ApiModelProperty(value = "订单编号")
    private String orderNo;
    /**
     * 订单状态 -1：未支付 0：已取消 1：已支付 2：已发货 3：（已确认）已签收
     */
    @ApiModelProperty(value = "订单状态 -1：未支付 0：已取消 1：已支付 2：已发货 3：（已确认）已签收")
    private Integer orderState;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "下单时间")
    private String createTime;
    /**
     * 订单总价
     */
    @ApiModelProperty(value = "订单总价")
    private BigDecimal totalPrice;
    /**
     * 订单支付金额
     */
    @ApiModelProperty(value = "订单支付金额")
    private BigDecimal orderPrice;
    /**
     * 订单优惠金额
     */
    @ApiModelProperty(value = "订单优惠金额")
    private BigDecimal orderDiscount;

    /**
     * 寄语
     */
    @ApiModelProperty(value = "寄语")
    private String orderRemark;
    /**
     * 派送时间/取货日期
     */
    @ApiModelProperty(value = "预计送货时间")
    private String sendTime;

    @ApiModelProperty(value = "订单详情")
    List<OrderItemDto> orderItemList;
}
