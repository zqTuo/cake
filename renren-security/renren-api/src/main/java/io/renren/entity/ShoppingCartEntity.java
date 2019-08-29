package io.renren.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 购物车表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 16:22:06
 */
@Data
@TableName("tb_shopping_cart")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class ShoppingCartEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	@ApiModelProperty(value = "购物车ID",required = true,example = "1")
	private Long id;
	/**
	 * 用户id
	 */
	@ApiModelProperty(hidden = true)
	private Long userId;
	/**
	 * 商品ID
	 */
	@ApiModelProperty(value = "商品ID",required = true,example = "1")
	private Long productId;
	/**
	 * 商品详情ID
	 */
	@ApiModelProperty(value = "商品详情ID",required = true,example = "1")
	private Long productDetailId;
	/**
	 * 商品名称
	 */
	@ApiModelProperty(value = "商品名称",required = true)
	private String productName;
	/**
	 * 商品规格图片
	 */
	@ApiModelProperty(value = "商品规格图片",required = true)
	private String detailCover;
	/**
	 * 商品规格名称
	 */
	@ApiModelProperty(value = "商品规格名称",required = true)
	private String detailName;
	/**
	 * 商品规格价格
	 */
	@ApiModelProperty(value = "商品规格价格",required = true)
	private BigDecimal detailPrice;
	/**
	 * 商品尺寸
	 */
	@ApiModelProperty(value = "商品尺寸",required = true)
	private String detailSize;
	/**
	 * 商品口味
	 */
	@ApiModelProperty(value = "商品口味",required = true)
	private String detailTaste;
	/**
	 * 购买数量
	 */
	@ApiModelProperty(value = "购买数量",required = true)
	private Integer buyNum;

	@ApiModelProperty(value = "寄语")
	private String remark;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	/**
	 * 修改时间
	 */
	@ApiModelProperty(hidden = true)
	private Date updateTime;

}
