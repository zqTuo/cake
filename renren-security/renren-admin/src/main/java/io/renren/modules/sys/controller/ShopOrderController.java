package io.renren.modules.sys.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.entity.ShopOrderEntity;
import io.renren.modules.sys.service.ShopOrderService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 订单表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 16:22:06
 */
@RestController
@RequestMapping("sys/shoporder")
public class ShopOrderController {
    @Autowired
    private ShopOrderService shopOrderService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:shoporder:list")
    public R list(@RequestParam Map<String, Object> params){

        PageUtils page = shopOrderService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:shoporder:info")
    public R info(@PathVariable("id") Long id){
        ShopOrderEntity shopOrder = shopOrderService.getById(id);

        return R.ok().put("shopOrder", shopOrder);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:shoporder:save")
    public R save(@RequestBody ShopOrderEntity shopOrder){
        shopOrderService.save(shopOrder);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:shoporder:update")
    public R update(@RequestBody ShopOrderEntity shopOrder){
        ValidatorUtils.validateEntity(shopOrder);
        shopOrderService.updateById(shopOrder);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:shoporder:delete")
    public R delete(@RequestBody Long[] ids){
        shopOrderService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
