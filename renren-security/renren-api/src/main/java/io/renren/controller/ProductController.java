package io.renren.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.dto.CategoryDto;
import io.renren.dto.ProductDto;
import io.renren.entity.ProductDetailEntity;
import io.renren.entity.ProductEntity;
import io.renren.form.PageForm;
import io.renren.service.ProductCategoryService;
import io.renren.service.ProductDetailService;
import io.renren.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Value("${project.pic_pre}")
    private String pic_pre;
    @Value("${project.video_pre}")
    private String video_pre;

    @GetMapping("cateInfo")
    @ApiOperation(value = "获取商品分类数据接口")
    public R cateInfo(){
        List<CategoryDto> categoryDtoList = productCategoryService.getProCateData();
        return R.ok().put("arrayData",categoryDtoList);
    }

    @GetMapping("proInfo")
    @ApiOperation(value = "获取商品数据接口")
    public R proInfo(@RequestBody PageForm form){
        ValidatorUtils.validateEntity(form);

        form.setPageNo(form.getPageNo() > 0 ? (form.getPageNo()-1) * form.getPageSize() : 0);

        List<ProductDto> productDtoList = productService.findProduct(form);

        for (ProductDto productDto:productDtoList){
            productDto.setProductImg(pic_pre + productDto.getProductImg());
        }
        return R.ok().put("arrayData",productDtoList);
    }

    @GetMapping("productInfo")
    @ApiOperation(value = "获取商品详情接口")
    public R productInfo(@RequestBody @ApiParam(value = "商品ID",required = true)long id){
        ProductEntity productEntity = productService.getById(id);

        productEntity.setProductImg(pic_pre + productEntity.getProductImg());

        if(StringUtils.isNotBlank(productEntity.getProductVideo())){
            productEntity.setProductVideo(video_pre + productEntity.getProductVideo());
        }

        try {
            List<ProductDetailEntity> productDetailEntityList = productDetailService.list(new QueryWrapper<ProductDetailEntity>().eq("product_id",id));

            Set<JSONObject> sizeArr = new HashSet<>(); // 所有尺寸列表 [{"size":"6寸","extraPrice":"20","flag":1,"id":1}]
            Set<JSONObject> tasteArr = new HashSet<>(); // 所有口味列表 [{"taste":"标准口味","extraPrice":"20","flag":1,"id":1}]
            JSONObject price = new JSONObject(); // 尺寸、口味对应对象 格式{"id_id":{"price":11,"detailId":1}}

            for (ProductDetailEntity productDetailEntity:productDetailEntityList){
                productDetailEntity.setDetailCover(pic_pre + productDetailEntity.getDetailCover());

                JSONObject size = JSONObject.parseObject(productDetailEntity.getDetailSize());
                size.put("id",productDetailEntity.getId());
                sizeArr.add(size);

                JSONObject taste = JSONObject.parseObject(productDetailEntity.getDetailTaste());
                taste.put("id",productDetailEntity.getId());
                tasteArr.add(taste);

                StringBuilder key = new StringBuilder(productDetailEntity.getId() + "_");
                for (ProductDetailEntity detailEntity:productDetailEntityList){
                    key.append(detailEntity.getId());
                    if(!price.containsKey(key.toString())){
                        JSONObject productDetail = new JSONObject();
                        productDetail.put("price",detailEntity.getDetailPrice());
                        productDetail.put("detailId",detailEntity.getId());
                        price.put(String.valueOf(key),productDetail);
                    }
                }
            }

            ProductDto productDto = new ProductDto();
            BeanUtils.copyProperties(productDto,productEntity);

            Map<String,Object> map = new HashMap<>();
            map.put("product",productDto);
            map.put("sizeArr",sizeArr);
            map.put("tasteArr",tasteArr);
            map.put("price",price);

            return R.ok(map);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(500,"系统错误");
        }
    }

    @GetMapping("getExtraInfo")
    @ApiOperation(value = "获取加购商品接口")
    public R getExtraInfo(){
        List<ProductDto> productDtoList = productService.getExtraInfo();

        for (ProductDto productDto:productDtoList){
            productDto.setProductImg(pic_pre + productDto.getProductImg());
        }
        return R.ok().put("arrayData",productDtoList);
    }
}
