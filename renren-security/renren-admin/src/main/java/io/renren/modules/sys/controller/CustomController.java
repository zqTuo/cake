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

import io.renren.modules.sys.entity.CustomEntity;
import io.renren.modules.sys.service.CustomService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 微信客服表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-03 11:34:56
 */
@RestController
@RequestMapping("sys/custom")
public class CustomController {
    @Autowired
    private CustomService customService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:custom:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = customService.queryPage(params);


        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:custom:info")
    public R info(@PathVariable("id") Long id){
        CustomEntity custom = customService.getById(id);

        return R.ok().put("custom", custom);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:custom:save")
    public R save(@RequestBody CustomEntity custom){
        customService.save(custom);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:custom:update")
    public R update(@RequestBody CustomEntity custom){
        ValidatorUtils.validateEntity(custom);
        customService.updateById(custom);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:custom:delete")
    public R delete(@RequestBody Long[] ids){
        customService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
