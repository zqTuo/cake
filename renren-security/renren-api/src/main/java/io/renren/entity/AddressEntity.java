package io.renren.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 * 收货地址表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 14:04:08
 */
@Data
@TableName("tb_address")
@ApiModel(value = "用户地址实体")
public class AddressEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	@ApiModelProperty(value = "地址ID,修改时必传",example = "0")
	private Long id;
	/**
	 * 用户ID
	 */
	@ApiModelProperty(hidden = true)
	private Long userId;
	/**
	 * 省份
	 */
//	@ApiModelProperty(value = "省份",required = true)
//	@NotBlank(message = "请选择省份")
	@ApiModelProperty(hidden = true)
	private String province;
	/**
	 * 市
	 */
	@ApiModelProperty(value = "市",required = true)
	@NotBlank(message = "请选择市")
	private String city;
	/**
	 * 区
	 */
//	@ApiModelProperty(value = "区",required = true)
//	@NotBlank(message = "请选择区")
	@ApiModelProperty(hidden = true)
	private String district;
	/**
	 * 详细地址
	 */
	@ApiModelProperty(value = "详细地址",required = true)
	@NotBlank(message = "请输入详细地址")
	private String address;
	/**
	 * 收货人
	 */
	@ApiModelProperty(value = "收货人",required = true)
	@NotBlank(message = "请输入收货人姓名")
	private String contract;
	/**
	 * 手机号
	 */
	@ApiModelProperty(value = "手机号",required = true)
	@NotBlank(message = "请输入手机号")
	@Pattern(regexp = "^((13[0-9])|(14[0-9])|(15([0-9]))|(16([0-9]))|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8}$",message = "手机号格式不正确")
	private String phone;
	/**
	 * 默认地址 0：否 1：是
	 */
	@ApiModelProperty(value = "是否默认地址",allowableValues = "0：否 1：是",required = true,example = "0")
	private Integer defaultFlag;

}
