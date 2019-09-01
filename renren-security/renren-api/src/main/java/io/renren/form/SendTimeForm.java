package io.renren.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * 功能描述: <br>
 * 地址表单
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/8/23 16:30
 */
@Data
@ApiModel(value = "地址表单")
public class SendTimeForm {

    @ApiModelProperty(value = "城市",required = true)
    private String city;

    @ApiModelProperty(value = "详细地址",required = true)
    private String addr;

    @ApiModelProperty(value = "派送日期，默认当天，格式yyyy-MM-dd")
    @NotBlank(message = "请选择时间")
    private String selectedDate;

    @ApiModelProperty(value = "店铺ID",required = true,example = "1")
    @Min(value = 1,message = "请传入店铺ID")
    private long shopId;

    @ApiModelProperty(value = "配送方式 0：送货上门 1：门店自取",required = true)
    @Min(value = 0,message = "请传入店铺ID")
    private int sendType;
}
