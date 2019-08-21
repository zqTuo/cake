package io.renren.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.annotation.Login;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.entity.ProductDetailEntity;
import io.renren.entity.ProductEntity;
import io.renren.entity.ShoppingCartEntity;
import io.renren.form.ShoppingCartForm;
import io.renren.service.ProductDetailService;
import io.renren.service.ProductService;
import io.renren.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/8/21 20:14
 */
@RestController
@RequestMapping("/api/buy")
@Api(value = "购物车接口控制器")
public class ShoppingCartController {
    @Resource
    private ShoppingCartService shoppingCartService;
    @Resource
    private ProductService productService;
    @Resource
    private ProductDetailService productDetailService;
    @Value("${project.pic_pre}")
    private String pic_pre;

    @Login
    @ApiOperation(value = "获取购物车列表数据接口")
    @GetMapping("getInfo")
    public R getInfo(@ApiIgnore @RequestAttribute("userId") long userId){
        List<ShoppingCartEntity> shoppingCartEntityList = shoppingCartService.getMyData(userId);

        BigDecimal totalPrice = new BigDecimal("0.00");
        for (ShoppingCartEntity shoppingCartEntity:shoppingCartEntityList){
            totalPrice = totalPrice.add(shoppingCartEntity.getDetailPrice());
            shoppingCartEntity.setDetailCover(pic_pre + shoppingCartEntity.getDetailCover());
        }

        totalPrice = totalPrice.setScale(2,BigDecimal.ROUND_HALF_UP);

        Map<String,Object> map = new HashMap<>();
        map.put("totalPrice",totalPrice);
        map.put("arrayData",shoppingCartEntityList);
        return R.ok(map);
    }

    /**
     * 保存/修改
     */
    @ApiOperation(value = "添加/修改购物车接口")
    @PostMapping("/saveOrUpdate")
    public R save(@RequestBody ShoppingCartForm form,@ApiIgnore @RequestAttribute("userId") long userId){
        ValidatorUtils.validateEntity(form);

        ProductDetailEntity productDetailEntity = productDetailService.getById(form.getProductDetailId());

        if(productDetailEntity == null){
            return R.error(-1,"商品不存在");
        }

        ProductEntity productEntity = productService.getById(productDetailEntity.getProductId());

        if(form.getId() == null){
            ShoppingCartEntity shoppingCartEntity = ShoppingCartEntity.builder().userId(userId).productId(productDetailEntity.getProductId())
                    .productDetailId(form.getProductDetailId()).productName(productEntity.getProductName())
                    .detailCover(productDetailEntity.getDetailCover()).detailName(productDetailEntity.getDetailName())
                    .detailPrice(productDetailEntity.getDetailPrice()).detailSize(productDetailEntity.getDetailSize())
                    .detailTaste(productDetailEntity.getDetailTaste()).buyNum(form.getBuyNum()).createTime(new Date()).build();

            shoppingCartService.save(shoppingCartEntity);
        }else{
            ShoppingCartEntity shoppingCartEntity = shoppingCartService.getById(form.getId());
            shoppingCartEntity.setBuyNum(form.getBuyNum());
            shoppingCartService.updateById(shoppingCartEntity);
        }

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("delete")
    @ApiModelProperty(value = "删除购物车中的商品接口")
    public R delete(@RequestBody @ApiParam(value = "购物车ID",required = true)long id){
        shoppingCartService.removeById(id);

        return R.ok();
    }
}
