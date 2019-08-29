package io.renren.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * banner表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 16:22:06
 */
@Data
@TableName("tb_banner")
@ApiModel(value = "banner实体")
public class BannerEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	@ApiModelProperty(hidden = true)
	private Long id;
	/**
	 * 图片地址
	 */
	@ApiModelProperty(value = "图片地址",required = true)
	private String bannerPic;
	/**
	 * 跳转地址
	 */
	@ApiModelProperty(value = "跳转地址",required = true)
	private String linkUrl;
	/**
	 * 展示位置 0：首页
	 */
	@ApiModelProperty(hidden = true)
	private Integer bannerType;
	/**
	 * 使用状态 0：禁用 1：启用
	 */
	@ApiModelProperty(hidden = true)
	private Integer bannerState;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(hidden = true)
	private Date createTime;
	/**
	 * 修改时间
	 */
	@ApiModelProperty(hidden = true)
	private Date updateTime;
	/**
	 * 修改管理员
	 */
	@ApiModelProperty(hidden = true)
	private String updateBy;

}
