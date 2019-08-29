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
 * @Date: 2019/8/28 19:38
 */
@Data
@ApiModel(value = "结算商品表单")
public class PayProForm {
    @Min(value = 0,message = "缺少购买来源参数")
    @ApiModelProperty(value = "购买来源 0:直接购买 1：购物车购买",required = true)
    private int sourceFrom;

    @ApiModelProperty(value = "商品详情ID，直接在商品详情页购买时必传")
    private long productDetailId;

    @ApiModelProperty(value = "购买数量，直接在商品详情页购买时必传")
    private int buyNum;
}
