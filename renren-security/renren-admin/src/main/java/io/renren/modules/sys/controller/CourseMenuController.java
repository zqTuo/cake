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

import io.renren.modules.sys.entity.CourseMenuEntity;
import io.renren.modules.sys.service.CourseMenuService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 课程菜单表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-17 09:35:40
 */
@RestController
@RequestMapping("sys/coursemenu")
public class CourseMenuController {
    @Autowired
    private CourseMenuService courseMenuService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:coursemenu:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = courseMenuService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:coursemenu:info")
    public R info(@PathVariable("id") Long id){
        CourseMenuEntity courseMenu = courseMenuService.getById(id);

        return R.ok().put("courseMenu", courseMenu);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:coursemenu:save")
    public R save(@RequestBody CourseMenuEntity courseMenu){
        courseMenuService.save(courseMenu);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:coursemenu:update")
    public R update(@RequestBody CourseMenuEntity courseMenu){
        ValidatorUtils.validateEntity(courseMenu);
        courseMenuService.updateById(courseMenu);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:coursemenu:delete")
    public R delete(@RequestBody Long[] ids){
        courseMenuService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
