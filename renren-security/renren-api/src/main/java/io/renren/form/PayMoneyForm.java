package io.renren.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/8/27 17:56
 */
@Data
@ApiModel(value = "计算支付金额参数")
public class PayMoneyForm {
    @ApiModelProperty(value = "配送方式 0：送货上门 1：门店自取",allowableValues = "range[0,1]",required = true)
    @Min(value = 0,message = "提供配送方式")
    private int sendType;

    @ApiModelProperty(value = "配送地址ID，送货上门时必传")
    private long addressId;

    @ApiModelProperty(value = "商品详情ID数组",required = true)
    private long[] productDetailId;

    @ApiModelProperty(value = "加购商品id数组")
    private long[] extraIds;

    @ApiModelProperty(value = "优惠券ID")
    private long couponUserId;

    @ApiModelProperty(value = "店铺ID",required = true)
    @Min(value = 1,message = "请传入店铺ID")
    private long shopId;
}
