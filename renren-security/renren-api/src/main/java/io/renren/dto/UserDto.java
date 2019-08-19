package io.renren.dto;

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
public class UserDto implements Serializable {
    private static final long serialVersionUID = -7640617705922214805L;

    private long id;
    /**
     * 用户昵称
     */
    private String userName;
    /**
     * 用户头像
     */
    private String userHead;
    /**
     * 用户手机号
     */
    private String userPhone;
    /**
     * 会员等级 0：普通用户 1：会员用户
     */
    private Integer userMember;
}
