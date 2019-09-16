package io.renren.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/9/16 14:59
 */
@Data
@ApiModel(value = "课程套餐结算参数实体")
public class SetCoursePayForm {
    @ApiModelProperty(value = "课程套餐ID",required = true)
    private long comboCourseId;
    @ApiModelProperty(value = "优惠券ID")
    private Long couponUserId;
}
