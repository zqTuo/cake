package io.renren.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户剩余套餐次数表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-17 15:50:04
 */
@Data
@Builder
@TableName("tb_combo_user")
public class ComboUserEntity implements Serializable {
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
	 * 课程套餐类别ID
	 */
	private Long typeId;
	/**
	 * 剩余次数
	 */
	private Integer num;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 过期时间
	 */
	private Date expiredTime;
}
