package io.renren.modules.sys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/9/18 20:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
    private long id;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品尺寸
     */
    private String detailSize;
    /**
     * 商品口味
     */
    private String detailTaste;
    /**
     * 购买数量
     */
    private int buyNum;
}
