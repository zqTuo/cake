package io.renren.modules.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.entity.ProductDetailEntity;
import io.renren.modules.sys.entity.ProductSizeEntity;
import io.renren.modules.sys.entity.ProductTasteEntity;
import io.renren.modules.sys.service.ProductDetailService;
import io.renren.modules.sys.service.ProductSizeService;
import io.renren.modules.sys.service.ProductTasteService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;



/**
 * 商品详情表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 21:51:22
 */
@RestController
@RequestMapping("sys/productdetail")
public class ProductDetailController {
    @Autowired
    private ProductDetailService productDetailService;
    @Autowired
    private ProductSizeService productSizeService;
    @Autowired
    private ProductTasteService productTasteService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:productdetail:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = productDetailService.queryPage(params);

        return R.ok().put("page", page);
    }

    @RequestMapping("/sizeList")
    public R sizeList(){
        List<ProductSizeEntity> sizeEntityList = productSizeService.list();
        return R.ok().put("sizeList",sizeEntityList);
    }

    @RequestMapping("/tasteList")
    public R tasteList(){
        List<ProductTasteEntity> tasteEntityList = productTasteService.list();
        return R.ok().put("tasteList",tasteEntityList);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:productdetail:info")
    public R info(@PathVariable("id") Long id){
        ProductDetailEntity productDetail = productDetailService.getById(id);

        return R.ok().put("productDetail", productDetail);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:productdetail:save")
    public R save(@RequestBody ProductDetailEntity productDetail){
        productDetailService.save(productDetail);

        return R.ok();
    }

    /**
     * 检测口味和尺寸是否存在该商品中
     */
    @RequestMapping("/saveSizeOrTaste")
    @RequiresPermissions("sys:productdetail:save")
    public R saveSizeOrTaste(@RequestBody ProductDetailEntity productDetail){
        ProductSizeEntity sizeEntity = productSizeService.getOne(
                new QueryWrapper<ProductSizeEntity>()
                        .eq("product_id",productDetail.getProductId())
                        .eq("title",productDetail.getDetailSize()));

        if(sizeEntity == null){
            sizeEntity = ProductSizeEntity.builder().title(productDetail.getDetailSize())
                    .productId(productDetail.getProductId()).build();
            productSizeService.save(sizeEntity);
        }

        ProductTasteEntity tasteEntity = productTasteService.getOne(
                new QueryWrapper<ProductTasteEntity>()
                        .eq("product_id",productDetail.getProductId())
                        .eq("title",productDetail.getDetailSize()));

        if(tasteEntity == null){
            tasteEntity = ProductTasteEntity.builder().title(productDetail.getDetailSize())
                    .productId(productDetail.getProductId()).build();
            productTasteService.save(tasteEntity);
        }

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:productdetail:update")
    public R update(@RequestBody ProductDetailEntity productDetail){
        ValidatorUtils.validateEntity(productDetail);
        productDetailService.updateById(productDetail);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:productdetail:delete")
    public R delete(@RequestBody Long[] ids){
        productDetailService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }


}
