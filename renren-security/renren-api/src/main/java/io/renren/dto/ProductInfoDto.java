package io.renren.dto;

import io.renren.entity.ProductDetailEntity;
import io.renren.entity.ProductSizeEntity;
import io.renren.entity.ProductTasteEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/8/29 5:47
 */
@Data
@Builder
@ApiModel(value = "商品信息实体")
public class ProductInfoDto {
    @ApiModelProperty(value = "商品信息")
    private ProductDto productDto;
    @ApiModelProperty(value = "尺寸列表")
    private List<ProductSizeEntity> sizeList;
    @ApiModelProperty(value = "口味列表")
    private List<ProductTasteEntity> tasteList;
    @ApiModelProperty(value = "副图数组列表")
    private String[] bannerArr;
    @ApiModelProperty(value = "商品详情数据列表")
    private List<ProductInfoDetailDto> detailList;
}
