package io.renren.modules.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.entity.ShopOrderEntity;
import io.renren.modules.sys.entity.ShopOrderItemEntity;
import io.renren.modules.sys.service.ShopOrderItemService;
import io.renren.modules.sys.service.ShopOrderService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
    @Resource
    private ShopOrderItemService shopOrderItemService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:shoporder:list")
    public R list(@RequestParam Map<String, Object> params){
        if(params.get("searchVal") != null){
            try {
                String searchVal = URLDecoder.decode(params.get("searchVal").toString(),"utf-8");
                searchVal = "%" + searchVal + "%";
                params.put("searchVal",searchVal);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
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
        List<ShopOrderItemEntity> detailList = shopOrderItemService.
                list(new QueryWrapper<ShopOrderItemEntity>().eq("order_no",shopOrder.getOrderNo()));

        Map<String,Object> map = new HashMap<>();
        map.put("shopOrder", shopOrder);
        map.put("detailList", detailList);
        return R.ok(map);
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

}
