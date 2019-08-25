package io.renren.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: Clarence
 * @Description: 我的优惠券实体
 * @Date: 2019/8/25 23:20.
 */
@Data
@ApiModel(value = "我的优惠券实体")
public class CouponDto {
    private long couponUserId; // 用户的优惠券ID
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
    private int couponType;
    /**
     * 截止时间 判断条件
     */
    private Date endTime;

    /**
     * 截止日期
     */
    private String expiredTime;
    /**
     * 是否限时 0：不限时 1:限时
     */
    private int dateFlag;
}
