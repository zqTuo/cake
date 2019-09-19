package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.dto.ExcelOrderDto;
import io.renren.modules.sys.dto.ExcelSmallOrderDto;
import io.renren.modules.sys.entity.ShopOrderEntity;

import java.util.List;
import java.util.Map;

/**
 * 订单表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 16:22:06
 */
public interface ShopOrderService extends IService<ShopOrderEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<ExcelOrderDto> getData(Map<String, Object> map);

    List<ExcelSmallOrderDto> getSmallData(Map<String, Object> map);
}

