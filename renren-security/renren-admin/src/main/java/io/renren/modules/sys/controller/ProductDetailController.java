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

import io.renren.modules.sys.entity.ProductDetailEntity;
import io.renren.modules.sys.service.ProductDetailService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 商品详情表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-19 16:35:41
 */
@RestController
@RequestMapping("sys/productdetail")
public class ProductDetailController {
    @Autowired
    private ProductDetailService productDetailService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:productdetail:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = productDetailService.queryPage(params);

        return R.ok().put("page", page);
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
