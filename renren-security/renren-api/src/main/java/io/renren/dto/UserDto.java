package io.renren.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: Clarence
 * @Description:
 * @Date: 2019/8/19 23:59.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value = "用户基本信息实体")
public class UserDto implements Serializable {
    private static final long serialVersionUID = -7640617705922214805L;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称",required = true,example = "1")
    private String userName;
    /**
     * 用户头像
     */
    @ApiModelProperty(value = "用户头像",required = true)
    private String userHead;
    /**
     * 用户手机号
     */
    @ApiModelProperty(value = "用户手机号")
    private String userPhone;
    /**
     * 会员等级 0：普通用户 1：会员用户
     */
    @ApiModelProperty(value = "会员等级 0：普通用户 1：会员用户",allowableValues = "range[0,1]")
    private Integer userMember;
}
