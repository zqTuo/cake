package io.renren.modules.sys.dto;

import io.renren.modules.sys.entity.ProductCategoryEntity;
import lombok.Data;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/8/26 14:16
 */
@Data
public class CategoryDto {
    private long id;
    private String name;
    private long parentID;
    private int status;
    private int lev;
    private int depth;

    public CategoryDto(ProductCategoryEntity category, int lev) {
        this.setId(category.getId());
        this.setName(category.getCategoryName());
        this.setParentID(category.getCategoryParentid());
        this.setStatus(category.getCategoryFlag());
        this.lev = lev;
    }

}
