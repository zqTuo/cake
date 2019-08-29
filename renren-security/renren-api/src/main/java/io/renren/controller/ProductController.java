package io.renren.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.result.Result;
import io.renren.common.validator.ValidatorUtils;
import io.renren.dto.CategoryDto;
import io.renren.dto.ProductDto;
import io.renren.dto.ProductInfoDetailDto;
import io.renren.dto.ProductInfoDto;
import io.renren.entity.ProductDetailEntity;
import io.renren.entity.ProductEntity;
import io.renren.entity.ProductSizeEntity;
import io.renren.entity.ProductTasteEntity;
import io.renren.form.PageForm;
import io.renren.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/8/21 17:37
 */
@Api(tags = "商品控制器")
@RestController
@RequestMapping("/api/goods")
public class ProductController {
    @Resource
    private ProductCategoryService productCategoryService;
    @Resource
    private ProductService productService;
    @Resource
    private ProductDetailService productDetailService;
    @Resource
    private ProductSizeService productSizeService;
    @Resource
    private ProductTasteService productTasteService;

    @Value("${project.pic_pre}")
    private String pic_pre;
    @Value("${project.video_pre}")
    private String video_pre;

    @GetMapping("cateInfo")
    @ApiOperation(value = "获取商品分类数据接口")
    public Result<CategoryDto> cateInfo(){
        List<CategoryDto> categoryDtoList = productCategoryService.getProCateData();
        return new Result<>().ok(categoryDtoList);
    }

    @PostMapping("proInfo")
    @ApiOperation(value = "获取商品数据接口")
    public Result<ProductDto> proInfo(@RequestBody PageForm form){
        ValidatorUtils.validateEntity(form);

        form.setPageNo(form.getPageNo() > 0 ? (form.getPageNo()-1) * form.getPageSize() : 0);

        List<ProductDto> productDtoList = productService.findProduct(form);

        for (ProductDto productDto:productDtoList){
            productDto.setProductImg(pic_pre + productDto.getProductImg());
        }
        return new Result<>().ok(productDtoList);
    }

    @GetMapping("productInfo")
    @ApiOperation(value = "获取商品详情接口",notes = "选择规格时，通过sizeId,tasteId在商品详情列表中匹配二者皆相等的数据，该数据的id为商品详情ID")
    public Result<ProductInfoDto> productInfo(@RequestParam("id") @ApiParam(value = "商品ID",required = true)long id){
        ProductEntity productEntity = productService.getById(id);

        productEntity.setProductImg(pic_pre + productEntity.getProductImg());

        String[] bannerArr = productEntity.getProductBanner().split(",");
        for (int i = 0; i < bannerArr.length; i++) {
            if(!bannerArr[i].equals("")){
                bannerArr[i] = pic_pre + bannerArr[i];
            }
        }

        if(StringUtils.isNotBlank(productEntity.getProductVideo())){
            productEntity.setProductVideo(video_pre + productEntity.getProductVideo());
        }

        try {
            List<ProductInfoDetailDto> productDetailEntityList = productDetailService.getByProductId(id);

            List<ProductSizeEntity> sizeArr = productSizeService.list(new QueryWrapper<ProductSizeEntity>()
                    .eq("product_id",id).orderByAsc("id"));

            List<ProductTasteEntity> tasteArr = productTasteService.list(new QueryWrapper<ProductTasteEntity>()
                    .eq("product_id",id).orderByAsc("id"));


            for (ProductInfoDetailDto productInfoDto:productDetailEntityList){
                productInfoDto.setDetailCover(pic_pre + productInfoDto.getDetailCover());
            }

            ProductDto productDto = new ProductDto();
            BeanUtils.copyProperties(productDto,productEntity);

            ProductInfoDto productInfoDto = ProductInfoDto.builder().productDto(productDto)
                    .sizeList(sizeArr).tasteList(tasteArr).bannerArr(bannerArr)
                    .detailList(productDetailEntityList).build();

            return new Result<>().ok(productInfoDto);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>().error("系统错误");
        }
    }

    @GetMapping("getExtraInfo")
    @ApiOperation(value = "获取加购商品接口")
    public Result<ProductDto> getExtraInfo(){
        List<ProductDto> productDtoList = productService.getExtraInfo();

        for (ProductDto productDto:productDtoList){
            productDto.setProductImg(pic_pre + productDto.getProductImg());
        }
        return new Result<>().ok(productDtoList);
    }
}
