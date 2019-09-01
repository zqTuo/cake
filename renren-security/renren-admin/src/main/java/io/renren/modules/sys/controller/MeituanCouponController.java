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

import io.renren.modules.sys.entity.MeituanCouponEntity;
import io.renren.modules.sys.service.MeituanCouponService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 美团券验券记录表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-01 14:26:01
 */
@RestController
@RequestMapping("sys/meituancoupon")
public class MeituanCouponController {
    @Autowired
    private MeituanCouponService meituanCouponService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:meituancoupon:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = meituanCouponService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:meituancoupon:info")
    public R info(@PathVariable("id") Long id){
        MeituanCouponEntity meituanCoupon = meituanCouponService.getById(id);

        return R.ok().put("meituanCoupon", meituanCoupon);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:meituancoupon:save")
    public R save(@RequestBody MeituanCouponEntity meituanCoupon){
        meituanCouponService.save(meituanCoupon);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:meituancoupon:update")
    public R update(@RequestBody MeituanCouponEntity meituanCoupon){
        ValidatorUtils.validateEntity(meituanCoupon);
        meituanCouponService.updateById(meituanCoupon);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:meituancoupon:delete")
    public R delete(@RequestBody Long[] ids){
        meituanCouponService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
