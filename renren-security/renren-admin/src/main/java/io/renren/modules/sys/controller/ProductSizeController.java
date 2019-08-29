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

import io.renren.modules.sys.entity.ProductSizeEntity;
import io.renren.modules.sys.service.ProductSizeService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 商品尺寸表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-29 05:38:16
 */
@RestController
@RequestMapping("sys/productsize")
public class ProductSizeController {
    @Autowired
    private ProductSizeService productSizeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:productsize:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = productSizeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:productsize:info")
    public R info(@PathVariable("id") Long id){
        ProductSizeEntity productSize = productSizeService.getById(id);

        return R.ok().put("productSize", productSize);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:productsize:save")
    public R save(@RequestBody ProductSizeEntity productSize){
        productSizeService.save(productSize);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:productsize:update")
    public R update(@RequestBody ProductSizeEntity productSize){
        ValidatorUtils.validateEntity(productSize);
        productSizeService.updateById(productSize);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:productsize:delete")
    public R delete(@RequestBody Long[] ids){
        productSizeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
