package io.renren.modules.sys.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.entity.SysUserEntity;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.sys.entity.BannerEntity;
import io.renren.modules.sys.service.BannerService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * banner表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 18:11:13
 */
@RestController
@RequestMapping("sys/banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;
    @Value("${project.pic_pre}")
    private String pic_pre;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:banner:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = bannerService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:banner:info")
    public R info(@PathVariable("id") Long id){
        BannerEntity banner = bannerService.getById(id);
        banner.setBannerPic(pic_pre + banner.getBannerPic());
        return R.ok().put("banner", banner);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:banner:save")
    public R save(@RequestBody BannerEntity banner){
        banner.setBannerPic(banner.getBannerPic().replaceFirst(pic_pre,""));
        banner.setCreateTime(new Date());
        banner.setUpdateBy(((SysUserEntity) SecurityUtils.getSubject().getPrincipal()).getUsername());
        bannerService.save(banner);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:banner:update")
    public R update(@RequestBody BannerEntity banner){
        ValidatorUtils.validateEntity(banner);
        banner.setBannerPic(banner.getBannerPic().replaceFirst(pic_pre,""));
        banner.setUpdateTime(new Date());
        banner.setUpdateBy(((SysUserEntity) SecurityUtils.getSubject().getPrincipal()).getUsername());
        bannerService.updateById(banner);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:banner:delete")
    public R delete(@RequestBody Long[] ids){
        bannerService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
