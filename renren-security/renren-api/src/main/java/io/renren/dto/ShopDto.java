package io.renren.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/8/21 15:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "店铺实体")
public class ShopDto {
    @ApiModelProperty(value = "店铺ID",required = true,example = "1")
    private long id; // 店铺ID
    @ApiModelProperty(value = "店铺名称",required = true)
    private String shopName; // 店铺名称
    /**
     * 所在地 经度
     */
    @ApiModelProperty(value = "经度",required = true)
    private String shopLongitude;
    /**
     * 所在地 纬度
     */
    @ApiModelProperty(value = "纬度",required = true)
    private String shopLatitude;
    @ApiModelProperty(value = "定位所在地标记 0：未定位此地 1：定位所在地",required = true)
    private int currentFlag; // 定位所在地标记 0：未定位此地 1：定位所在地
}
