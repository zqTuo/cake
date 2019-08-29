package io.renren.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/8/27 22:02
 */
@Data
@ApiModel(value = "结算金额实体")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayMoneyDto {
    @ApiModelProperty(value = "支付总金额",required = true)
    private BigDecimal totalPrice;
    @ApiModelProperty(value = "运费",required = true)
    private BigDecimal freight;
    @ApiModelProperty(value = "订单金额",required = true)
    private BigDecimal price;
    @ApiModelProperty(value = "优惠金额",required = true)
    private BigDecimal discount;
}
