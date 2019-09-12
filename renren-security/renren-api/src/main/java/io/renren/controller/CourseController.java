package io.renren.controller;

import io.renren.common.result.Result;
import io.renren.common.utils.JodaTimeUtil;
import io.renren.common.validator.ValidatorUtils;
import io.renren.dto.ProductDto;
import io.renren.dto.SendTimeDto;
import io.renren.entity.CourseEntity;
import io.renren.form.PageForm;
import io.renren.form.SendTimeForm;
import io.renren.service.CourseService;
import io.renren.service.SendTimeService;
import io.renren.service.ShopOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/9/11 16:42
 */
@Api(tags = "课程类控制器")
@RestController
@RequestMapping("/api/course")
public class CourseController {
    private static Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @Value("${project.pic_pre}")
    private String pic_pre;
    @Value("${project.video_pre}")
    private String video_pre;

    @Resource
    private CourseService courseService;
    @Resource
    private SendTimeService sendTimeService;
    @Resource
    private ShopOrderService orderService;

    @PostMapping("courseInfo")
    @ApiOperation(value = "获取课程列表数据接口")
    public Result<CourseEntity> courseInfo(@RequestBody PageForm form){
        ValidatorUtils.validateEntity(form);

        form.setPageNo(form.getPageNo() > 0 ? (form.getPageNo()-1) * form.getPageSize() : 0);

        List<CourseEntity> courseEntityList = courseService.getDataByPage(form);

        for (CourseEntity courseEntity:courseEntityList){
            courseEntity.setCourseImg(pic_pre + courseEntity.getCourseImg());
        }
        return new Result<>().ok(courseEntityList);
    }

    @PostMapping("courseDetail")
    @ApiOperation(value = "获取课程详情数据接口",notes = "course课程数据参考课程列表接口，bannerArr为副图图片数组")
    public Result<HashMap> courseDetail(@RequestParam("id") @ApiParam(value = "课程ID",required = true)long id){
        CourseEntity courseEntity = courseService.getById(id);
        courseEntity.setCourseImg(pic_pre + courseEntity.getCourseImg());
        String[] bannerArr = courseEntity.getCourseBanner().split(",");
        for (int i = 0; i < bannerArr.length; i++) {
            if(!bannerArr[i].equals("")){
                bannerArr[i] = pic_pre + bannerArr[i];
            }
        }

        if(StringUtils.isNotBlank(courseEntity.getCourseVideo())){
            courseEntity.setCourseVideo(video_pre + courseEntity.getCourseVideo());
        }

        Map<String,Object> map = new HashMap<>();
        map.put("course",courseEntity);
        map.put("bannerArr",bannerArr);
        return new Result<>().ok(map);
    }

    @PostMapping("getSendTime")
    @ApiOperation(value = "获取课程到达时间接口")
    public Result<SendTimeDto> getSendTime(@RequestParam("selectedDate") @ApiParam(value = "派送日期，默认当天，格式yyyy-MM-dd",required = true)String selectedDate) throws ParseException {
        List<SendTimeDto> sendTimeEntityList = sendTimeService.getData();

        for (SendTimeDto sendTimeDto:sendTimeEntityList){
            //判断当前时间是否已经过了这个点了 selectedDate -> yyyy-MM-dd  startTime -> HH:mm
            if(JodaTimeUtil.isExpiredCur(selectedDate + " " + sendTimeDto.getStartTime())){
                sendTimeDto.setState(1);
                continue;
            }

            // 判断是否已经约满
            int curOrderNum = orderService.countToday(selectedDate + " " + sendTimeDto.getStartTime() ,selectedDate + " " + sendTimeDto.getEndTime(),1);
            if(curOrderNum >= sendTimeDto.getMaxOrder()){
                // 已经约满 不能再预约了
                sendTimeDto.setState(0);
            }
        }
        return new Result<>().ok(sendTimeEntityList);
    }
}
