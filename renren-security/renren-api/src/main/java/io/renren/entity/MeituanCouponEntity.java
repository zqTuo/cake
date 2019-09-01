package io.renren.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 美团券验券记录表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-01 14:26:01
 */
@Data
@TableName("tb_meituan_coupon")
@Builder
public class MeituanCouponEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 美团点评券码
	 */
	private String code;
	/**
	 * 券码类型 0：美团 1：大众点评
	 */
	private Integer sourcetype;
	/**
	 * 使用状态 0:未使用 1：已使用
	 */
	private Integer flag;
	/**
	 * 验券时间
	 */
	private Date createTime;
	/**
	 * 使用时间
	 */
	private Date useTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;

}
