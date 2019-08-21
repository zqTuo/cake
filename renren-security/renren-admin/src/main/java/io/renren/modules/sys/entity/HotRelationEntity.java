package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 热销栏目与商品关联表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 17:12:34
 */
@Data
@TableName("tb_hot_relation")
public class HotRelationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 热销栏目ID
	 */
	private Long hotId;
	/**
	 * 商品ID
	 */
	private Long productId;
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
