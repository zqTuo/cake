package io.renren.controller;

import io.renren.common.utils.R;
import io.renren.dto.ShopDto;
import io.renren.form.ShopForm;
import io.renren.service.ShopService;
import io.renren.common.utils.LocationUtils;
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
 * @Date: 2019/8/21 11:40
 */
@RestController
@RequestMapping("/api/shop")
@Api(tags="门店接口控制器")
public class ShopController {
    @Resource
    private ShopService shopService;

    @GetMapping("shopInfo")
    @ApiOperation(value="获取门店列表接口")
    public R shopInfo(@RequestBody(required = false) ShopForm form){
        List<ShopDto> shopList = shopService.findAll();

        if(form.getLatitude() > 0 && form.getLongitude() > 0){
            for (ShopDto shopDto:shopList){
                // 判断定位所在地是哪家店铺
                if(checkIsHear(shopDto,form)){
                    shopDto.setCurrentFlag(1);
                }
            }
        }

        return R.ok().put("arrayData",shopList);
    }

    private boolean checkIsHear(ShopDto shopDto, ShopForm form) {
        double distance = LocationUtils.getDistance(Double.parseDouble(shopDto.getShopLatitude()),
                Double.parseDouble(shopDto.getShopLongitude()),
                form.getLatitude(),
                form.getLongitude());

        // 1公里以内
        return (distance / 1000) <= 1;
    }


}
