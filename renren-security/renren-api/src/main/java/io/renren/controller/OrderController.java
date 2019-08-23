package io.renren.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.entity.SendTimeEntity;
import io.renren.form.SendTimeForm;
import io.renren.service.SendTimeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/8/22 15:24
 */
@RestController
@RequestMapping("/api/order")
@Api(tags="订单接口控制器")
public class OrderController {
    @Resource
    private SendTimeService sendTimeService;

    @GetMapping("getSendTime")
    @ApiOperation(value = "获取配送时间接口")
    public R getSendTime(@RequestBody SendTimeForm form){
        ValidatorUtils.validateEntity(form);

        List<SendTimeEntity> sendTimeEntityList = sendTimeService.list(new QueryWrapper<SendTimeEntity>().orderByAsc("id"));

        // 根据派送地址系统时间 计算可选时段
        sendTimeService.resolveTimeList(sendTimeEntityList,form);
        return R.ok().put("arrayData",sendTimeEntityList);
    }
}
