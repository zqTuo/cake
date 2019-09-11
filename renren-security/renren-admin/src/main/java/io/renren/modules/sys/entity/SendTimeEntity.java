package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * 配送时间表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 21:10:39
 */
@Data
@TableName("tb_send_time")
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
	@NotBlank(message = "缺少开始时间点")
	private String startTime;
	/**
	 * 派送时间点 HH:mm
	 */
	@NotBlank(message = "缺少结束时间点")
	private String endTime;
	/**
	 * 最大预约订单数
	 */
	@Min(value = 1,message = "缺少最大预约订单数")
	private Integer maxOrder;
	/**
	 * 时间类别：0：配送时间 1：课程时间
	 */
	@Min(value = 0,message = "缺少时间类别")
	private Integer type;
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
