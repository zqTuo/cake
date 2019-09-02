package io.renren.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/8/28 10:17
 */
@ApiModel(value = "下单表单")
@Data
public class OrderForm {

    @ApiModelProperty(value = "购买商品数据,格式：[{\"detailId\":1,buyNum:1}]",required = true)
    private String prods;

    @ApiModelProperty(value = "优惠类型：0无优惠 1：优惠券 2：美团券",required = true)
    @Min(value = 0,message = "缺少优惠类型参数")
    private int discountType;

    @ApiModelProperty(value = "优惠券ID")
    private long couponUserId;

    @ApiModelProperty(value = "美团券券码ID")
    private long meituanId;

    @Min(value = 0,message = "缺少业务类型参数")
    @ApiModelProperty(value = "业务类型 0:蛋糕订购 1：预约烘焙课程 2：购买会员",required = true)
    private int sourceType;

    @Min(value = 0,message = "缺少配送方式参数")
    @ApiModelProperty(value = "配送方式 0：送货上门 1：门店自取",required = true)
    private int sendType;

    @ApiModelProperty(value = "配送地址ID")
    private long addressId;

    @NotBlank(message = "缺少配送/取货时间参数")
    @ApiModelProperty(value = "配送/取货时间 格式：yyyy-MM-dd HH:mm",required = true)
    private String sendTime;

    @Min(value = 0,message = "缺少门店参数")
    @ApiModelProperty(value = "门店ID",required = true)
    private long shopId;

    @Min(value = 0,message = "缺少购买来源参数")
    @ApiModelProperty(value = "购买来源 0:直接购买 1：购物车购买",required = true)
    private int sourceFrom;

    /**
     * 备注
     */
    @ApiModelProperty(value = "寄语")
    private String orderRemark;
}