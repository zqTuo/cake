package io.renren.modules.sys.controller;

import java.util.*;

import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.entity.ProductDetailEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.form.ProductForm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.sys.entity.ProductEntity;
import io.renren.modules.sys.service.ProductService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



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

    @Value("${project.pic_pre}")
    private String pic_pre;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:product:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = productService.findByPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:product:info")
    public R info(@PathVariable("id") Long id){
        ProductEntity product = productService.getById(id);
        product.setProductImg(pic_pre + product.getProductImg());
        
        String[] bannerArr = product.getProductBanner().split(",");
        for (int i = 0; i < bannerArr.length; i++) {
            if(!bannerArr[i].equals("")){
                bannerArr[i] = pic_pre + bannerArr[i];
            }
        }

        Map<String,Object> map = new HashMap<>();
        map.put("bannerArr",bannerArr);
        map.put("product",product);
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

        product.setCreateTime(new Date());
        product.setUpdateBy(((SysUserEntity) SecurityUtils.getSubject().getPrincipal()).getUsername());
        productService.save(product);

        List<ProductDetailEntity> detailEntityList = form.getDetailList();

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:product:update")
    public R update(@RequestBody ProductEntity product){
        ValidatorUtils.validateEntity(product);

        product.setProductImg(product.getProductImg().replaceAll(pic_pre,""));
        product.setProductBanner(product.getProductBanner().replaceAll(pic_pre,""));

        product.setUpdateTime(new Date());
        product.setUpdateBy(((SysUserEntity) SecurityUtils.getSubject().getPrincipal()).getUsername());
        productService.updateById(product);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:product:delete")
    public R delete(@RequestBody Long[] ids){
        productService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
