package com.mall.web.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.mall.common.api.CommonResult;
import com.mall.common.enumconfig.enumWxPlatformStatusCode;
import com.mall.mbg.Mapper.LMemberWechatMapper;
import com.mall.mbg.Model.LMemberWechat;
import com.mall.mbg.Model.LMemberWechatExample;
import com.mall.web.config.WeChatConfig;
import com.mall.web.service.MallMemberService;
import com.mall.web.service.MallWxService;
import com.mall.web.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MallWxServiceImpl implements MallWxService {


    @Autowired
    private MallMemberService   mallMemberService;
    @Autowired
    private LMemberWechatMapper memberWechatMapper;
    @Autowired
    private WeChatConfig        weChatConfig;

    //检查会员是否与公众号绑定,没有则返回一个链接
    @Override
    public CommonResult isBindingWxPublicAndMember() {
        CommonResult result;

        Integer memberId = mallMemberService.getCurrentMemberId();
        LMemberWechatExample memberWechatExample = new LMemberWechatExample();
        memberWechatExample.createCriteria()
                .andMemberIdEqualTo(memberId)
                .andWxPlatformEqualTo(enumWxPlatformStatusCode.WxPublic.getCode());

        List<LMemberWechat> memberWechats = memberWechatMapper.selectByExample(memberWechatExample);
        if(memberWechats != null && memberWechats.size() > 0 && !StrUtil.isEmpty(memberWechats.get(0).getOpenId())){
            result = CommonResult.success(WxPublicAuthorizationURL(memberId));
        }else {
            result = CommonResult.NotBingdingWxPublic(WxPublicAuthorizationURL(memberId));
        }

        return result;
    }

    @Override
    public CommonResult unBindingMemberWxPublic() {
        CommonResult result;

        Integer memberId = mallMemberService.getCurrentMemberId();
        LMemberWechatExample bdmMemberWechatExample = new LMemberWechatExample();
        bdmMemberWechatExample.createCriteria()
                .andMemberIdEqualTo(memberId)
                .andWxPlatformEqualTo(enumWxPlatformStatusCode.WxPublic.getCode());

        List<LMemberWechat> memberWechats = memberWechatMapper.selectByExample(bdmMemberWechatExample);
        if(memberWechats != null && memberWechats.size() > 0 ){
            for (LMemberWechat memberWechat : memberWechats) {
                memberWechat.setMemberId(0);
                memberWechatMapper.updateByPrimaryKeySelective(memberWechat);
            }
            result = CommonResult.success("解绑成功");
        }else {
            result = CommonResult.NotBingdingWxPublic(WxPublicAuthorizationURL(memberId));
        }

        return result;
    }

    //微信公号号网页授权URL拼装
    //开发文档：https://developers.weixin.qq.com/doc/offiaccount/OA_Web_Apps/Wechat_webpage_authorization.html
    public String WxPublicAuthorizationURL(Integer memberId){
        StringBuilder url = new StringBuilder();
        try {
            url.append("https://open.weixin.qq.com/connect/oauth2/authorize");
            url.append("?appid="+weChatConfig.getWxDcPublicAppid());
            //微信公众号授权成功重定向地址
            String redirectUrl = weChatConfig.getWxDcPublicRedirectUrl();
            String callbackUrl = URLEncoder.encode(redirectUrl, "GBK");
            url.append("&redirect_uri="+callbackUrl);
            url.append("&response_type=code");
            url.append("&scope=snsapi_userinfo&state="+memberId+"#wechat_redirect");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url.toString();
    }


    /**微信扫一扫登录 ，登录成功返回JWT Token，否则为null，code为扫描后微信传回来获取微信信息**/
    @Override
    public LMemberWechat getWxUserInfoByCode(String code) {
        LMemberWechat memberWechat = new LMemberWechat();
        try {
            //-------------通过接口向微信后台获取微信账号信息-----------
            String accessTokenUrl = String.format(WeChatConfig.getWxDcPublicAccessTokenUrl(),weChatConfig.getWxDcPublicAppid(),weChatConfig.getWxDcPublicAppSecret(),code);
            //获取access_token
            Map<String ,Object> baseMap =  HttpClientUtil.doGet(accessTokenUrl);
            if(baseMap == null || baseMap.isEmpty()){ return  null; }
            String unionid  = (String) baseMap.get("unionid");

            //如果unionid为空，可能是出现储物，直接返回错误信息
            if(StringUtils.isEmpty(unionid)){
                throw  new BadCredentialsException((String) baseMap.get("errmsg"));
            }

            String accessToken = (String)baseMap.get("access_token");
            String refreshToken = (String)baseMap.get("refresh_token");
            String openId  = (String) baseMap.get("openid");

            //获取用户基本信息
            String userInfoUrl = String.format(WeChatConfig.getOpenUserInfoUrl(),accessToken,openId);
            Map<String ,Object> baseUserMap =  HttpClientUtil.doGet(userInfoUrl);

            //微信昵称
            String nickname = (String)baseUserMap.get("nickname");
            if(!StringUtils.isEmpty(nickname))
                nickname = new String(nickname.getBytes("ISO-8859-1"), "UTF-8");//解决乱码

            //省
            String province = (String)baseUserMap.get("province");
            if(!StringUtils.isEmpty(province))
                province = new String(province.getBytes("ISO-8859-1"), "UTF-8");//解决乱码

            //市
            String city = (String)baseUserMap.get("city");
            if(!StringUtils.isEmpty(city))
                city = new String(city.getBytes("ISO-8859-1"), "UTF-8");//解决乱码

            //区
            String country = (String)baseUserMap.get("country");
            if(!StringUtils.isEmpty(country))
                country = new String(country.getBytes("ISO-8859-1"), "UTF-8");//解决乱码

            //微信头像URL
            String headimgurl = (String)baseUserMap.get("headimgurl");
            //性别
            Double sexTemp  = (Double) baseUserMap.get("sex");
            //语言
            String language  = (String) baseUserMap.get("language");

            memberWechat = new LMemberWechat();
            memberWechat.setWxPlatform(enumWxPlatformStatusCode.WxPublic.getCode());
            memberWechat.setUnionId(unionid);
            memberWechat.setNickname(nickname);
            memberWechat.setProvince(province);
            memberWechat.setCity(city);
            memberWechat.setCountry(country);
            memberWechat.setHeadimgurl(headimgurl);
            memberWechat.setOpenId(openId);
            memberWechat.setAccessToken(accessToken);
            memberWechat.setRefreshToken(refreshToken);
            memberWechat.setSex(sexTemp.toString());
            memberWechat.setLanguage(language);
            memberWechat.setUnionId(unionid);

            //------------开始保存会员数据----

        } catch (Exception e) {
        }
        return memberWechat;
    }



    @Override
    public LMemberWechat callbackWxUserInfoByCode(String code,String state) {
        LMemberWechat userInfoByCode = getWxUserInfoByCode(code);
        if(userInfoByCode != null && !StrUtil.isEmpty(userInfoByCode.getOpenId()) ){
            //返回数据为Long，则直接保存数据
            if(NumberUtil.isLong(state)){
                userInfoByCode.setMemberId(Integer.valueOf(state));
                //查询会员是否已经绑定过微信公众号
                LMemberWechatExample bdmMemberWechatExample = new LMemberWechatExample();
                bdmMemberWechatExample.createCriteria()
                        .andOpenIdEqualTo(userInfoByCode.getOpenId())
                        .andMemberIdEqualTo(userInfoByCode.getMemberId())
                        .andWxPlatformEqualTo(enumWxPlatformStatusCode.WxPublic.getCode());
                List<LMemberWechat> memberWechats = memberWechatMapper.selectByExample(bdmMemberWechatExample);
                if(memberWechats != null && memberWechats.size() > 0){
                    LMemberWechat memberWechat = memberWechats.get(0);
                    userInfoByCode.setId(memberWechat.getId());
                    userInfoByCode.setMemberId(memberWechat.getMemberId());
                    memberWechatMapper.updateByPrimaryKey(userInfoByCode);
                }else {
                    userInfoByCode.setCreateTime(new DateTime());
                    memberWechatMapper.insertSelective(userInfoByCode);
                }
            }
        }
        return userInfoByCode;
    }

    @Override
    public LMemberWechat getWeChatInfoByMemberIdInWxPublic(Integer memberId) {
        LMemberWechatExample bdmMemberWechatExample = new LMemberWechatExample();
        bdmMemberWechatExample.createCriteria()
                .andMemberIdEqualTo(memberId)
                .andWxPlatformEqualTo(enumWxPlatformStatusCode.WxPublic.getCode());

        bdmMemberWechatExample.setOrderByClause(" id desc ");
        List<LMemberWechat> memberWechats = memberWechatMapper.selectByExample(bdmMemberWechatExample);
        if(memberWechats != null && memberWechats.size() > 0){
            return memberWechats.get(0);
        }
        return null;
    }

    //获取小程序的accessToken,并非开发平台的accessToken。
    private  String getToken() {
        String accessTokenUrl = String.format(WeChatConfig.getMiniProgramTokenUrl(),weChatConfig.getWxspAppid(),weChatConfig.getWxspSecret());
        //获取access_token
        Map<String ,Object> baseMap =  HttpClientUtil.doGet(accessTokenUrl);
        if(baseMap == null || baseMap.isEmpty()){ return  null; }
        String accessToken = (String)baseMap.get("access_token");
        return accessToken;
    }



}
