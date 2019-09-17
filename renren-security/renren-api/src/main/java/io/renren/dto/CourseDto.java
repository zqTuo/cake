package io.renren.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/9/17 15:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "使用套餐结算实体")
public class CourseDto {
    @ApiModelProperty(value = "课程ID")
    private long id;

    @ApiModelProperty(value = "课程名称")
    private String title;

    @ApiModelProperty(value = "套餐课程类别")
    private String type;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "消耗次数说明")
    private String cost;

    @ApiModelProperty(value = "套餐课程剩余")
    private List<RemandCourseDto> remandList;
}
