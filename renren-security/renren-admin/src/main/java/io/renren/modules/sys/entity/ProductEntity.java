package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 商品表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-19 16:35:41
 */
@Data
@TableName("product")
public class ProductEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 商品名称
	 */
	private String productName;
	/**
	 * 商品类型ID
	 */
	private Long productCateid;
	/**
	 * 商品售价
	 */
	private BigDecimal productPrice;
	/**
	 * 商品原价
	 */
	private BigDecimal productOrigin;
	/**
	 * 商品SKU，主要是为了匹配美团套餐
	 */
	private String productSku;
	/**
	 * 商品主图
	 */
	private String productImg;
	/**
	 * 商品副图
	 */
	private String productBanner;
	/**
	 * 商品描述
	 */
	private String productDesc;
	/**
	 * 商品详情HTML代码
	 */
	private String productInfo;
	/**
	 * 商品状态 0：下架 1：上架
	 */
	private Integer productFlag;
	/**
	 * 热销标记(展示在首页) 0：不推荐 1：推荐
	 */
	private Integer productHot;
	/**
	 * 所属门店
	 */
	private Long shopId;
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
