package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 配送时间表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-19 16:35:41
 */
@Data
@TableName("send_time")
public class SendTimeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 派送时间点 HH:mm
	 */
	private Date timeArea;
	/**
	 * 最大预约订单数
	 */
	private Integer maxOrder;

}
