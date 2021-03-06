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

import io.renren.modules.sys.entity.ComboTypeEntity;
import io.renren.modules.sys.service.ComboTypeService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 套餐课程类别表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-12 15:49:25
 */
@RestController
@RequestMapping("sys/settype")
public class SetTypeController {
    @Autowired
    private ComboTypeService comboTypeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:settype:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = comboTypeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:settype:info")
    public R info(@PathVariable("id") Long id){
        ComboTypeEntity setType = comboTypeService.getById(id);

        return R.ok().put("setType", setType);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:settype:save")
    public R save(@RequestBody ComboTypeEntity setType){
        comboTypeService.save(setType);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:settype:update")
    public R update(@RequestBody ComboTypeEntity setType){
        ValidatorUtils.validateEntity(setType);
        comboTypeService.updateById(setType);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:settype:delete")
    public R delete(@RequestBody Long[] ids){
        comboTypeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
