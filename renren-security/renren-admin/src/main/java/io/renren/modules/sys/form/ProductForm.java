package io.renren.modules.sys.form;

import io.renren.modules.sys.entity.ProductDetailEntity;
import io.renren.modules.sys.entity.ProductEntity;
import io.renren.modules.sys.entity.ProductSizeEntity;
import io.renren.modules.sys.entity.ProductTasteEntity;
import lombok.Data;

import java.util.List;


/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/8/30 14:16
 */
@Data
public class ProductForm {
    private ProductEntity product;
    private List<ProductDetailEntity> detailList;
    private String[] sizeList;
    private String[] tasteList;
}
