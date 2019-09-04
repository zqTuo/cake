package io.renren.modules.sys.controller;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import io.renren.common.utils.*;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.service.IRedisService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.sys.entity.MeituanCouponEntity;
import io.renren.modules.sys.service.MeituanCouponService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * 美团券验券记录表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-01 14:26:01
 */
@Controller
@RequestMapping("sys/meituancoupon")
public class MeituanCouponController {
    private static Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @Autowired
    private MeituanCouponService meituanCouponService;
    @Resource
    private MeiTuanUtil meiTuanUtil;
    @Resource
    private IRedisService redisService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @ResponseBody
    @RequiresPermissions("sys:meituancoupon:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = meituanCouponService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 检测美团授权是否过期
     */
    @ResponseBody
    @RequestMapping("/checkSession")
    public R checkSession(){
        String session = meiTuanUtil.getSession();
        if(StringUtils.isEmpty(session)){
            return R.error("session已失效，请重新授权");
        }

        return R.ok();
    }


    @GetMapping("auth")
    @ApiOperation(value = "美团授权接口")
    public String auth(HttpServletRequest request) throws UnsupportedEncodingException {
        String o_url = ServletRequestUtils.getStringParameter(request,"p","");

        String url = meiTuanUtil.getSessionUrl(request,o_url);
        log.info("美团请求授权链接：" + url);
        return "redirect:" + url;
    }

    /**
     * 美团授权回调地址
     * @return
     */
    @RequestMapping("authRedirect")
    public String authRedirect(HttpServletRequest request){
        String message = ServletRequestUtils.getStringParameter(request,"message","");
        String auth_code = ServletRequestUtils.getStringParameter(request,"auth_code","");
        String state = ServletRequestUtils.getStringParameter(request,"state","");
        String ip = ServletRequestUtils.getStringParameter(request,"ip","");
        String o_url = ServletRequestUtils.getStringParameter(request,"o_url","");

        log.info("============ 美团授权回调参数：message：" + message + ",auth_code："
                + auth_code + "，state：" + state + ",ip：" + ip + ",o_url:" + o_url);
        if(StringUtils.isEmpty(message) || StringUtils.isEmpty(auth_code)
                || StringUtils.isEmpty(state) || StringUtils.isEmpty(ip)){
            log.error("缺少参数，授权回调异常！");
            return "";
        }

        // 校验 防止跨站请求伪造攻击
        String o_state = Constant.MEITUAN_SECCESS_SALT + ip + DateUtil.getYYYYMMdd();
        MD5 md5 = new MD5();
        String cotent2Aes = md5.toDigest(o_state);

        log.info("美团授权，原state：" + cotent2Aes);
        if(!(cotent2Aes).equals(state)){
            log.info("授权检验失败：ip：" + ip);
            return "redirect:" + o_url;
        }

        // 通过code换取session
        JSONObject userInfo = meiTuanUtil.getWebSession(auth_code,o_url);

        log.info("====== meituan_session获取结果：" + userInfo);

        if(200 != userInfo.getInteger("code")){
            return "";
        }

        redisService.set("meituan_session",userInfo.getString("access_token"),userInfo.getLong("expires_in"));

        return "redirect:" + o_url;
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @ResponseBody
    @RequiresPermissions("sys:meituancoupon:info")
    public R info(@PathVariable("id") Long id){
        MeituanCouponEntity meituanCoupon = meituanCouponService.getById(id);

        return R.ok().put("meituanCoupon", meituanCoupon);
    }

    /**
     * 保存
     */
    @ResponseBody
    @RequestMapping("/save")
    @RequiresPermissions("sys:meituancoupon:save")
    public R save(@RequestBody MeituanCouponEntity meituanCoupon){
        meituanCouponService.save(meituanCoupon);

        return R.ok();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("sys:meituancoupon:update")
    public R update(@RequestBody MeituanCouponEntity meituanCoupon){
        ValidatorUtils.validateEntity(meituanCoupon);
        meituanCouponService.updateById(meituanCoupon);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @ResponseBody
    @RequestMapping("/delete")
    @RequiresPermissions("sys:meituancoupon:delete")
    public R delete(@RequestBody Long[] ids){
        meituanCouponService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
