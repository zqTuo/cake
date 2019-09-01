package io.renren.modules.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.entity.*;
import io.renren.modules.sys.form.ProductForm;
import io.renren.modules.sys.service.*;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.*;



/**
 * 商品表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-25 22:57:11
 */
@RestController
@RequestMapping("sys/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductDetailService productDetailService;
    @Autowired
    private ProductTasteService productTasteService;
    @Autowired
    private ProductSizeService productSizeService;

    @Value("${project.pic_pre}")
    private String pic_pre;
    @Value("${project.video_pre}")
    private String video_pre;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:product:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = productService.findByPage(params);

        return R.ok().put("page", page);
    }

    @RequestMapping("/getInfo/{id}")
    public R getInfo(@PathVariable("id")long id){
        List<ProductEntity> list = productService.list(
                new QueryWrapper<ProductEntity>().eq("product_hot_id",id));

        for (ProductEntity productEntity:list){
            productEntity.setProductImg(pic_pre + productEntity.getProductImg());
        }

        return R.ok().put("list",list);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:product:info")
    public R info(@PathVariable("id") Long id){
        ProductEntity product = productService.getById(id);
        product.setProductImg(pic_pre + product.getProductImg());

        if(StringUtils.isNotBlank(product.getProductVideo())){
            product.setProductVideo(video_pre + product.getProductVideo());
        }
        
        String[] bannerArr = product.getProductBanner().split(",");
        for (int i = 0; i < bannerArr.length; i++) {
            if(!bannerArr[i].equals("")){
                bannerArr[i] = pic_pre + bannerArr[i];
            }
        }

        List<ProductDetailEntity> productDetailEntityList = productDetailService.list(new QueryWrapper<ProductDetailEntity>().eq("product_id",id));

        for (ProductDetailEntity productDetailEntity:productDetailEntityList){
            productDetailEntity.setDetailCover(pic_pre + productDetailEntity.getDetailCover());
        }

        List<String> sizeList = productSizeService.findDetail(id);

        List<String> tasteList = productTasteService.findDetail(id);

        Map<String,Object> map = new HashMap<>();
        map.put("bannerArr",bannerArr);
        map.put("product",product);
        map.put("sizeList",sizeList);
        map.put("tasteList",tasteList);
        map.put("productDetailEntityList",productDetailEntityList);
        return R.ok(map);
    }


    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:product:save")
    public R save(@RequestBody ProductForm form){
        ValidatorUtils.validateEntity(form);
        if(form.getProduct() == null || form.getSizeList() == null || form.getTasteList() == null){
            return R.error("缺少必传参数");
        }

        ProductEntity product = form.getProduct();
        product.setProductImg(product.getProductImg().replaceAll(pic_pre,""));
        product.setProductBanner(product.getProductBanner().replaceAll(pic_pre,""));
        if(StringUtils.isNotBlank(product.getProductVideo())){
            product.setProductVideo(product.getProductVideo().replaceFirst(video_pre,""));
        }

        product.setCreateTime(new Date());
        product.setUpdateBy(((SysUserEntity) SecurityUtils.getSubject().getPrincipal()).getUsername());
        productService.save(product);

        List<ProductDetailEntity> detailEntityList = form.getDetailList();
        for (ProductDetailEntity productDetailEntity:detailEntityList){
            productDetailEntity.setDetailCover(productDetailEntity.getDetailCover().replaceAll(pic_pre,""));
            productDetailEntity.setCreateTime(new Date());
            productDetailEntity.setUpdateBy(((SysUserEntity) SecurityUtils.getSubject().getPrincipal()).getUsername());
            productDetailEntity.setProductId(product.getId());
        }

        productDetailService.saveBatch(detailEntityList);

        List<ProductSizeEntity> sizeEntityList = new ArrayList<>();
        for (int i = 0; i < form.getSizeList().length; i++) {
            String size = form.getSizeList()[i];
            ProductSizeEntity sizeEntity = ProductSizeEntity.builder().productId(product.getId())
                    .title(size).build();
            sizeEntityList.add(sizeEntity);
        }
        productSizeService.saveBatch(sizeEntityList);

        List<ProductTasteEntity> tasteEntityArrayList = new ArrayList<>();
        for (int i = 0; i < form.getTasteList().length; i++) {
            String taste = form.getTasteList()[i];
            ProductTasteEntity tasteEntity = ProductTasteEntity.builder().productId(product.getId())
                    .title(taste).build();
            tasteEntityArrayList.add(tasteEntity);
        }
        productTasteService.saveBatch(tasteEntityArrayList);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:product:update")
    public R update(@RequestBody ProductForm form){
        ValidatorUtils.validateEntity(form);
        if(form.getProduct() == null || form.getSizeList() == null || form.getTasteList() == null){
            return R.error("缺少必传参数");
        }

        ProductEntity product = form.getProduct();
        if(StringUtils.isNotBlank(product.getProductVideo())){
            product.setProductVideo(product.getProductVideo().replaceFirst(video_pre,""));
        }

        product.setProductImg(product.getProductImg().replaceAll(pic_pre,""));
        product.setProductBanner(product.getProductBanner().replaceAll(pic_pre,""));
        product.setUpdateTime(new Date());
        product.setUpdateBy(((SysUserEntity) SecurityUtils.getSubject().getPrincipal()).getUsername());
        productService.updateById(product);

        List<ProductDetailEntity> detailEntityList = form.getDetailList();
        for (ProductDetailEntity productDetailEntity:detailEntityList){
            productDetailEntity.setDetailCover(productDetailEntity.getDetailCover().replaceAll(pic_pre,""));
            productDetailEntity.setUpdateTime(new Date());
            productDetailEntity.setUpdateBy(((SysUserEntity) SecurityUtils.getSubject().getPrincipal()).getUsername());
            productDetailEntity.setProductId(product.getId());
        }

        productDetailService.updateBatchById(detailEntityList);

        productSizeService.remove(new QueryWrapper<ProductSizeEntity>().eq("product_id",product.getId()));
        productTasteService.remove(new QueryWrapper<ProductTasteEntity>().eq("product_id",product.getId()));

        List<ProductSizeEntity> sizeEntityList = new ArrayList<>();
        for (int i = 0; i < form.getSizeList().length; i++) {
            String size = form.getSizeList()[i];
            ProductSizeEntity sizeEntity = ProductSizeEntity.builder().productId(product.getId())
                    .title(size).build();
            sizeEntityList.add(sizeEntity);
        }
        productSizeService.saveBatch(sizeEntityList);

        List<ProductTasteEntity> tasteEntityArrayList = new ArrayList<>();
        for (int i = 0; i < form.getTasteList().length; i++) {
            String taste = form.getTasteList()[i];
            ProductTasteEntity tasteEntity = ProductTasteEntity.builder().productId(product.getId())
                    .title(taste).build();
            tasteEntityArrayList.add(tasteEntity);
        }
        productTasteService.saveBatch(tasteEntityArrayList);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:product:delete")
    public R delete(@RequestBody Long[] ids){
        productService.delPros(ids);
        return R.ok();
    }

}
