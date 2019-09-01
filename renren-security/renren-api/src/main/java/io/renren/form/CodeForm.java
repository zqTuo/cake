package io.renren.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @Author: Clarence
 * @Description:
 * @Date: 2019/9/1 13:19.
 */
@Data
@ApiModel(value = "订单编号参数")
public class CodeForm {
    @ApiModelProperty(value = "券码",required = true)
    @NotBlank(message = "请传入券码")
    private String code;

    @ApiModelProperty(value = "店铺ID",required = true)
    @Min(value = 1,message = "请求传入店铺ID")
    private long shopId;

    @ApiModelProperty(value = "店铺类型 0：美团 1：大众点评",required = true)
    @Min(value = 0,message = "请选择店铺类型")
    private int sourceType;
}
