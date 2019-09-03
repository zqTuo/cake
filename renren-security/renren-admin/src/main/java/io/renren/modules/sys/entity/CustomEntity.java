package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 微信客服表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-03 11:34:56
 */
@Data
@TableName("tb_custom")
public class CustomEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 微信昵称
	 */
	private String userName;
	/**
	 * OPENID
	 */
	private String userOpenId;
	/**
	 * 微信头像
	 */
	private String userHead;
	/**
	 * 完整客服帐号，格式为：帐号前缀@公众号微信号
	 */
	private String kfAccount;
	/**
	 * 客服昵称
	 */
	private String kfNick;
	/**
	 * 客服编号
	 */
	private String kfAccount;
	/**
	 * 客服头像
	 */
	private String kfHeadImgUrl;
	/**
	 * 如果客服帐号已绑定了客服人员微信号， 则此处显示微信号
	 */
	private String kfWx;
	/**
	 * 客服状态 0：已停用 1：已启用
	 */
	private Integer state;
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
