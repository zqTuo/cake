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

import io.renren.modules.sys.entity.CouponUserEntity;
import io.renren.modules.sys.service.CouponUserService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 优惠券用户表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 16:22:06
 */
@RestController
@RequestMapping("sys/couponuser")
public class CouponUserController {
    @Autowired
    private CouponUserService couponUserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:couponuser:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = couponUserService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:couponuser:info")
    public R info(@PathVariable("id") Long id){
        CouponUserEntity couponUser = couponUserService.getById(id);

        return R.ok().put("couponUser", couponUser);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:couponuser:save")
    public R save(@RequestBody CouponUserEntity couponUser){
        couponUserService.save(couponUser);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:couponuser:update")
    public R update(@RequestBody CouponUserEntity couponUser){
        ValidatorUtils.validateEntity(couponUser);
        couponUserService.updateById(couponUser);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:couponuser:delete")
    public R delete(@RequestBody Long[] ids){
        couponUserService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
