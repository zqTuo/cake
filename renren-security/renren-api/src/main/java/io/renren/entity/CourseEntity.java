package io.renren.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 蛋糕课程表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-09 18:28:51
 */
@Data
@TableName("tb_course")
@ApiModel(value = "课程实体")
public class CourseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	@ApiModelProperty(value = "课程ID")
	private Long id;
	/**
	 * 课程名称
	 */
	@ApiModelProperty(value = "课程名称")
	@NotBlank(message = "请填写课程名称")
	private String title;
	/**
	 * 分类id
	 */
	@Min(value = 1,message = "请选择分类")
	private Long cateId;
	/**
	 * 主图图片
	 */
	@ApiModelProperty(value = "课程主图")
	@NotBlank(message = "请选择主图图片")
	private String courseImg;
	/**
	 * 副图图片
	 */
	@ApiModelProperty(value = "课程副图图片")
	@NotBlank(message = "请选择副图图片")
	private String courseBanner;
	/**
	 * 视频
	 */
	private String courseVideo;
	/**
	 * 售价
	 */
	@Min(value = 1,message = "请填写售价")
	private BigDecimal price;
	/**
	 * 课程简介
	 */
	@NotBlank(message = "请填写课程简介")
	private String courseDes;
	/**
	 * 课程详情HTML代码
	 */
	@NotBlank(message = "请填写课程详情介绍")
	private String courseInfo;

}
