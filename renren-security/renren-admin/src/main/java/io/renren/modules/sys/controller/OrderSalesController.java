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

import io.renren.modules.sys.entity.OrderSalesEntity;
import io.renren.modules.sys.service.OrderSalesService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 财务流水表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-28 21:42:09
 */
@RestController
@RequestMapping("sys/ordersales")
public class OrderSalesController {
    @Autowired
    private OrderSalesService orderSalesService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:ordersales:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = orderSalesService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:ordersales:info")
    public R info(@PathVariable("id") Long id){
        OrderSalesEntity orderSales = orderSalesService.getById(id);

        return R.ok().put("orderSales", orderSales);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:ordersales:save")
    public R save(@RequestBody OrderSalesEntity orderSales){
        orderSalesService.save(orderSales);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:ordersales:update")
    public R update(@RequestBody OrderSalesEntity orderSales){
        ValidatorUtils.validateEntity(orderSales);
        orderSalesService.updateById(orderSales);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:ordersales:delete")
    public R delete(@RequestBody Long[] ids){
        orderSalesService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
