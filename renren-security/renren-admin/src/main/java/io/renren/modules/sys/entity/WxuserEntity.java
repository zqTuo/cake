package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-19 16:35:41
 */
@Data
@TableName("wxuser")
public class WxuserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 用户昵称
	 */
	private String userName;
	/**
	 * 用户头像
	 */
	private String userHead;
	/**
	 * 会员等级 0：普通用户 1：会员用户
	 */
	private Integer userMember;
	/**
	 * openid
	 */
	private String userOpenid;
	/**
	 * unionid
	 */
	private String userUnionid;
	/**
	 * 上次登录IP地址
	 */
	private String userLastip;
	/**
	 * 上次登录时间
	 */
	private Date userLastlogintime;
	/**
	 * 用户状态
	 */
	private Integer userState;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 修改管理员
	 */
	private String updateBy;

}
