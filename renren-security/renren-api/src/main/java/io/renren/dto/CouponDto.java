package io.renren.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "用户优惠券ID",required = true,example = "1")
    private long couponUserId; // 用户的优惠券ID
    /**
     * 优惠券名称
     */
    @ApiModelProperty(value = "优惠券名称",required = true)
    private String couponName;
    /**
     * 触发价格
     */
    @ApiModelProperty(value = "触发价格",required = true)
    private BigDecimal couponPrice;
    /**
     * 优惠金额
     */
    @ApiModelProperty(value = "优惠金额",required = true)
    private BigDecimal price;

    @ApiModelProperty(value = "优惠券类型 0：商品优惠券，1：单次体验课程优惠券，2：会员套餐课程优惠券",required = true,example = "0")
    private int couponType;

    @ApiModelProperty(value = "优惠券描述")
    private String desc;
    /**
     * 截止时间 判断条件
     */
    @ApiModelProperty(hidden = true)
    private Date endTime;

    /**
     * 截止日期
     */
    @ApiModelProperty(value = "截止日期")
    private String expiredTime;
    /**
     * 是否限时 0：不限时 1:限时 - 未过期 2:限时 - 已过期
     */
    @ApiModelProperty(value = "是否限时 0:不限时, 1:限时 - 未过期，2:限时 - 已过期(不显示)",required = true,example = "0")
    private int dateFlag;
}
