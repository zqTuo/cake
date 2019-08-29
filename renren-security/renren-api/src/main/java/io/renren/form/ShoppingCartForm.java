package io.renren.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/8/21 20:46
 */
@Data
@ApiModel(value = "购物车实体类")
public class ShoppingCartForm {

    /**
     * 商品详情ID
     */
    @ApiModelProperty(value = "商品详情ID",required = true,example = "1")
    @Min(value = 1,message = "请选择规格")
    private Long productDetailId;
    /**
     * 购买数量
     */
    @ApiModelProperty(value = "购买数量",required = true,example = "1")
    @Min(value = 1,message = "购买数量")
    private Integer buyNum;

    @ApiModelProperty(value = "寄语")
    private String remark;

    @ApiModelProperty(value = "业务类型 0：添加 1：扣除",required = true)
    @Min(value = 0,message = "缺少业务类型")
    private int opt;
}
