package io.renren.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.annotation.Login;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.entity.AddressEntity;
import io.renren.service.AddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.tomcat.jni.Address;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/8/22 11:45
 */
@RestController
@RequestMapping("/api/addr")
@Api(tags = "用户地址接口控制器")
public class AddressController {
    @Resource
    private AddressService addressService;

    @Login
    @ApiOperation(value = "获取用户收货地址接口")
    @GetMapping("getInfo")
    public R getInfo(@ApiIgnore @RequestAttribute("userId")long userId){
        List<AddressEntity> addressList = addressService.list(new QueryWrapper<AddressEntity>().eq("user_id",userId));
        return R.ok().put("arrayData",addressList);
    }

    @Login
    @ApiOperation(value = "获取用户地址详情接口")
    @GetMapping("getDetail")
    public R getDetail(@RequestBody @ApiParam(value = "地址ID")long id){
        AddressEntity addressEntity = addressService.getById(id);
        return R.ok().put("data",addressEntity);
    }

    @Login
    @ApiOperation(value = "新增或修改地址接口")
    @PostMapping("addOrUpdate")
    public R addOrUpdate(@RequestBody AddressEntity addressEntity,@ApiIgnore @RequestAttribute("userId")long userId){
        ValidatorUtils.validateEntity(addressEntity);

        int count = addressService.count(new QueryWrapper<AddressEntity>().eq("user_id",userId));
        if(count == 0){
            addressEntity.setDefaultflag(1);
        }else{
            if(addressEntity.getDefaultflag() > 0){
                // 更换默认数据
                addressService.cleanDefault(userId);
            }
        }

        if(addressEntity.getId() != null && addressEntity.getId() > 0){
            addressService.updateById(addressEntity);
        }else{
            addressEntity.setUserId(userId);
            addressService.save(addressEntity);
        }
        return R.ok();
    }

    @Login
    @ApiOperation(value = "获取默认地址接口")
    @GetMapping("getDefault")
    public R getDefault(@ApiIgnore @RequestAttribute("userId")long userId){
        AddressEntity addressEntity = addressService.getOne(new QueryWrapper<AddressEntity>().eq("user_id",userId)
                .eq("default_flag",1));

        return R.ok().put("data",addressEntity);
    }
}
