package com.mall.web.service;

import com.mall.common.api.CommonResult;
import com.mall.mbg.Model.LMemberWechat;
import io.swagger.models.auth.In;

public interface MallWxService {
    //检查会员是否与公众号绑定(德成在线)
    CommonResult isBindingWxPublicAndMember();

    //解绑会员的微信
    CommonResult unBindingMemberWxPublic();

    //根据code获取微信用户信息（微信公众号）
    LMemberWechat getWxUserInfoByCode(String code);

    //处理code获取微信用户信息（微信公众号）
    LMemberWechat callbackWxUserInfoByCode(String code, String state);

    //根据会员ID获取该会员当时绑定的微信
    LMemberWechat getWeChatInfoByMemberIdInWxPublic(Integer memberId);
}
