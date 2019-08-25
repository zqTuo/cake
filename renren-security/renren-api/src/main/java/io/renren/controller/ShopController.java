package io.renren.controller;

import com.alibaba.fastjson.JSONObject;
import io.renren.common.utils.LocationUtils;
import io.renren.common.utils.R;
import io.renren.dto.ShopDto;
import io.renren.form.ShopForm;
import io.renren.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
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
    @Resource
    private LocationUtils locationUtils;

    @GetMapping("shopInfo")
    @ApiOperation(value="获取门店列表接口")
    public R shopInfo(@RequestBody(required = false) ShopForm form){
        List<ShopDto> shopList = shopService.findAll();

        if(form != null && StringUtils.isNotBlank(form.getLatitude()) && StringUtils.isNotBlank(form.getLongitude())){
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
        JSONObject distance = locationUtils.getDistance(shopDto.getShopLatitude() + "," + shopDto.getShopLongitude(),
                form.getLatitude() + "," + form.getLongitude());

        // 1公里以内
        if(distance.getInteger("status") == 0){
            //获取路线距离，duration也可以返回路线耗时  驾驶模式
            float num = distance.getJSONArray("result").getJSONObject(0).getJSONObject("distance").getFloat("value");
            return (num / 1000) <= 1;
        }else {
            return false;
        }
    }


}
