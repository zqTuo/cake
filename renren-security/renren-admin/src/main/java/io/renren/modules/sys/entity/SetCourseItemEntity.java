package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 套餐课程表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-12 15:49:25
 */
@Data
@TableName("tb_set_course_item")
public class SetCourseItemEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 课程套餐ID
	 */
	private Long setCourseId;
	/**
	 * 套餐课程类别ID
	 */
	private Long typeId;
	/**
	 * 包含课程次数
	 */
	private Integer num;

}
