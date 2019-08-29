package io.renren.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/8/28 11:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailDto {
    private long id;
    /**
     * 所属商品ID
     */
    private long productId;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品规格图片
     */
    private String detailCover;
    /**
     * 商品规格价格
     */
    private BigDecimal detailPrice;
    /**
     * 商品尺寸
     */
    private String detailSize;
    /**
     * 商品口味
     */
    private String detailTaste;
    /**
     * 商品状态 0：下架 1：上架 2:上架但不显示
     */
    private int productFlag;
}
