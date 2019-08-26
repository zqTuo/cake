package io.renren.controller;

import io.renren.common.utils.LocationUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.dto.SendTimeDto;
import io.renren.entity.ShopEntity;
import io.renren.form.SendTimeForm;
import io.renren.service.SendTimeService;
import io.renren.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;
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
    private static Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @Resource
    private SendTimeService sendTimeService;
    @Resource
    private LocationUtils locationUtils;
    @Resource
    private ShopService shopService;

    @GetMapping("getSendTime")
    @ApiOperation(value = "获取配送时间接口")
    public R getSendTime(@RequestBody SendTimeForm form) throws ParseException {
        ValidatorUtils.validateEntity(form);
        log.info("获取配送时间参数：" + form.toString());
        List<SendTimeDto> sendTimeEntityList = sendTimeService.getData();

        // 根据派送地址系统时间 计算可选时段
        ShopEntity shopEntity = shopService.getById(form.getShopId());
        String addrName = form.getProvince() + form.getCity() + form.getDistrict() + form.getAddr();
        String origin = shopEntity.getShopLatitude() + "," + shopEntity.getShopLongitude();

        float distance = locationUtils.getDistanceByBaiduMap(addrName,origin);

        if(distance == -1){
            return R.error(-3,"尚未获取派送距离，请核实派送地址是否有误");
        }

        sendTimeService.resolveTimeList(sendTimeEntityList,distance,form.getSelectedDate());
        return R.ok().put("arrayData",sendTimeEntityList);
    }

    // todo 计算订单接口
}
