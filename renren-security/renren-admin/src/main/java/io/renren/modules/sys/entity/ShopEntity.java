package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 门店表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 16:22:06
 */
@Data
@TableName("tb_shop")
public class ShopEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 门店名称
	 */
	private String shopName;
	/**
	 * 门店详细地址
	 */
	private String shopAddr;
	/**
	 * 所在地 经度
	 */
	private String shopLongitude;
	/**
	 * 所在地 纬度
	 */
	private String shopLatitude;
	/**
	 * 美团店铺ID
	 */
	private String meituanShopId;
	/**
	 * 点评店铺ID
	 */
	private String dianpingShopId;
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
