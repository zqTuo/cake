package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 优惠券表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-25 23:30:01
 */
@Data
@TableName("tb_coupon")
public class CouponEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 优惠券名称
	 */
	private String couponName;
	/**
	 * 触发价格
	 */
	private BigDecimal couponPrice;
	/**
	 * 优惠金额
	 */
	private BigDecimal price;
	/**
	 * 优惠券类型 0：商品优惠券 1：课程优惠券
	 */
	private Integer couponType;
	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 截止时间
	 */
	private Date endTime;
	/**
	 * 是否限时 0：不限时 1:限时
	 */
	private Integer dateFlag;
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
