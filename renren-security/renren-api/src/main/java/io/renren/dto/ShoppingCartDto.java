package io.renren.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/8/27 18:21
 */
@ApiModel(value = "购物车统计实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartDto {
    @ApiModelProperty(value = "购物车总金额",required = true)
    private BigDecimal totalPrice;
    @ApiModelProperty(value = "购物车中商品总数",required = true)
    private int num;
}
