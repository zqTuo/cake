package io.renren.controller;

import com.alibaba.fastjson.JSONObject;
import io.renren.annotation.Login;
import io.renren.common.result.Result;
import io.renren.common.utils.*;
import io.renren.common.validator.ValidatorUtils;
import io.renren.dto.ProductDto;
import io.renren.entity.MeituanCouponEntity;
import io.renren.entity.MeituanItemEntity;
import io.renren.entity.ShopEntity;
import io.renren.form.CodeForm;
import io.renren.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @Author: Clarence
 * @Description:
 * @Date: 2019/8/31 18:33.
 */

@Controller
@RequestMapping(value = "/api/meituan")
@Api(tags = "美团点评验券销券接口控制器")
public class MeituanController {
    private static Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @Resource
    private MeiTuanUtil meiTuanUtil;
    @Value("${project.url_pre}")
    private String url_pre;
    @Value("${project.pic_pre}")
    private String pic_pre;
    @Resource
    private IRedisService redisService;
    @Resource
    private ShopService shopService;
    @Resource
    private ProductService productService;
    @Resource
    private MeituanCouponService meituanCouponService;
    @Resource
    private MeituanItemService meituanItemService;




    @Login
    @PostMapping("check")
    @ResponseBody
    @ApiOperation(value = "验券接口",notes = "注意：下方参数不可信，原因不明。此接口真实参数为-> code：券码  shopId：店铺ID   sourceType：店铺类型 0：美团 1：大众点评")
    public Result<ProductDto> checkCode(@RequestBody CodeForm codeForm, @ApiIgnore @RequestAttribute("userId")long userId) throws Exception {
        ValidatorUtils.validateEntity(codeForm);

        String session = meiTuanUtil.getSession();
        if(StringUtils.isEmpty(session)){
            return new Result().error("此功能尚在维护，请稍后再试");
        }

        ShopEntity shopEntity = shopService.getById(codeForm.getShopId());

        if(StringUtils.isEmpty(shopEntity.getMeituanShopId()) && StringUtils.isEmpty(shopEntity.getDianpingShopId())){
            return new Result().error("该店铺暂无美团/大众点评店铺");
        }

        if(codeForm.getSourceType() == 0 && StringUtils.isEmpty(shopEntity.getMeituanShopId())){
            return new Result().error("该店铺尚未配置店铺ID，请联系客服");
        }
        if(codeForm.getSourceType() == 1 && StringUtils.isEmpty(shopEntity.getDianpingShopId())){
            return new Result().error("该店铺尚未配置店铺ID，请联系客服");
        }

        JSONObject codeInfo = meiTuanUtil.checkCode(session,codeForm.getCode(),shopEntity,codeForm.getSourceType());
        log.info("美团验券请求结果：" + codeInfo);
        if(200 != codeInfo.getInteger("code")){
            return new Result().error(codeInfo.getString("msg"));
        }

        JSONObject data = codeInfo.getJSONObject("data");
        log.info("券码信息：" + data);

        String deal_title = data.getString("deal_title"); // 商品名称
        Date expireTime = data.getDate("receiptEndDate"); // 券过期时间
        if(JodaTimeUtil.isExpired(expireTime,new Date())){
            return new Result().error("抱歉，您的券已过期！");
        }

        //获取券的类型 2：抵用券 8：商户抵用券
        int amountType = data.getJSONArray("payment_detail").getJSONObject(0).getInteger("amount_type");


        String[] detailSku = new String[1];
        //根据商品名称获取sku,可能是多个商品详情sku 匹配规格为: #sku1,sku2#
        Pattern p = Pattern.compile("#(.*?)#");
        Matcher m = p.matcher(deal_title);
        while(m.find()){
            log.info("====== 匹配到商品sku：" + m.group(1));
            detailSku = m.group(1).split(",");
        }

        log.info("匹配最终sku个数：" + detailSku.length);

        List<ProductDto> productDtoList = new ArrayList<>();

        //生成美团券验券记录
        MeituanCouponEntity couponEntity = MeituanCouponEntity.builder().userId(userId).code(codeForm.getCode())
                .sourcetype(codeForm.getSourceType()).flag(0).createTime(new Date()).build();

        meituanCouponService.save(couponEntity);
        log.info("美团券验券记录生成成功:" + couponEntity.toString());


        if(detailSku.length > 0){

            List<MeituanItemEntity> meituanItemEntityList = new ArrayList<>();

            productDtoList = productService.findBySku(detailSku);

            for (ProductDto detailDto:productDtoList){
                detailDto.setProductImg(pic_pre + detailDto.getProductImg());

                MeituanItemEntity meituanItemEntity = MeituanItemEntity.builder().couponId(couponEntity.getId())
                        .productDetailId(detailDto.getProductDetailId()).build();

                meituanItemEntityList.add(meituanItemEntity);
            }

            meituanItemService.saveBatch(meituanItemEntityList);
            log.info("===========  美团商品抵扣券生成成功！========");
        }else{
            // 可能是配置问题  可能是美团代金券
            // TODO 待确定返回参数 amountType
            //随便选一个商品展示 选最新的商品
            ProductDto detailDto = productService.findNewOne();
            productDtoList.add(detailDto);

            MeituanItemEntity meituanItemEntity = MeituanItemEntity.builder().couponId(couponEntity.getId())
                    .productDetailId(0L).build();

            meituanItemService.save(meituanItemEntity);
            log.info("===========  美团代金券生成成功！========");
        }

        Map<String,Object> map = new HashMap<>();
        map.put("productList",productDtoList);
        map.put("meituanId",couponEntity.getId());

        return new Result().ok(map);
    }

}
