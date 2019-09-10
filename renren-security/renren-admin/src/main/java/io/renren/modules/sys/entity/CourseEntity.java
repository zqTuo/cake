package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 蛋糕课程表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-09 18:28:51
 */
@Data
@TableName("tb_course")
public class CourseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 课程名称
	 */
	private String title;
	/**
	 * 分类id
	 */
	private Long cateId;
	/**
	 * 主图图片
	 */
	private String courseImg;
	/**
	 * 副图图片
	 */
	private String courseBanner;
	/**
	 * 视频
	 */
	private String courseVideo;
	/**
	 * 售价
	 */
	private BigDecimal price;
	/**
	 * 课程简介
	 */
	private String courseDes;
	/**
	 * 课程详情HTML代码
	 */
	private String courseInfo;

}
