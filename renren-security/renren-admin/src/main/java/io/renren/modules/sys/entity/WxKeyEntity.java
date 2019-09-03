package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 微信关键字表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-03 17:29:29
 */
@Data
@TableName("tb_wx_key")
public class WxKeyEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 关键字,多个中间用英文逗号隔开
	 */
	private String keyword;
	/**
	 * 消息类型（text/news/image）
	 */
	private String msgType;
	/**
	 * 回复内容
	 */
	private String content;
	/**
	 * 图文ID
	 */
	private String mediaid;

}
