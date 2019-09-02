package io.renren.modules.sys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/8/29 6:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private long id;
    private String productName;
    private String categoryName;
    private BigDecimal productPrice;
    private String productPriceRegion;
    private BigDecimal productOrigin;
    private String productSku;
    private String productImg;
    private Integer productFlag;
    private Long productHotId;
    private Integer productExtra;
    private Long shopId;
    /**
     * 门店名称
     */
    private String shopName;
}
