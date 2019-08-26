package io.renren.modules.sys.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.dto.CategoryDto;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.sys.entity.ProductCategoryEntity;
import io.renren.modules.sys.service.ProductCategoryService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 商品种类表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 16:22:06
 */
@RestController
@RequestMapping("sys/productcategory")
public class ProductCategoryController {
    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:productcategory:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = productCategoryService.queryPage(params);

        return R.ok().put("page", page);
    }

    @RequestMapping("/findAll")
    public R findAll(){
        //调取业务层获取所有商品种类数据
        List<ProductCategoryEntity> mList = productCategoryService.list();
        //最终得到的数据：经过无限分类，排序之后的数据
        List<CategoryDto> backList = new ArrayList<CategoryDto>();
        //对mList集合进行排序，并把排序之后的数据交给backList
        RecGoodsType(backList,mList,0,0);
        //对排序后的数据再次处理并且输出
        for (CategoryDto categoryDto: backList) {
            categoryDto.setName(setNBSP(categoryDto.getLev()) + categoryDto.getName() );
        }

        return R.ok().put("arrayData",backList);
    }

    /**
     * 将mList进行排序返回给backList
     * @param backList
     * @param mList
     * @param gt_id
     * @param lev
     */
    private void RecGoodsType(List<CategoryDto> backList, List<ProductCategoryEntity> mList, long gt_id, int lev) {
        for (ProductCategoryEntity category:mList) {
            if(category.getCategoryParentid() == gt_id){
                CategoryDto categoryDto = new CategoryDto(category,lev);
                backList.add(categoryDto);
                RecGoodsType(backList,mList,category.getId(),lev+1);
            }
        }
    }

    /**
     * 根据层级关系处理 类别名称显示
     * @param lev
     * @return
     */
    public static String setNBSP(int lev) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lev; i++) {
            sb.append("...");
        }
        sb.append("| ");
        return sb.toString();
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:productcategory:info")
    public R info(@PathVariable("id") Long id){
        ProductCategoryEntity productCategory = productCategoryService.getById(id);

        return R.ok().put("productCategory", productCategory);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:productcategory:save")
    public R save(@RequestBody ProductCategoryEntity productCategory){
        productCategoryService.save(productCategory);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:productcategory:update")
    public R update(@RequestBody ProductCategoryEntity productCategory){
        ValidatorUtils.validateEntity(productCategory);
        productCategoryService.updateById(productCategory);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:productcategory:delete")
    public R delete(@RequestBody Long[] ids){
        productCategoryService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
