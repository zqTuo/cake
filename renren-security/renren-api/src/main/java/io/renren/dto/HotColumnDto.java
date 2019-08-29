package io.renren.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 功能描述: <br>
 * 首页热销栏目
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/8/21 17:18
 */
@ApiModel(value = "热销栏目实体")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotColumnDto {
    @ApiModelProperty(hidden = true,example = "1")
    private long id;
    @ApiModelProperty(value = "热销栏目title",required = true)
    private String hotTitle;
    @ApiModelProperty(value = "栏目包含商品列表")
    private List<IndexProductDto> productList;
}
