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

import io.renren.modules.sys.entity.ShoppingCartEntity;
import io.renren.modules.sys.service.ShoppingCartService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 购物车表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-19 16:35:41
 */
@RestController
@RequestMapping("sys/shoppingcart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:shoppingcart:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = shoppingCartService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:shoppingcart:info")
    public R info(@PathVariable("id") Long id){
        ShoppingCartEntity shoppingCart = shoppingCartService.getById(id);

        return R.ok().put("shoppingCart", shoppingCart);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:shoppingcart:save")
    public R save(@RequestBody ShoppingCartEntity shoppingCart){
        shoppingCartService.save(shoppingCart);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:shoppingcart:update")
    public R update(@RequestBody ShoppingCartEntity shoppingCart){
        ValidatorUtils.validateEntity(shoppingCart);
        shoppingCartService.updateById(shoppingCart);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:shoppingcart:delete")
    public R delete(@RequestBody Long[] ids){
        shoppingCartService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
