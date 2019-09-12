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

import io.renren.modules.sys.entity.SetCourseEntity;
import io.renren.modules.sys.service.SetCourseService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 课程套餐表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-12 15:49:25
 */
@RestController
@RequestMapping("sys/setcourse")
public class SetCourseController {
    @Autowired
    private SetCourseService setCourseService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:setcourse:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = setCourseService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:setcourse:info")
    public R info(@PathVariable("id") Long id){
        SetCourseEntity setCourse = setCourseService.getById(id);

        return R.ok().put("setCourse", setCourse);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:setcourse:save")
    public R save(@RequestBody SetCourseEntity setCourse){
        setCourseService.save(setCourse);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:setcourse:update")
    public R update(@RequestBody SetCourseEntity setCourse){
        ValidatorUtils.validateEntity(setCourse);
        setCourseService.updateById(setCourse);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:setcourse:delete")
    public R delete(@RequestBody Long[] ids){
        setCourseService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
