package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.io.Serializable;
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
	@NotBlank(message = "请填写商品名称")
	private String productName;
	/**
	 * 商品类型ID
	 */
	@Min(value = 1,message = "请选择商品类型")
	private Long productCateid;
	/**
	 * 商品售价
	 */
	@Min(value = 0,message = "请填写商品价格")
	private BigDecimal productPrice;
	/**
	 * 商品规格价格区间
	 */
	@NotBlank(message = "请填写商品价格区间")
	private String productPriceRegion;
	/**
	 * 商品原价
	 */
	@Min(value = 0,message = "请填写商品原价")
	private BigDecimal productOrigin;
	/**
	 * 商品SKU，主要是为了匹配美团套餐
	 */
	private String productSku;
	/**
	 * 商品主图
	 */
	@NotBlank(message = "请选择商品主图")
	private String productImg;
	/**
	 * 商品视频
	 */
	private String productVideo;
	/**
	 * 商品副图
	 */
	@NotBlank(message = "请选择商品副图")
	private String productBanner;
	/**
	 * 商品描述
	 */
	@NotBlank(message = "请填写商品描述")
	private String productDesc;
	/**
	 * 商品详情HTML代码
	 */
	@NotBlank(message = "请填写商品详情")
	private String productInfo;
	/**
	 * 商品状态 0：下架 1：上架 2:上架但不显示
	 */
	@Min(value = 0,message = "请选择商品状态")
	private Integer productFlag;
	/**
	 * 热销标记(展示在首页) 0：不推荐 1：推荐
	 */
	@Min(value = 0,message = "请选择商品热销标记状态")
	private Integer productHot;
	/**
	 * 加购标记 0：不设为加购 1：设为加购
	 */
	@Min(value = 0,message = "请选择商品加购标记状态")
	private Integer productExtra;
	/**
	 * 所属门店
	 */
	@Min(value = 0,message = "请选择商品所属门店")
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
