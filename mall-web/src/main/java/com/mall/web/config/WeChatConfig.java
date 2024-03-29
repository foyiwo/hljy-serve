package com.mall.web.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 微信配置类
 */
@Configuration
@Data
public class WeChatConfig {

//    /**
//     * 公众号appid
//     */
//    @Value("${wxpay.appid}")
//    private String appId;
//
//    /**
//     * 公众号秘钥
//     */
//    @Value("${wxpay.appsecret}")
//    private String appsecret;


    /**
     * 微信公众号(德成在线服务号)Appid
     */
    @Value("${wxdcpublic.appid}")
    private String wxDcPublicAppid;

    /**
     * 微信公众号(德成在线服务号)appsecret
     */
    @Value("${wxdcpublic.appsecret}")
    private String wxDcPublicAppSecret;
    /**
     * 微信公众号(德成在线服务号)redirect_url
     */
    @Value("${wxdcpublic.redirect_url}")
    private String wxDcPublicRedirectUrl;



    /**
     * 开放平台appid
     */
    @Value("${wxopen.appid}")
    private String openAppid;

    /**
     * 开放平台appsecret
     */
    @Value("${wxopen.appsecret}")
    private String openAppsecret;


    /**
     * 小程序密钥
     */
    @Value("${wxopen.wxsp_secret}")
    private String wxspSecret;


    /**
     * 小程序appid
     */
    @Value("${wxdcpublic.appid}")
    private String wxspAppid;

    /**
     * 小程序授权类型
     */
    @Value("${wxopen.wxsp_grant_type}")
    private String grantType;

    /**
     * 开放平台回调url
     */
    @Value("${wxopen.redirect_url}")
    private String openRedirectUrl;


    /**
     * 微信开放平台二维码连接
     */
    private final static String OPEN_QRCODE_URL= "https://open.weixin.qq.com/connect/qrconnect?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_login&state=%s#wechat_redirect";


    /**
     * 开放平台获取access_token地址
     */
    private final static String OPEN_ACCESS_TOKEN_URL="https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

    /**
     * 小程序获取access_token地址
     */
    private final static String MINI_PROGRAM_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
    //

    /**
     * 获取用户信息
     */
    private final static String OPEN_USER_INFO_URL ="https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";

    /**
     * 商户号id
     */
    @Value("${wxpay.mer_id}")
    private String mchId;


    /**
     * 支付key
     */
    @Value("${wxpay.key}")
    private  String key;

    /**
     * 微信支付回调url
     */
    @Value("${wxpay.callback}")
    private  String payCallbackUrl;

    /**
     * 统一下单url
     */
    private static final String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    public static String getUnifiedOrderUrl() {
        return UNIFIED_ORDER_URL;
    }

    public static String getOpenUserInfoUrl() {
        return OPEN_USER_INFO_URL;
    }

    public static String getOpenAccessTokenUrl() {
        return OPEN_ACCESS_TOKEN_URL;
    }

    public static String getMiniProgramTokenUrl() {
        return MINI_PROGRAM_TOKEN_URL;
    }

    public static String getWxDcPublicAccessTokenUrl() {
        return OPEN_ACCESS_TOKEN_URL;
    }

    public static String getOpenQrcodeUrl() {
        return OPEN_QRCODE_URL;
    }


}
