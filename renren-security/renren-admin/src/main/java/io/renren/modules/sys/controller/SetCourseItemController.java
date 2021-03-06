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

import io.renren.modules.sys.entity.ComboCourseItemEntity;
import io.renren.modules.sys.service.ComboCourseItemService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 套餐课程表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-12 15:49:25
 */
@RestController
@RequestMapping("sys/setcourseitem")
public class SetCourseItemController {
    @Autowired
    private ComboCourseItemService comboCourseItemService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:setcourseitem:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = comboCourseItemService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:setcourseitem:info")
    public R info(@PathVariable("id") Long id){
        ComboCourseItemEntity setCourseItem = comboCourseItemService.getById(id);

        return R.ok().put("setCourseItem", setCourseItem);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:setcourseitem:save")
    public R save(@RequestBody ComboCourseItemEntity setCourseItem){
        comboCourseItemService.save(setCourseItem);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:setcourseitem:update")
    public R update(@RequestBody ComboCourseItemEntity setCourseItem){
        ValidatorUtils.validateEntity(setCourseItem);
        comboCourseItemService.updateById(setCourseItem);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:setcourseitem:delete")
    public R delete(@RequestBody Long[] ids){
        comboCourseItemService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
