package io.renren.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/8/21 19:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "商品分类实体")
public class CategoryDto {
    @ApiModelProperty(hidden = true,example = "1")
    private long id;
    /**
     * 大分类名称
     */
    @ApiModelProperty(value = "顶部大分类菜单名称")
    private String parentName;
    /**
     * 子类列表
     */
    @ApiModelProperty(value = "左侧子菜单列表")
    private List<ProductCateDto> categoryList;
}
