package io.renren.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Author: Clarence
 * @Description:
 * @Date: 2019/9/12 22:10.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "课程结算实体")
public class CoursePayDto {
    @ApiModelProperty(value = "上课时间")
    private String sendTime;
    @ApiModelProperty(value = "店铺名")
    private String shopName;
    @ApiModelProperty(value = "手机号码")
    private String userPhone;
    @ApiModelProperty(value = "课程名称")
    private String courseName;
    @ApiModelProperty(value = "订单金额")
    private BigDecimal coursePrice;
    @ApiModelProperty(value = "实际金额")
    private BigDecimal orderPrice;
    @ApiModelProperty(value = "优惠金额")
    private BigDecimal discountPrice;
}
