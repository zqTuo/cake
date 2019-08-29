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
 * @Date: 2019/8/29 0:34
 */
@Data
@ApiModel(value = "订单编号参数")
public class OrderNoForm {
    @ApiModelProperty(value = "订单编号",required = true)
    @NotBlank(message = "请传入订单编号")
    private String orderNo;
}
