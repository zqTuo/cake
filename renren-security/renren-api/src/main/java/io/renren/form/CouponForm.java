package io.renren.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

/**
 * @Author: Clarence
 * @Description:
 * @Date: 2019/8/25 23:37.
 */
@Data
@ApiModel(value = "获取优惠券数据参数")
public class CouponForm {
    @ApiModelProperty(value = "数据来源 0:蛋糕预定结算页，1:个人页优惠券列表，2:课程预定结算页",required = true,example = "0")
    @Min(value = 0,message = "缺少来源参数")
    private int sourceType;
}
