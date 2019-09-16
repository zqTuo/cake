package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 课程套餐表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-12 15:49:25
 */
@Data
@TableName("tb_combo_course")
public class ComboCourseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 套餐名称
	 */
	@NotBlank(message = "请填写套餐名称")
	private String title;
	/**
	 * 套餐图片
	 */
	@NotBlank(message = "请选择套餐图片")
	private String picUrl;
	/**
	 * 有效期 0：永久有效
	 */
	@Min(value = 0,message = "请选择有效时长")
	private Integer validPeriod;
	/**
	 * 套餐价格
	 */
	@Min(value = 0,message = "请填写套餐价格")
	private BigDecimal price;
	/**
	 * 套餐描述
	 */
	@NotBlank(message = "请填写套餐描述")
	private String remark;

}
