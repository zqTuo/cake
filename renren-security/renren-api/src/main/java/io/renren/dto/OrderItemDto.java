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
 * @Date: 2019/8/28 23:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "订单详情")
public class OrderItemDto {
    private long id;
    /**
     * 商品规格图片
     */
    @ApiModelProperty(value = "商品规格图片")
    private String detailCover;
    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String productName;

    @ApiModelProperty(value = "商品ID")
    private long productId;

    /**
     * 商品规格价格
     */
    @ApiModelProperty(value = "商品规格价格")
    private BigDecimal detailPrice;

    /**
     * 购买数量
     */
    @ApiModelProperty(value = "购买数量")
    private int buyNum;
}
