package io.renren.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.annotation.Login;
import io.renren.common.result.Result;
import io.renren.common.utils.DateUtil;
import io.renren.common.utils.JodaTimeUtil;
import io.renren.common.validator.ValidatorUtils;
import io.renren.dto.*;
import io.renren.entity.*;
import io.renren.form.CourseDetailForm;
import io.renren.form.PageForm;
import io.renren.form.SetCoursePayForm;
import io.renren.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.math.BigDecimal;
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
    @Resource
    private ComboCourseService comboCourseService;
    @Resource
    private CouponUserService couponUserService;
    @Resource
    private CourseMenuService courseMenuService;
    @Resource
    private ComboTypeService comboTypeService;
    @Resource
    private ComboUserService comboUserService;

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
    public Result<CourseDetailForm> courseDetail(@RequestParam("id") @ApiParam(value = "课程ID",required = true)long id){
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

        CourseDetailForm res = new CourseDetailForm();
        res.setBannerArr(bannerArr);
        res.setCourse(courseEntity);

        return new Result<>().ok(res);
    }

    @PostMapping("getSendTime")
    @ApiOperation(value = "获取课程到达时间接口")
    public Result<SendTimeDto> getSendTime(@RequestParam("selectedDate") @ApiParam(value = "派送日期，默认当天，格式yyyy-MM-dd",required = true)String selectedDate) throws ParseException {
        List<SendTimeDto> sendTimeEntityList = sendTimeService.getData(1);

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
            }else{
                sendTimeDto.setState(2);
            }
        }
        return new Result<>().ok(sendTimeEntityList);
    }

    @PostMapping("getSetCourse")
    @ApiOperation(value = "获取套餐列表接口")
    public Result<ComboCourseEntity> getSetCourse(){
        List<ComboCourseEntity> courseEntityList = comboCourseService.list();

        for (ComboCourseEntity comboCourseEntity :courseEntityList){
            comboCourseEntity.setPicUrl(pic_pre + comboCourseEntity.getPicUrl());
        }

        return new Result<>().ok(courseEntityList);
    }

    @Login
    @PostMapping("getSetPay")
    @ApiOperation(value = "获取课程套餐结算数据接口")
    public Result<ComboCourseDto> getSetPay(@RequestBody SetCoursePayForm form){
        ValidatorUtils.validateEntity(form);

        ComboCourseDto comboCourseDto = comboCourseService.findById(form.getComboCourseId());
        comboCourseDto.setPicUrl(pic_pre + comboCourseDto.getPicUrl());

        if(comboCourseDto.getValidPeriod() > 0){
            comboCourseDto.setExpiredDate(JodaTimeUtil.getDateFromToday(comboCourseDto.getValidPeriod()));
        }else{
            comboCourseDto.setExpiredDate("无期限");
        }

        comboCourseDto.setTotalPrice(comboCourseDto.getPrice());

        if(form.getCouponUserId() != null && form.getCouponUserId() > 0){
            //计算优惠券
            BigDecimal discount = couponUserService.calculate(form.getCouponUserId(), comboCourseDto.getTotalPrice());
            log.info( "使用优惠券优惠金额：" + discount);
            comboCourseDto.setTotalPrice(comboCourseDto.getTotalPrice().subtract(discount));
            comboCourseDto.setDiscount(discount);
        }else{
            comboCourseDto.setDiscount(new BigDecimal("0.00"));
        }

        return new Result().ok(comboCourseDto);
    }


    @PostMapping("getMenu")
    @ApiOperation(value = "获取课程菜单接口")
    public Result<CourseMenuDto> getMenu(@RequestParam("selectedDate") @ApiParam(value = "派送日期，默认当天，格式yyyy-MM-dd",required = true)String selectedDate) throws ParseException {
        ValidatorUtils.validateEntity(selectedDate);

        List<CourseMenuDto> menuDtoList = courseMenuService.getMenu(selectedDate);

        for (CourseMenuDto courseMenuDto:menuDtoList){
            if(courseMenuDto.getCourseId() == 0){
                courseMenuDto.setState(1);
                courseMenuDto.setTitle("暂无课程");
                continue;
            }

            // 判断是否已经约满
            int curOrderNum = orderService.countToday(selectedDate + " " + courseMenuDto.getStartTime() ,selectedDate + " " + courseMenuDto.getEndTime(),2);
            if(curOrderNum >= courseMenuDto.getNum()){
                // 已经约满 不能再预约了
                courseMenuDto.setState(0);
                courseMenuDto.setTitle("约满");
            }else{
                courseMenuDto.setState(2);
            }
        }
        return new Result<>().ok(menuDtoList);
    }

    @Login
    @PostMapping("courseUsePay")
    @ApiOperation(value = "使用套餐课程结算接口")
    public Result<CourseDto> courseUsePay(
            @RequestParam("courseMenuId") @ApiParam(value = "课程菜单ID",required = true)Long courseMenuId
            , @ApiIgnore @RequestAttribute("userId")long userId){

        CourseMenuEntity courseMenuEntity = courseMenuService.getById(courseMenuId);

        if(courseMenuEntity == null){
            return new Result<>().error("此课程已结束");
        }

        CourseEntity courseEntity = courseService.getById(courseMenuEntity.getCourseId());

        //查询用户所有套餐剩余次数
        List<RemandCourseDto> remandCourseDtoList = comboUserService.findByUserId(userId);

        CourseDto courseDto = new CourseDto();
        courseDto.setId(courseMenuEntity.getCourseId());
        courseDto.setTitle(courseEntity.getTitle());
        courseDto.setPrice(courseEntity.getPrice());

        ComboTypeEntity comboTypeEntity = comboTypeService.getById(courseEntity.getComboTypeId());
        courseDto.setType(comboTypeEntity.getTitle());

        courseDto.setCost("此次预约将消耗掉" + courseMenuEntity.getCost() + "次" + comboTypeEntity.getTitle());
        courseDto.setRemandList(remandCourseDtoList);

        //检测用户这类套餐次数是否使用完
        ComboUserEntity comboUserEntity = comboUserService.getOne(
                new QueryWrapper<ComboUserEntity>().eq("user_id",userId)
                .eq("type_id",comboTypeEntity.getId())
        );

        if(comboUserEntity == null){
            return new Result().error(courseDto,"您的套餐剩余次数不足！");
        }

        if(comboUserEntity.getNum() <= 0){
            return new Result().error(courseDto,"您的套餐剩余次数不足！");
        }else{
            if(comboUserEntity.getExpiredTime() != null && JodaTimeUtil.isExpired(comboUserEntity.getExpiredTime(),new Date())){
                log.info("套餐已过期");
                return new Result().error(courseDto,"您的套餐已过期！");
            }

            return new Result().ok(courseDto);
        }
    }


}
