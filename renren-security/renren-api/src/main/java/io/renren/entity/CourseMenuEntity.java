package io.renren.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 课程菜单表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-17 09:35:40
 */
@Data
@TableName("tb_course_menu")
public class CourseMenuEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 时间ID
	 */
	private Long sendTimeId;
	/**
	 * 课程ID
	 */
	private Long courseId;
	/**
	 * 最大人数
	 */
	private Integer num;
	/**
	 * 课程当前日期
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date menuDate;
	/**
	 * 消耗课程类别次数
	 */
	private Integer cost;
}
