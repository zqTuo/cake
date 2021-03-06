package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.io.Serializable;
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
	@NotBlank(message = "商品规格图片不能为空")
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
	 * 规格SKU
	 */
	private String detailSku;
	/**
	 * 商品规格价格
	 */
	private BigDecimal detailPrice;
	/**
	 * 商品尺寸
	 */
	private String detailSize;
	/**
	 * 尺寸ID
	 */
	private Long sizeId;
	/**
	 * 商品口味
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
