package io.renren.modules.sys.controller;

import java.util.Arrays;
import java.util.Map;

import io.renren.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.sys.entity.ProductCategoryEntity;
import io.renren.modules.sys.service.ProductCategoryService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 商品种类表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 16:22:06
 */
@RestController
@RequestMapping("sys/productcategory")
public class ProductCategoryController {
    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:productcategory:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = productCategoryService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:productcategory:info")
    public R info(@PathVariable("id") Long id){
        ProductCategoryEntity productCategory = productCategoryService.getById(id);

        return R.ok().put("productCategory", productCategory);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:productcategory:save")
    public R save(@RequestBody ProductCategoryEntity productCategory){
        productCategoryService.save(productCategory);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:productcategory:update")
    public R update(@RequestBody ProductCategoryEntity productCategory){
        ValidatorUtils.validateEntity(productCategory);
        productCategoryService.updateById(productCategory);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:productcategory:delete")
    public R delete(@RequestBody Long[] ids){
        productCategoryService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
