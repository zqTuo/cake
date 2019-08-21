package io.renren.dto;

import io.swagger.annotations.ApiModel;
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
    private long id;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品描述
     */
    private String productDesc;

    /**
     * 商品售价
     */
    private BigDecimal productPrice;

    /**
     * 商品主图
     */
    private String productImg;
}
