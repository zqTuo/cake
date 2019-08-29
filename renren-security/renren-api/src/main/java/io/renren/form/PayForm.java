package io.renren.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/8/28 16:41
 */
@ApiModel(value = "支付实体")
@Data
public class PayForm {
    @ApiModelProperty(value = "订单号",required = true)
    @NotBlank(message = "请传入订单号")
    private String orderNo;
}
