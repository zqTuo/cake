package io.renren.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 财务流水表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-28 21:42:09
 */
@Data
@TableName("tb_order_sales")
@Builder
public class OrderSalesEntity implements Serializable {
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
	 * 订单编号
	 */
	private String orderNo;
	/**
	 * 订单金额
	 */
	private BigDecimal orderPrice;
	/**
	 * 优惠金额
	 */
	private BigDecimal orderDiscount;
	/**
	 * 业务类型 0：蛋糕订购 1：预约烘焙课程 2:购买会员 3:退款
	 */
	private Integer sourceType;
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
