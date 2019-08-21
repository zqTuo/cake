package io.renren.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
     * 购物车ID
     */
    @ApiModelProperty(value = "购物车ID，修改时必传")
    private Long id;

    /**
     * 商品详情ID
     */
    @ApiModelProperty(value = "商品详情ID",required = true)
    private Long productDetailId;
    /**
     * 购买数量
     */
    @ApiModelProperty(value = "购买数量",required = true)
    private Integer buyNum;

}
