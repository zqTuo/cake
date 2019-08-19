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

import io.renren.modules.sys.entity.SendTimeEntity;
import io.renren.modules.sys.service.SendTimeService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 配送时间表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-19 16:35:41
 */
@RestController
@RequestMapping("sys/sendtime")
public class SendTimeController {
    @Autowired
    private SendTimeService sendTimeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:sendtime:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sendTimeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:sendtime:info")
    public R info(@PathVariable("id") Long id){
        SendTimeEntity sendTime = sendTimeService.getById(id);

        return R.ok().put("sendTime", sendTime);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:sendtime:save")
    public R save(@RequestBody SendTimeEntity sendTime){
        sendTimeService.save(sendTime);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:sendtime:update")
    public R update(@RequestBody SendTimeEntity sendTime){
        ValidatorUtils.validateEntity(sendTime);
        sendTimeService.updateById(sendTime);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:sendtime:delete")
    public R delete(@RequestBody Long[] ids){
        sendTimeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
