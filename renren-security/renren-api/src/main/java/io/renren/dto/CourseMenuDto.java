package io.renren.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/9/17 11:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseMenuDto {
    @ApiModelProperty(value = "课程菜单ID")
    private long id;
    @ApiModelProperty(value = "开始时间",required = true)
    private String startTime; // 派送开始时间 10:00
    @ApiModelProperty(value = "结束时间",required = true)
    private String endTime; // 派送截止时间 11:00

    /**
     * 课程ID
     */
    @ApiModelProperty(value = "课程ID")
    private long courseId;
    @ApiModelProperty(value = "课程名称")
    private String title;
    @ApiModelProperty(value = "最大人数")
    private int num;
    @ApiModelProperty(value = "是否可以预订 0：已约满 1：暂无课程 2：可以预约")
    private int state;

}
