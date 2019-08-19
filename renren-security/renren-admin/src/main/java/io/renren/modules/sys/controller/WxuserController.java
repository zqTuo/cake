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

import io.renren.modules.sys.entity.WxuserEntity;
import io.renren.modules.sys.service.WxuserService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 用户表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-19 16:35:41
 */
@RestController
@RequestMapping("sys/wxuser")
public class WxuserController {
    @Autowired
    private WxuserService wxuserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:wxuser:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = wxuserService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:wxuser:info")
    public R info(@PathVariable("id") Long id){
        WxuserEntity wxuser = wxuserService.getById(id);

        return R.ok().put("wxuser", wxuser);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:wxuser:save")
    public R save(@RequestBody WxuserEntity wxuser){
        wxuserService.save(wxuser);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:wxuser:update")
    public R update(@RequestBody WxuserEntity wxuser){
        ValidatorUtils.validateEntity(wxuser);
        wxuserService.updateById(wxuser);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:wxuser:delete")
    public R delete(@RequestBody Long[] ids){
        wxuserService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
