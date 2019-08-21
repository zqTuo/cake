package io.renren.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/8/21 19:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "商品分类实体")
public class ProductCateDto {
    private long id;
    /**
     * 分类名称
     */
    private String categoryName;
}
