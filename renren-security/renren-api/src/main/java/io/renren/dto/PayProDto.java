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
 * @Date: 2019/8/28 19:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "结算商品实体")
@Builder
public class PayProDto {
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
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String productName;
    /**
     * 商品规格价格
     */
    @ApiModelProperty(value = "商品规格价格")
    private BigDecimal detailPrice;
    /**
     * 商品尺寸
     * size：尺寸  price：额外加价  flag：是否默认选项 0：否 1：是
     * {"size":"6寸","extraPrice":"20","flag":1}
     */
    @ApiModelProperty(value = "商品尺寸")
    private String detailSize;
    /**
     * 商品口味
     * {"taste":"标准口味","extraPrice":"20","flag":1}
     */
    @ApiModelProperty(value = "商品口味")
    private String detailTaste;

    @ApiModelProperty(value = "购买数量")
    private int buyNum;

    @ApiModelProperty(value = "商品详情ID")
    private long productDetailId;

    @ApiModelProperty(value = "寄语")
    private String remark;
}
