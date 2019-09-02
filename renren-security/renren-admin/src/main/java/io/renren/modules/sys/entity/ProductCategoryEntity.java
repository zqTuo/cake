package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * 商品种类表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 16:22:06
 */
@Data
@TableName("tb_product_category")
public class ProductCategoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 分类名称
	 */
	@NotBlank(message = "请填写分类名称")
	private String categoryName;
	/**
	 * 父类ID
	 */
	@Min(value = 0,message = "请选择父类")
	private Long categoryParentid;
	/**
	 * 分类状态 0：禁用 1：启用
	 */
	@Min(value = 0,message = "请选择分类状态")
	private Integer categoryFlag;
	/**
	 * 首页是否展示 0：不展示 1：展示
	 */
	@Min(value = 0,message = "请选择首页是否展示")
	private Integer showFlag;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 修改管理员
	 */
	private String updateBy;

}
