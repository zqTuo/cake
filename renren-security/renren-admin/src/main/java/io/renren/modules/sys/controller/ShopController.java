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

import io.renren.modules.sys.entity.ShopEntity;
import io.renren.modules.sys.service.ShopService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 门店表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-19 16:35:41
 */
@RestController
@RequestMapping("sys/shop")
public class ShopController {
    @Autowired
    private ShopService shopService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:shop:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = shopService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:shop:info")
    public R info(@PathVariable("id") Long id){
        ShopEntity shop = shopService.getById(id);

        return R.ok().put("shop", shop);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:shop:save")
    public R save(@RequestBody ShopEntity shop){
        shopService.save(shop);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:shop:update")
    public R update(@RequestBody ShopEntity shop){
        ValidatorUtils.validateEntity(shop);
        shopService.updateById(shop);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:shop:delete")
    public R delete(@RequestBody Long[] ids){
        shopService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
