package io.renren.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/8/21 15:21
 */
@Data
@ApiModel(value = "门店参数")
public class ShopForm {
    /**
     * 所在地 经度
     */
    @ApiModelProperty(value = "经度")
    private double longitude;
    /**
     * 所在地 纬度
     */
    @ApiModelProperty(value = "纬度")
    private double latitude;
}
