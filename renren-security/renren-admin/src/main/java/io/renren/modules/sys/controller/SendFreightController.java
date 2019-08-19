package io.renren.modules.sys.controller;

import java.util.Arrays;
import java.util.Map;

import io.renren.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.sys.entity.SendFreightEntity;
import io.renren.modules.sys.service.SendFreightService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 运费表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-19 16:35:41
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
