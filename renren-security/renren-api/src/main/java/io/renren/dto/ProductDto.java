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
 * @Date: 2019/8/21 19:27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "商品数据实体")
public class ProductDto {
    /**
     * 商品ID
     */
    @ApiModelProperty(value = "商品ID",required = true,example = "1")
    private long id;
    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称",required = true)
    private String productName;
    /**
     * 商品描述
     */
    @ApiModelProperty(value = "商品描述",required = true)
    private String productDesc;

    /**
     * 商品售价
     */
    @ApiModelProperty(value = "商品售价",required = true)
    private BigDecimal productPrice;

    /**
     * 商品主图
     */
    @ApiModelProperty(value = "商品主图",required = true)
    private String productImg;
    /**
     * 商品视频
     */
    @ApiModelProperty(value = "商品视频，若有需要放在副图之前展示")
    private String productVideo;
    /**
     * 商品规格价格区间
     */
    @ApiModelProperty(value = "商品规格价格区间",required = true)
    private String productPriceRegion;
    /**
     * 商品详情HTML代码
     */
    @ApiModelProperty(value = "商品详情html代码",required = true)
    private String productInfo;

    @ApiModelProperty(value = "商品详情ID")
    private long productDetailId;

    @ApiModelProperty(value = "商品规格")
    private String detailType;

    @ApiModelProperty(value = "购买数量")
    private int buyNum;
}
