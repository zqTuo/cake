package io.renren.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品详情表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 21:51:22
 */
@Data
@TableName("tb_product_detail")
public class ProductDetailEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 商品规格图片
	 */
	private String detailCover;
	/**
	 * 商品规格名称
	 */
	private String detailName;
	/**
	 * 所属商品ID
	 */
	private Long productId;
	/**
	 * 商品规格价格
	 */
	private BigDecimal detailPrice;
	/**
	 * 商品尺寸
	 * size：尺寸  price：额外加价  flag：是否默认选项 0：否 1：是
	 * {"size":"6寸","extraPrice":"20","flag":1}
	 */
	private String detailSize;
	/**
	 * 尺寸ID
	 */
	private Long sizeId;
	/**
	 * 商品口味
	 * {"taste":"标准口味","extraPrice":"20","flag":1}
	 */
	private String detailTaste;
	/**
	 * 口味ID
	 */
	private Long tasteId;
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
