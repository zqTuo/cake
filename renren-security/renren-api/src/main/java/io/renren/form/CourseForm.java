package io.renren.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @Author: Clarence
 * @Description:
 * @Date: 2019/9/12 22:13.
 */
@Data
@ApiModel(value = "课程预约结算实体")
public class CourseForm {
    /**
     * 同行人数 - 大人
     */
    @ApiModelProperty(value = "同行人数 - 大人")
    @Min(value = 0,message = "请传入同行10岁以上人数")
    private Integer adultNum;
    /**
     * 同行人数 - 小孩
     */
    @ApiModelProperty(value = "同行人数 - 小孩")
    @Min(value = 0,message = "请传入同行10岁以下人数")
    private Integer kidNum;
    /**
     * 预约课程ID
     */
    @ApiModelProperty(value = "课程ID")
    @Min(value = 1,message = "请传入课程ID")
    private Long courseId;
    /**
     * 优惠券ID
     */
    @ApiModelProperty(value = "优惠券ID")
    private Long couponUserId;

    @ApiModelProperty(value = "手机号")
    @Pattern(regexp = "^((13[0-9])|(14[0-9])|(15([0-9]))|(16([0-9]))|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8}$",message = "手机号格式不正确")
    @NotBlank(message = "请传入手机号")
    private String userPhone;

    @NotBlank(message = "缺少上课时间参数")
    @ApiModelProperty(value = "上课时间 格式：yyyy-MM-dd HH:mm",required = true)
    private String sendTime;

    @Min(value = 0,message = "缺少门店参数")
    @ApiModelProperty(value = "门店ID",required = true)
    private long shopId;

}
