package io.renren.modules.sys.controller;

import com.alibaba.fastjson.JSONObject;
import io.renren.common.utils.LocationUtils;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.entity.ShopEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.ShopService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;



/**
 * 门店表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 16:22:06
 */
@RestController
@RequestMapping("sys/shop")
public class ShopController {
    @Autowired
    private ShopService shopService;
    @Resource
    private LocationUtils locationUtils;

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
        //获取经纬度
        JSONObject addrRes = locationUtils.getLocation(shop.getShopAddr());

        if(addrRes.getInteger("status") == 0) {
            JSONObject locaJson = addrRes.getJSONArray("results").getJSONObject(0).getJSONObject("location");
            shop.setShopLongitude(locaJson.getString("lng"));
            shop.setShopLatitude(locaJson.getString("lat"));
        }else{
            return R.error(-3,addrRes.getString("message"));
        }

        shop.setCreateTime(new Date());
        shop.setUpdateBy(((SysUserEntity) SecurityUtils.getSubject().getPrincipal()).getUsername());

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

        //获取经纬度
        JSONObject addrRes = locationUtils.getLocation(shop.getShopAddr());

        if(addrRes.getInteger("status") == 0) {
            JSONObject locaJson = addrRes.getJSONObject("location");
            shop.setShopLongitude(locaJson.getString("lng"));
            shop.setShopLatitude(locaJson.getString("lat"));
        }else{
            return R.error(-3,addrRes.getString("message"));
        }

        shop.setUpdateTime(new Date());
        shop.setUpdateBy(((SysUserEntity) SecurityUtils.getSubject().getPrincipal()).getUsername());

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
