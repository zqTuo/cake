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

import io.renren.modules.sys.entity.ShopOrderItemEntity;
import io.renren.modules.sys.service.ShopOrderItemService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 订单详情表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-19 16:35:41
 */
@RestController
@RequestMapping("sys/shoporderitem")
public class ShopOrderItemController {
    @Autowired
    private ShopOrderItemService shopOrderItemService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:shoporderitem:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = shopOrderItemService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:shoporderitem:info")
    public R info(@PathVariable("id") Long id){
        ShopOrderItemEntity shopOrderItem = shopOrderItemService.getById(id);

        return R.ok().put("shopOrderItem", shopOrderItem);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:shoporderitem:save")
    public R save(@RequestBody ShopOrderItemEntity shopOrderItem){
        shopOrderItemService.save(shopOrderItem);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:shoporderitem:update")
    public R update(@RequestBody ShopOrderItemEntity shopOrderItem){
        ValidatorUtils.validateEntity(shopOrderItem);
        shopOrderItemService.updateById(shopOrderItem);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:shoporderitem:delete")
    public R delete(@RequestBody Long[] ids){
        shopOrderItemService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
