package io.renren.dto;

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
public class ShopDto {
    private long id; // 店铺ID
    private String shopName; // 店铺名称
    /**
     * 所在地 经度
     */
    private String shopLongitude;
    /**
     * 所在地 纬度
     */
    private String shopLatitude;

    private int currentFlag; // 定位所在地标记 0：未定位此地 1：定位所在地
}
