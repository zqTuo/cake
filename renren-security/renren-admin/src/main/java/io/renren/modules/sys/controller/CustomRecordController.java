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

import io.renren.modules.sys.entity.CustomRecordEntity;
import io.renren.modules.sys.service.CustomRecordService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 客服接待记录表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-03 11:34:56
 */
@RestController
@RequestMapping("sys/customrecord")
public class CustomRecordController {
    @Autowired
    private CustomRecordService customRecordService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:customrecord:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = customRecordService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:customrecord:info")
    public R info(@PathVariable("id") Long id){
        CustomRecordEntity customRecord = customRecordService.getById(id);

        return R.ok().put("customRecord", customRecord);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:customrecord:save")
    public R save(@RequestBody CustomRecordEntity customRecord){
        customRecordService.save(customRecord);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:customrecord:update")
    public R update(@RequestBody CustomRecordEntity customRecord){
        ValidatorUtils.validateEntity(customRecord);
        customRecordService.updateById(customRecord);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:customrecord:delete")
    public R delete(@RequestBody Long[] ids){
        customRecordService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
