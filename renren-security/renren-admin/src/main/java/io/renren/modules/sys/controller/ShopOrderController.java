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

import io.renren.modules.sys.entity.ShopOrderEntity;
import io.renren.modules.sys.service.ShopOrderService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 订单表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-19 16:35:41
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
