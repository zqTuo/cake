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

import io.renren.modules.sys.entity.WxKeyEntity;
import io.renren.modules.sys.service.WxKeyService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 微信关键字表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-03 17:29:29
 */
@RestController
@RequestMapping("sys/wxkey")
public class WxKeyController {
    @Autowired
    private WxKeyService wxKeyService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:wxkey:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = wxKeyService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:wxkey:info")
    public R info(@PathVariable("id") Long id){
        WxKeyEntity wxKey = wxKeyService.getById(id);

        return R.ok().put("wxKey", wxKey);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:wxkey:save")
    public R save(@RequestBody WxKeyEntity wxKey){
        wxKeyService.save(wxKey);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:wxkey:update")
    public R update(@RequestBody WxKeyEntity wxKey){
        ValidatorUtils.validateEntity(wxKey);
        wxKeyService.updateById(wxKey);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:wxkey:delete")
    public R delete(@RequestBody Long[] ids){
        wxKeyService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
