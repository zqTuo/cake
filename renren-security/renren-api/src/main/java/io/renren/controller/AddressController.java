package io.renren.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.annotation.Login;
import io.renren.common.result.Result;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.entity.AddressEntity;
import io.renren.service.AddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @Resource
    private AddressService addressService;

    @Login
    @ApiOperation(value = "获取用户收货地址接口")
    @GetMapping("getInfo")
    public Result<AddressEntity> getInfo(@ApiIgnore @RequestAttribute("userId")long userId){
        List<AddressEntity> addressList = addressService.list(new QueryWrapper<AddressEntity>().eq("user_id",userId));
        return new Result<>().ok(addressList);
    }

    @Login
    @ApiOperation(value = "获取用户地址详情接口")
    @GetMapping("getDetail")
    public Result<AddressEntity> getDetail(@RequestParam("id") @ApiParam(value = "地址ID",example = "1")long id){
        AddressEntity addressEntity = addressService.getById(id);
        return new Result().ok(addressEntity);
    }

    @Login
    @ApiOperation(value = "新增或修改地址接口")
    @PostMapping("addOrUpdate")
    public Result<AddressEntity> addOrUpdate(@RequestBody AddressEntity addressEntity,@ApiIgnore @RequestAttribute("userId")long userId){
        ValidatorUtils.validateEntity(addressEntity);

        log.info("添加或修改地址参数：" + addressEntity.toString());

        int count = addressService.count(new QueryWrapper<AddressEntity>().eq("user_id",userId));
        if(count == 0){
            addressEntity.setDefaultFlag(1);
        }else{
            if(addressEntity.getDefaultFlag() > 0){
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
        return new Result<>().ok(addressEntity);
    }

    @Login
    @ApiOperation(value = "获取默认地址接口")
    @GetMapping("getDefault")
    public Result<AddressEntity> getDefault(@ApiIgnore @RequestAttribute("userId")long userId){
        AddressEntity addressEntity = addressService.getOne(new QueryWrapper<AddressEntity>().eq("user_id",userId)
                .eq("default_flag",1));

        return new Result().ok(addressEntity);
    }

    @Login
    @ApiOperation(value = "删除地址接口")
    @PostMapping("delAddr")
    public R delAddr(@RequestParam("id") @ApiParam(value = "地址ID",required = true)long id,
                     @ApiIgnore @RequestAttribute("userId") long userId){

        addressService.removeById(id);
        return R.ok();
    }
}
