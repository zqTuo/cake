package io.renren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.dto.OrderDto;
import io.renren.entity.ShopOrderEntity;
import io.renren.form.MyOrderForm;

import java.util.List;

/**
 * 订单表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 16:22:06
 */
public interface ShopOrderService extends IService<ShopOrderEntity> {

    String generatePayOrderXml(String weCatAppId, String mchId, String noncestr, String desc, String orderNo, int price, String ipAddr, String notify_url, String openid, String type);

    void payFinished(ShopOrderEntity order);

    List<OrderDto> findMyOrder(MyOrderForm form, long userId);

    OrderDto findByOrderNo(String orderNo);
}

