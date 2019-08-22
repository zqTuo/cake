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
 * @Date: 2019/8/21 19:51
 */
@Data
@ApiModel(value = "分页表单")
public class PageForm {
    @ApiModelProperty(value = "当前页码",required = true)
    @Min(value = 1,message = "最小页码为1")
    private int pageNo;

    @ApiModelProperty(value = "每页条数",required = true)
    @Min(value = 5,message = "每页最少展示5条")
    private int pageSize;

    @ApiModelProperty(value = "资源类型",allowableValues = "0:热门商品,1:分类商品")
    private int sourceType;

    @ApiModelProperty(value = "类别ID，查询分类商品时必传")
    private long cateid;

    @ApiModelProperty(value = "门店ID",required = true)
    private long shopId;
}
