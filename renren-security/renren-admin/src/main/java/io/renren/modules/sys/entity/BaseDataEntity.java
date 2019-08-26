package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统配置表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 16:22:06
 */
@Data
@TableName("tb_base_data")
public class BaseDataEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 配置类型
	 * 	0：首页菜单icon
	 * 	{"cake":{"title":"生日蛋糕","icon":"http://cake.maojimall.com/images/cake.png"},
	 * 	"tea":{"title":"元气下午茶","icon":"http://cake.maojimall.com/images/tea.png"},
	 * 	 "hot":{"title":"人气推荐","icon":"http://cake.maojimall.com/images/hot.png"},
	 * 	 "meituan":{"title":"使用美团/大众点评券","icon":"http://cake.maojimall.com/images/meituan.png"}
	 * 	 }
	 */
	private Integer sourceType;
	/**
	 * 配置数据内容
	 */
	private String content;
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
