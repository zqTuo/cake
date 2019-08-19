package io.renren.config;

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
public class LoginConfig {
    @Value("${weCat.appId}")
    private String weCatAppId;

    @Value("${weCat.appSecret}")
    private String weCatAppSecret;

    @Value("${weCat.accessTokenUrl}")
    private String weCatAccessTokenUrl;

    @Value("${weCat.userInfoUrl}")
    private String weCatUserInfoUrl;

}
