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

import io.renren.modules.sys.entity.HotColumnEntity;
import io.renren.modules.sys.service.HotColumnService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 首页热销栏目表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 17:12:34
 */
@RestController
@RequestMapping("sys/hotcolumn")
public class HotColumnController {
    @Autowired
    private HotColumnService hotColumnService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:hotcolumn:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = hotColumnService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:hotcolumn:info")
    public R info(@PathVariable("id") Long id){
        HotColumnEntity hotColumn = hotColumnService.getById(id);

        return R.ok().put("hotColumn", hotColumn);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:hotcolumn:save")
    public R save(@RequestBody HotColumnEntity hotColumn){
        hotColumnService.save(hotColumn);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:hotcolumn:update")
    public R update(@RequestBody HotColumnEntity hotColumn){
        ValidatorUtils.validateEntity(hotColumn);
        hotColumnService.updateById(hotColumn);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:hotcolumn:delete")
    public R delete(@RequestBody Long[] ids){
        hotColumnService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
