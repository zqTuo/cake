package io.renren.modules.sys.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import io.renren.common.validator.ValidatorUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.sys.entity.CourseEntity;
import io.renren.modules.sys.service.CourseService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 蛋糕课程表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-09 18:28:51
 */
@RestController
@RequestMapping("sys/course")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Value("${project.pic_pre}")
    private String pic_pre;
    @Value("${project.video_pre}")
    private String video_pre;


    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:course:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = courseService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:course:info")
    public R info(@PathVariable("id") Long id){
        CourseEntity course = courseService.getById(id);

        course.setCourseImg(pic_pre + course.getCourseImg());

        if(StringUtils.isNotBlank(course.getCourseVideo())){
            course.setCourseVideo(video_pre + course.getCourseVideo());
        }

        String[] bannerArr = course.getCourseBanner().split(",");
        for (int i = 0; i < bannerArr.length; i++) {
            if(!bannerArr[i].equals("")){
                bannerArr[i] = pic_pre + bannerArr[i];
            }
        }

        Map<String,Object> map = new HashMap<>();
        map.put("course",course);
        map.put("bannerArr",bannerArr);
        return R.ok(map);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:course:save")
    public R save(@RequestBody CourseEntity course){
        courseService.save(course);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:course:update")
    public R update(@RequestBody CourseEntity course){
        ValidatorUtils.validateEntity(course);
        courseService.updateById(course);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:course:delete")
    public R delete(@RequestBody Long[] ids){
        courseService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
