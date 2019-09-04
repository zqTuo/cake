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
 * 订单表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 16:22:06
 */
@Data
@TableName("tb_shop_order")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShopOrderEntity implements Serializable {
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
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 订单支付金额
	 */
	private BigDecimal orderPrice;
	/**
	 * 订单优惠金额
	 */
	private BigDecimal orderDiscount;
	/**
	 * 订单优惠类型 0：无优惠 1：优惠券  2：美团券
	 */
	private Integer orderDiscountType;
	/**
	 * 用户的优惠券ID
	 */
	private Long couponUserId;
	/**
	 * 美团券验券记录ID
	 */
	private Long meituanId;
	/**
	 * 订单状态 -1：未支付 0：已取消 1：已支付 2：已发货 3：（已确认）已签收
	 */
	private Integer orderState;
	/**
	 * 订单来源 0：蛋糕订购 1：预约烘焙课程 2:购买会员
	 */
	private Integer orderSourceType;
	/**
	 * 寄语
	 */
	private String orderRemark;
	/**
	 * 配送省份 x
	 */
	private String addrPro;
	/**
	 * 配送城市 x
	 */
	private String addrCity;
	/**
	 * 配送区域 x
	 */
	private String addrDis;
	/**
	 * 配送详细地址
	 */
	private String addrDetail;
	/**
	 * 收货人
	 */
	private String addrReceiver;
	/**
	 * 配送方式 0：送货上门 1：门店自取
	 */
	private Integer sendType;
	/**
	 * 配送费用
	 */
	private BigDecimal sendPrice;
	/**
	 * 联系方式
	 */
	private String addrPhone;
	/**
	 * 派送时间/取货日期
	 */
	private String sendTime;

	/**
	 * 接单客服
	 */
	private String kfNick;
	/**
	 * 派送日期 yyyy-MM-dd
	 */
	private Date sendDate;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 支付时间
	 */
	private Date payTime;
	/**
	 * 支付方式 0：微信支付
	 */
	private Integer payType;
	/**
	 * 门店ID
	 */
	private Long shopId;
	/**
	 * 取消时间
	 */
	private Date cancelTime;
	/**
	 * 签收时间
	 */
	private Date finishTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 修改管理员
	 */
	private String updateBy;

}
