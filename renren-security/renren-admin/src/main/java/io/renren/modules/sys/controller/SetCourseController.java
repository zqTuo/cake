package io.renren.modules.sys.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.dto.ComboCourseItemDto;
import io.renren.modules.sys.entity.ComboCourseEntity;
import io.renren.modules.sys.entity.ComboCourseItemEntity;
import io.renren.modules.sys.entity.ComboTypeEntity;
import io.renren.modules.sys.form.SetCourseForm;
import io.renren.modules.sys.service.ComboCourseItemService;
import io.renren.modules.sys.service.ComboCourseService;
import io.renren.modules.sys.service.ComboTypeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private ComboCourseService comboCourseService;
    @Resource
    private ComboTypeService comboTypeService;
    @Resource
    private ComboCourseItemService comboCourseItemService;
    @Value("${project.pic_pre}")
    private String pic_pre;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:setcourse:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = comboCourseService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:setcourse:info")
    public R info(@PathVariable("id") Long id){
        ComboCourseEntity setCourse = comboCourseService.getById(id);
        setCourse.setPicUrl(pic_pre + setCourse.getPicUrl());

        List<ComboCourseItemDto> detailList = comboCourseItemService.getList(id);
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
        List<ComboTypeEntity> typeEntityList = comboTypeService.list();

        return R.ok().put("typeList", typeEntityList);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:setcourse:save")
    public R save(@RequestBody SetCourseForm form){
        ValidatorUtils.validateEntity(form);
        if(form.getDetailList().size() == 0){
            return R.error("请添加套餐课程详情");
        }

        ComboCourseEntity setCourse = form.getSetCourse();
        setCourse.setPicUrl(setCourse.getPicUrl().replaceAll(pic_pre,""));
        comboCourseService.save(setCourse);

        for(ComboCourseItemEntity setCourseItemEntity:form.getDetailList()){
            setCourseItemEntity.setComboCourseId(setCourse.getId());
        }

        comboCourseItemService.saveBatch(form.getDetailList());

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:setcourse:update")
    public R update(@RequestBody SetCourseForm form){
        ValidatorUtils.validateEntity(form);
        if(form.getDetailList().size() == 0){
            return R.error("请添加套餐课程详情");
        }

        ComboCourseEntity setCourse = form.getSetCourse();
        setCourse.setPicUrl(setCourse.getPicUrl().replaceAll(pic_pre,""));
        comboCourseService.updateById(setCourse);

        for(ComboCourseItemEntity setCourseItemEntity:form.getDetailList()){
            if(setCourseItemEntity.getId() != null && setCourseItemEntity.getId() > 0){
                comboCourseItemService.updateById(setCourseItemEntity);
            }else{
                setCourseItemEntity.setComboCourseId(setCourse.getId());
                comboCourseItemService.save(setCourseItemEntity);
            }
        }

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:setcourse:delete")
    public R delete(@RequestBody Long[] ids){
        comboCourseService.removeByIds(Arrays.asList(ids));
        comboCourseItemService.removeByCourseIds(ids);
        return R.ok();
    }

    /**
     * 删除套餐课程详情
     */
    @RequestMapping("/deleteItem")
    public R deleteItem(@RequestBody Long id){
        comboCourseItemService.removeById(id);

        return R.ok();
    }

}
