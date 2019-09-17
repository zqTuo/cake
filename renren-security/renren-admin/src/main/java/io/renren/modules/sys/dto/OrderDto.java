package io.renren.modules.sys.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/9/5 9:47
 */
@Data
public class OrderDto {
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
     * 用户昵称
     */
    private String userName;
    /**
     * 收货人
     */
    private String addrReceiver;

    /**
     * 订单支付金额
     */
    private BigDecimal orderPrice;
    /**
     * 订单优惠金额
     */
    private BigDecimal orderDiscount;
    /**
     * 订单优惠类型 0：无优惠 1：优惠券  2：美团券 3：优惠券+美团
     */
    private Integer orderDiscountType;
    /**
     * 订单状态 -1：未支付 0：已取消 1：已支付 2：已发货 3：（已确认）已签收
     */
    private Integer orderState;
    /**
     * 订单来源 0:蛋糕订购 1：预约单次体验课程 2：购买套餐课程 3：使用套餐
     */
    private Integer orderSourceType;
    /**
     * 寄语
     */
    private String orderRemark;
    /**
     * 备注
     */
    private String orderDes;
    /**
     * 配送省份
     */
    private String addrPro;
    /**
     * 配送城市
     */
    private String addrCity;
    /**
     * 配送区域
     */
    private String addrDis;
    /**
     * 配送详细地址
     */
    private String addrDetail;
    /**
     * 配送方式 0：送货上门 1：门店自取
     */
    private Integer sendType;
    /**
     * 联系方式
     */
    private String addrPhone;
    /**
     * 配送费用
     */
    private BigDecimal sendPrice;
    /**
     * 派送时间段
     */
    private String sendTime;
    /**
     * 接单客服
     */
    private String kfNick;
    /**
     * 同行人数 - 大人
     */
    private Integer adultNum;
    /**
     * 同行人数 - 小孩
     */
    private Integer kidNum;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 支付时间
     */
    private Date payTime;
    /**
     * 门店ID
     */
    private Long shopId;
    /**
     * 门店名称
     */
    private String shopName;
    /**
     * 用户课程套餐ID
     */
    private Long comboUserId;
    /**
     * 取消时间
     */
    private Date cancelTime;
    /**
     * 签收时间
     */
    private Date finishTime;
    /**
     * 配送时间
     */
    private Date updateTime;
}
