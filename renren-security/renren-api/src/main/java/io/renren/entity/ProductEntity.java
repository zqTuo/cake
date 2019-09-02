package io.renren.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-25 22:57:11
 */
@Data
@TableName("tb_product")
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
	 * 商品规格价格区间
	 */
	private String productPriceRegion;
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
	 * 商品视频
	 */
	private String productVideo;
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
	 * 商品状态 0：下架 1：上架 2:上架但不显示
	 */
	private Integer productFlag;
	/**
	 * 热销标记(展示在首页) 0：不展示 1：展示栏目ID
	 */
	private Long productHotId;
	/**
	 * 加购标记 0：不设为加购 1：设为加购
	 */
	private Integer productExtra;
	/**
	 * 热门标记 0：不推荐 1：推荐
	 */
	private Integer productHotFlag;
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
