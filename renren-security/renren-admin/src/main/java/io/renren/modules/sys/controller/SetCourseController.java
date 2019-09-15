package io.renren.modules.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.entity.SetCourseEntity;
import io.renren.modules.sys.entity.SetCourseItemEntity;
import io.renren.modules.sys.entity.SetTypeEntity;
import io.renren.modules.sys.service.SetCourseItemService;
import io.renren.modules.sys.service.SetCourseService;
import io.renren.modules.sys.service.SetTypeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



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
    @Resource
    private SetTypeService setTypeService;
    @Resource
    private SetCourseItemService setCourseItemService;

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
        List<SetCourseItemEntity> detailList = setCourseItemService.list(new QueryWrapper<SetCourseItemEntity>().eq("set_course_id",id));
        Map<String,Object> map = new HashMap<>();
        map.put("detailList",detailList);
        map.put("setCourse",setCourse);
        return R.ok(map);
    }

    /**
     * 获取所有课程类别
     */
    @RequestMapping("/cateList")
    public R cateList(){
        List<SetTypeEntity> typeEntityList = setTypeService.list();

        return R.ok().put("typeList", typeEntityList);
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
