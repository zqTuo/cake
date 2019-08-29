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
    @ApiModelProperty(value = "时间段ID",required = true,example = "1")
    private long id;
    @ApiModelProperty(value = "开始派送时间",required = true)
    private String startTime; // 派送开始时间 10:00
    @ApiModelProperty(value = "派送截止时间",required = true)
    private String endTime; // 派送截止时间 11:00
    /**
     * 最大预约订单数
     */
    @ApiModelProperty(hidden = true,example = "1")
    private int maxOrder;
    @ApiModelProperty(value = "是否可以预订 0：已约满 1：未在指定时间段 2：可以预约",required = true,example = "1")
    private int state; // 是否可以预订 0：已约满 1：未在指定时间段 2：可以预约
}
