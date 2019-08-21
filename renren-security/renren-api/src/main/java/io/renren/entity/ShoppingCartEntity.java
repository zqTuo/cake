package io.renren.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

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
public class ShoppingCartEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 商品ID
	 */
	private Long productId;
	/**
	 * 商品详情ID
	 */
	private Long productDetailId;
	/**
	 * 商品名称
	 */
	private String productName;
	/**
	 * 商品规格图片
	 */
	private String detailCover;
	/**
	 * 商品规格名称
	 */
	private String detailName;
	/**
	 * 商品规格价格
	 */
	private BigDecimal detailPrice;
	/**
	 * 商品尺寸
	 */
	private String detailSize;
	/**
	 * 商品口味
	 */
	private String detailTaste;
	/**
	 * 购买数量
	 */
	private Integer buyNum;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;

}
