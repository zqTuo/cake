package io.renren.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 客服接待记录表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-03 11:34:56
 */
@Data
@TableName("tb_custom_record")
public class CustomRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 客服ID
	 */
	private Long customId;
	/**
	 * 用户昵称
	 */
	private String userName;
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
