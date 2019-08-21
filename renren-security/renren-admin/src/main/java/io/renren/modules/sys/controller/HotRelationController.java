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

import io.renren.modules.sys.entity.HotRelationEntity;
import io.renren.modules.sys.service.HotRelationService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 热销栏目与商品关联表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 17:12:34
 */
@RestController
@RequestMapping("sys/hotrelation")
public class HotRelationController {
    @Autowired
    private HotRelationService hotRelationService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:hotrelation:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = hotRelationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:hotrelation:info")
    public R info(@PathVariable("id") Long id){
        HotRelationEntity hotRelation = hotRelationService.getById(id);

        return R.ok().put("hotRelation", hotRelation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:hotrelation:save")
    public R save(@RequestBody HotRelationEntity hotRelation){
        hotRelationService.save(hotRelation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:hotrelation:update")
    public R update(@RequestBody HotRelationEntity hotRelation){
        ValidatorUtils.validateEntity(hotRelation);
        hotRelationService.updateById(hotRelation);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:hotrelation:delete")
    public R delete(@RequestBody Long[] ids){
        hotRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
