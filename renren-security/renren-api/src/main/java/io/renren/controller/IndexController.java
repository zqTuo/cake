package io.renren.controller;

import com.alibaba.fastjson.JSONObject;
import io.renren.common.result.Result;
import io.renren.common.utils.R;
import io.renren.dto.HotColumnDto;
import io.renren.dto.IndexProductDto;
import io.renren.entity.AddressEntity;
import io.renren.entity.BannerEntity;
import io.renren.entity.BaseDataEntity;
import io.renren.entity.ShopEntity;
import io.renren.service.BannerService;
import io.renren.service.BaseDataService;
import io.renren.service.ProductService;
import io.renren.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/8/21 11:40
 */
@RestController
@RequestMapping("/api/index")
@Api(tags="首页接口控制器")
public class IndexController {
    @Resource
    private BaseDataService baseDataService;
    @Resource
    private ProductService productService;
    @Resource
    private BannerService bannerService;
    @Value("${project.pic_pre}")
    private String pic_pre;

    @GetMapping("menuInfo")
    @ApiOperation(value="获取首页菜单信息接口",notes = "data数据为菜单配置，cake对象是第一个菜单，tea是第二个菜单，hot是第三个，meituan是最后一个，" +
            "其中title是菜单名称，icon是菜单icon地址")
    public R menuInfo(){
        BaseDataEntity baseDataEntity = baseDataService.getBySourceType(0);
        List<BannerEntity> bannerList = bannerService.getIndexBanner();
        for (BannerEntity banner:bannerList){
            banner.setBannerPic(pic_pre + banner.getBannerPic());
        }

        Map<String,Object> map = new HashMap<>();
        map.put("bannerList",bannerList);
        map.put("data",JSONObject.parseObject(baseDataEntity.getContent()));
        return R.ok(map);
    }

    @GetMapping("proInfo")
    @ApiOperation(value="获取首页热销商品接口")
    public Result<HotColumnDto> proInfo(){
        List<HotColumnDto> hotColumnDtoList = productService.findAllHotColumn();
        for (HotColumnDto hotColumnDto:hotColumnDtoList){
            for (IndexProductDto productDto:hotColumnDto.getProductList()){
                productDto.setProductImg(pic_pre + productDto.getProductImg());
            }
        }
        return new Result().ok(hotColumnDtoList);
    }

}
