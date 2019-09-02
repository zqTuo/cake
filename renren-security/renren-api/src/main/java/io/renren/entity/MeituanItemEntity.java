package io.renren.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 美团券包含商品信息表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-01 14:26:01
 */
@Data
@TableName("tb_meituan_item")
@Builder
public class MeituanItemEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 美团券ID
	 */
	private Long couponId;
	/**
	 * 商品详情ID
	 */
	private Long productDetailId;

}
