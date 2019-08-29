package io.renren.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "左侧分类ID",required = true,example = "1")
    private long id;
    /**
     * 分类名称
     */
    @ApiModelProperty(value = "左侧分类名称",required = true)
    private String categoryName;
}
