package io.renren.form;

import io.renren.entity.CourseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/9/16 18:11
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseDetailForm {
    @ApiModelProperty(value = "课程实体")
    private CourseEntity course;

    @ApiModelProperty(value = "副图数组")
    private String[] bannerArr;
}
