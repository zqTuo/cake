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

import io.renren.modules.sys.entity.MeituanItemEntity;
import io.renren.modules.sys.service.MeituanItemService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 美团券包含商品信息表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-01 14:26:01
 */
@RestController
@RequestMapping("sys/meituanitem")
public class MeituanItemController {
    @Autowired
    private MeituanItemService meituanItemService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:meituanitem:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = meituanItemService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:meituanitem:info")
    public R info(@PathVariable("id") Long id){
        MeituanItemEntity meituanItem = meituanItemService.getById(id);

        return R.ok().put("meituanItem", meituanItem);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:meituanitem:save")
    public R save(@RequestBody MeituanItemEntity meituanItem){
        meituanItemService.save(meituanItem);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:meituanitem:update")
    public R update(@RequestBody MeituanItemEntity meituanItem){
        ValidatorUtils.validateEntity(meituanItem);
        meituanItemService.updateById(meituanItem);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:meituanitem:delete")
    public R delete(@RequestBody Long[] ids){
        meituanItemService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
