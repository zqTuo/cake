package io.renren.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/8/21 16:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "热销商品实体")
@Component
public class IndexProductDto {

    /**
     * 商品ID
     */
    @ApiModelProperty(value = "商品ID",required = true,example = "1")
    private long id;
    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称",required = true)
    private String productName;

    /**
     * 商品售价
     */
    @ApiModelProperty(value = "商品售价",required = true)
    private BigDecimal productPrice;

    /**
     * 商品主图
     */
    @ApiModelProperty(value = "商品主图",required = true)
    private String productImg;

}
