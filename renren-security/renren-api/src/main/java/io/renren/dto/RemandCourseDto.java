package io.renren.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/9/17 15:28
 */
@Data
@ApiModel(value = "剩余套餐课程实体")
public class RemandCourseDto {
    private long id; // 课程套餐类别id
    private String courseType;//课程类别名称
    private int num; // 剩余次数
}
