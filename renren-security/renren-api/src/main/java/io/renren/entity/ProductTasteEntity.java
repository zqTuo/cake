package io.renren.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品口味表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-29 05:38:16
 */
@Data
@TableName("tb_product_taste")
@ApiModel(value = "商品口味实体")
public class ProductTasteEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	@ApiModelProperty(value = "口味ID")
	private Long id;
	/**
	 * 标题
	 */
	@ApiModelProperty(value = "口味标题")
	private String title;
	/**
	 * 商品ID
	 */
	@ApiModelProperty(value = "商品ID")
	private Long productId;
}
