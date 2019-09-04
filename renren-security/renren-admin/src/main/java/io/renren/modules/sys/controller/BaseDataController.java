package io.renren.modules.sys.controller;

import java.util.Arrays;
import java.util.Map;

import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.service.IRedisService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.sys.entity.BaseDataEntity;
import io.renren.modules.sys.service.BaseDataService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;

import javax.annotation.Resource;


/**
 * 系统配置表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 16:22:06
 */
@RestController
@RequestMapping("sys/basedata")
public class BaseDataController {
    @Autowired
    private BaseDataService baseDataService;


    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:basedata:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = baseDataService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:basedata:info")
    public R info(@PathVariable("id") Long id){
        BaseDataEntity baseData = baseDataService.getById(id);

        return R.ok().put("baseData", baseData);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:basedata:save")
    public R save(@RequestBody BaseDataEntity baseData){
        baseDataService.save(baseData);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:basedata:update")
    public R update(@RequestBody BaseDataEntity baseData){
        ValidatorUtils.validateEntity(baseData);
        baseDataService.updateById(baseData);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:basedata:delete")
    public R delete(@RequestBody Long[] ids){
        baseDataService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
