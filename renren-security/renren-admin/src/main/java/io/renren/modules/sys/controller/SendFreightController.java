package io.renren.modules.sys.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.entity.SendFreightEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SendFreightService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;



/**
 * 运费表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 16:22:06
 */
@RestController
@RequestMapping("sys/sendfreight")
public class SendFreightController {
    @Autowired
    private SendFreightService sendFreightService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:sendfreight:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sendFreightService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:sendfreight:info")
    public R info(@PathVariable("id") Long id){
        SendFreightEntity sendFreight = sendFreightService.getById(id);

        return R.ok().put("sendFreight", sendFreight);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:sendfreight:save")
    public R save(@RequestBody SendFreightEntity sendFreight){
        ValidatorUtils.validateEntity(sendFreight);

        sendFreight.setCreateTime(new Date());

        sendFreight.setUpdateBy(((SysUserEntity) SecurityUtils.getSubject().getPrincipal()).getUsername());
        sendFreightService.save(sendFreight);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:sendfreight:update")
    public R update(@RequestBody SendFreightEntity sendFreight){
        ValidatorUtils.validateEntity(sendFreight);

        sendFreight.setUpdateTime(new Date());

        sendFreight.setUpdateBy(((SysUserEntity) SecurityUtils.getSubject().getPrincipal()).getUsername());
        sendFreightService.updateById(sendFreight);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:sendfreight:delete")
    public R delete(@RequestBody Long[] ids){
        sendFreightService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
