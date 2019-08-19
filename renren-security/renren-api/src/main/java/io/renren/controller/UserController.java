package io.renren.controller;

import io.renren.annotation.Login;
import io.renren.annotation.LoginUser;
import io.renren.common.utils.R;
import io.renren.dto.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @Author: Clarence
 * @Description:
 * @Date: 2019/8/19 23:55.
 */
@RestController
@RequestMapping("/api")
@Api(tags="用户接口")
public class UserController {

    @Login
    @GetMapping("userInfo")
    @ApiOperation(value="获取用户信息", response= UserDto.class)
    public R userInfo(@ApiIgnore @LoginUser UserDto user){
        return R.ok().put("user", user);
    }
}
