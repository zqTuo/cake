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
 * @Date: 2019/8/23 20:13
 */
@Data
@ApiModel(value = "派送时间实体")
@AllArgsConstructor
@NoArgsConstructor
public class SendTimeDto {
    @ApiModelProperty(value = "时间段ID",required = true)
    private long id;
    private String startTime; // 派送开始时间 10:00
    private String endTime; // 派送截止时间 11:00
    /**
     * 最大预约订单数
     */
    private int maxOrder;
    private int state; // 是否可以预订 0：已约满 1：未在指定时间段 2：可以预约
}
