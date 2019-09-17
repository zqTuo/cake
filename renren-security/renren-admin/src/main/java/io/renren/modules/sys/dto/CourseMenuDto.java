package io.renren.modules.sys.dto;

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
    private long id;
    private String courseTime;
    /**
     * 时间ID
     */
    private Long sendTimeId;
    /**
     * 课程ID
     */
    private Long courseId;
    private String title;
    private int num;
    /**
     * 课程当前日期
     */
    private Date menuDate;
    /**
     * 消耗课程类别次数
     */
    private int cost;
}
