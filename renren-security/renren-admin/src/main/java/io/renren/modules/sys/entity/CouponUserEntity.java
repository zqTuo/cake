package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 优惠券用户表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-19 16:35:41
 */
@Data
@TableName("coupon_user")
public class CouponUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 优惠券ID
	 */
	private Long couponId;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 获得来源 0：商城后台赠送
	 */
	private Integer sourceFrom;
	/**
	 * 获得时间
	 */
	private Date getTime;
	/**
	 * 使用时间
	 */
	private Date useTime;
	/**
	 * 截止时间
	 */
	private Date endTime;
	/**
	 * 使用状态 1：可用 0：不可用
	 */
	private Integer state;

}
