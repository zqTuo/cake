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

import io.renren.modules.sys.entity.ProductTasteEntity;
import io.renren.modules.sys.service.ProductTasteService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 商品口味表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-29 05:38:16
 */
@RestController
@RequestMapping("sys/producttaste")
public class ProductTasteController {
    @Autowired
    private ProductTasteService productTasteService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:producttaste:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = productTasteService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:producttaste:info")
    public R info(@PathVariable("id") Long id){
        ProductTasteEntity productTaste = productTasteService.getById(id);

        return R.ok().put("productTaste", productTaste);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:producttaste:save")
    public R save(@RequestBody ProductTasteEntity productTaste){
        productTasteService.save(productTaste);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:producttaste:update")
    public R update(@RequestBody ProductTasteEntity productTaste){
        ValidatorUtils.validateEntity(productTaste);
        productTasteService.updateById(productTaste);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:producttaste:delete")
    public R delete(@RequestBody Long[] ids){
        productTasteService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
