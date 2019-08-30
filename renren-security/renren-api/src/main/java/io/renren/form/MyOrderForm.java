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
 * @Date: 2019/8/28 23:34
 */
@Data
@ApiModel(value = "我的订单列表参数")
public class MyOrderForm {
    @ApiModelProperty(value = "订单状态：-1:全部订单 0：待付款 1：待发货 2：待上课 3：已完成",required = true)
    @Min(value = -1,message = "请选择订单状态")
    private int orderState;

    @ApiModelProperty(value = "当前页码,首页为 1",required = true)
    @Min(value = 1,message = "缺少页码参数")
    private int pageNo;
    @ApiModelProperty(value = "每页条数",required = true)
    @Min(value = 1,message = "缺少每页条数")
    private int pageSize;
}
