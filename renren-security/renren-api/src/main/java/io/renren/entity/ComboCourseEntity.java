package io.renren.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 课程套餐表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-12 15:49:25
 */
@Data
@ApiModel(value = "套餐实体")
@TableName("tb_combo_course")
public class ComboCourseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	@ApiModelProperty(value = "课程套餐ID")
	private Long id;
	/**
	 * 套餐名称
	 */
	@ApiModelProperty(value = "套餐名称")
	private String title;
	/**
	 * 套餐图片
	 */
	@ApiModelProperty(value = "套餐图片")
	private String picUrl;
	/**
	 * 有效期 0：永久有效
	 */
	@ApiModelProperty(value = "有效期 单位：月，若值为0，代表无期限")
	private Integer validPeriod;
	/**
	 * 套餐价格
	 */
	@ApiModelProperty(value = "套餐价格")
	private BigDecimal price;
	/**
	 * 套餐描述
	 */
	@ApiModelProperty(value = "套餐内容")
	private String remark;
}
