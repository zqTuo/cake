package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * banner表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 18:11:13
 */
@Data
@TableName("tb_banner")
public class BannerEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 图片地址
	 */
	private String bannerPic;
	/**
	 * 跳转地址
	 */
	private String linkUrl;
	/**
	 * 展示位置 0：首页
	 */
	private Integer bannerType;
	/**
	 * 使用状态 0：禁用 1：启用
	 */
	private Integer bannerState;
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
