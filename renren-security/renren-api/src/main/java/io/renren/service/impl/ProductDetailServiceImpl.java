package io.renren.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.dao.ProductDetailDao;
import io.renren.dto.ProductDetailDto;
import io.renren.dto.ProductInfoDetailDto;
import io.renren.entity.ProductDetailEntity;
import io.renren.service.ProductDetailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service("productDetailService")
public class ProductDetailServiceImpl extends ServiceImpl<ProductDetailDao, ProductDetailEntity> implements ProductDetailService {
    private static Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @Override
    public BigDecimal countExtraPrice(long[] extraIds) {
        return baseMapper.countExtraPrice(extraIds);
    }

    @Override
    public ProductDetailDto findPayInfo(long detailId) {
        return baseMapper.findPayInfo(detailId);
    }

    @Override
    public List<ProductDetailEntity> getByIds(long[] ids) {
        return baseMapper.getByIds(ids);
    }

    @Override
    public List<ProductInfoDetailDto> getByProductId(long id) {
        return baseMapper.getByProductId(id);
    }

    @Override
    public BigDecimal countTotalPrice(String prods) {
        BigDecimal totalPrice = new BigDecimal("0");

        JSONArray proArr = JSONArray.parseArray(prods);
        for (int i = 0; i < proArr.size(); i++) {
            long detailId = proArr.getJSONObject(i).getLong("detailId");
            int buyNum = proArr.getJSONObject(i).getInteger("buyNum");
            log.info("计算条目[" + i + "] 购买蛋糕商品详情id：" + detailId + ",购买数量：" + buyNum);

            ProductDetailEntity detail = baseMapper.selectById(detailId);
            totalPrice = totalPrice.add(detail.getDetailPrice().multiply(new BigDecimal(buyNum)));
        }

        return totalPrice;
    }

}
