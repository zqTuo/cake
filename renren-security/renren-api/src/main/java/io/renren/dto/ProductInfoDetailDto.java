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
 * @Date: 2019/8/29 6:15
 */
@Data
@ApiModel(value = "商品详情信息实体")
@AllArgsConstructor
@NoArgsConstructor
public class ProductInfoDetailDto {
    /**
     * 商品详情ID
     */
    @ApiModelProperty(value = "商品详情ID")
    private long id;

    @ApiModelProperty(value = "商品ID")
    private long productId;

    /**
     * 商品规格图片
     */
    @ApiModelProperty(value = "商品规格图片")
    private String detailCover;
    /**
     * 商品规格名称
     */
    @ApiModelProperty(value = "商品规格名称")
    private String detailName;
    /**
     * 商品规格价格
     */
    @ApiModelProperty(value = "商品规格价格")
    private BigDecimal detailPrice;
    /**
     * 尺寸ID
     */
    @ApiModelProperty(value = "尺寸ID")
    private long sizeId;
    /**
     * 口味ID
     */
    @ApiModelProperty(value = "口味ID")
    private long tasteId;
}
