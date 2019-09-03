package io.renren.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Clarence
 * @Description: 微信公众号配置
 * @Date: 2019/8/8 13:26.
 */
@Configuration
@Data
public class WechatConfig {

    @Value("${weCat.appId}")
    private String weCatAppId;

    @Value("${weCat.appSecret}")
    private String weCatAppSecret;

    /**
     * 特殊的网页授权access_token
     */
    @Value("${weCat.accessTokenByCodeUrl}")
    private String accessTokenByCodeUrl;

    @Value("${weCat.accessTokenUrl}")
    private String accessTokenUrl;

    @Value("${weCat.userInfoUrl}")
    private String weCatUserInfoUrl;

    @Value("${weCat.ticketUrl}")
    private String ticketUrl;

    @Value("${weCat.download_media_url}")
    private String download_media_url;
    @Value("${weCat.online_kf_list}")
    private String online_kf_list;

}
