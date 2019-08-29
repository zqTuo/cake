package io.renren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.dto.PayProDto;
import io.renren.dto.ShoppingCartDto;
import io.renren.entity.ShoppingCartEntity;

import java.util.List;

/**
 * 购物车表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 16:22:06
 */
public interface ShoppingCartService extends IService<ShoppingCartEntity> {

    List<ShoppingCartEntity> getMyData(long userId);

    ShoppingCartDto countMyData(long userId);

    List<PayProDto> getBuyData(long userId);
}

