package io.renren.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单详情表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 16:22:06
 */
@Data
@TableName("tb_shop_order_item")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShopOrderItemEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 订单编号
	 */
	private String orderNo;
	/**
	 * 商品名称
	 */
	private String productName;
	/**
	 * 商品描述
	 */
	private String productDesc;
	/**
	 * 商品ID
	 */
	private Long productId;
	/**
	 * 商品规格图片
	 */
	private String detailCover;
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
	 * 购买会员等级
	 */
	private Integer userMember;
	/**
	 * 购买课程ID
	 */
	private Long courseId;

	/**
	 * 课程套餐ID
	 */
	private Long comboCourseId;
	/**
	 * 课程套餐截止日期
	 */
	private Date expiredDate;
}
