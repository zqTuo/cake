package io.renren.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/8/28 11:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "订单下单返回实体")
public class OrderFormDto {
    @ApiModelProperty(value = "订单状态 -1:未支付 1：抵扣成功，无需支付(支付成功)",required = true)
    private int orderState;
    @ApiModelProperty(value = "订单编号",required = true)
    private String orderNo;
}
