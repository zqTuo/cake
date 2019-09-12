package io.renren.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

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
@TableName("tb_set_course")
public class SetCourseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 套餐名称
	 */
	private String title;
	/**
	 * 套餐图片
	 */
	private String picUrl;
	/**
	 * 有效期 0：永久有效
	 */
	private Integer validPeriod;
	/**
	 * 套餐价格
	 */
	private BigDecimal price;

}
