package io.renren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.dto.ShopDto;
import io.renren.entity.ShopEntity;

import java.util.List;

/**
 * 门店表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-19 16:35:41
 */
public interface ShopService extends IService<ShopEntity> {

    List<ShopDto> findAll();
}

