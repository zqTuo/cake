package io.renren.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/9/16 15:08
 */
@Data
@ApiModel(value = "课程套餐实体")
@NoArgsConstructor
@AllArgsConstructor
public class ComboCourseDto {
    @ApiModelProperty(value = "课程套餐ID")
    private Long id;
    /**
     * 套餐名称
     */
    @ApiModelProperty(value = "套餐名称")
    private String title;
    /**
     * 套餐图片
     */
    @ApiModelProperty(value = "套餐图片")
    private String picUrl;
    /**
     * 有效期 0：永久有效
     */
    @ApiModelProperty(value = "有效期 单位：月，若值为0，代表无期限")
    private Integer validPeriod;
    /**
     * 套餐价格
     */
    @ApiModelProperty(value = "套餐价格")
    private BigDecimal price;
    /**
     * 套餐描述
     */
    @ApiModelProperty(value = "套餐内容")
    private String remark;

    @ApiModelProperty(value = "有效期时段")
    private String expiredDate;

    @ApiModelProperty(value = "支付金额")
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "优惠金额")
    private BigDecimal discount;
}
