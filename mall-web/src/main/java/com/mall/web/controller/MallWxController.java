package com.mall.web.controller;

import com.mall.common.api.CommonResult;
import com.mall.mbg.Model.LMemberWechat;
import com.mall.web.service.MallWxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

@Api(tags = "微信开发接口",description = "微信小程序，公众号开发接口")
@Controller
@RequestMapping("/wx")
public class MallWxController {

    @Autowired
    private MallWxService mallWxService;


    @ApiOperation("检查会员是否与公众号绑定(德成在线):状态：405(没有绑定)，200(绑定成功)")
    @RequestMapping(value = "/isBindingWxPublicAndMember",method = RequestMethod.GET)
    @ResponseBody
    public CommonResult isBindingWxPublicAndMember(){
        CommonResult result;
        try {
            result = mallWxService.isBindingWxPublicAndMember();
        }catch (Exception ex){
            result = CommonResult.failed(ex.getMessage());
        }
        return result;
    }

    @ApiOperation("解绑会员的微信")
    @RequestMapping(value = "/unBindingMemberWxPublic",method = RequestMethod.GET)
    @ResponseBody
    public CommonResult unBindingMemberWxPublic(){
        CommonResult result;
        try {
            result = mallWxService.unBindingMemberWxPublic();
        }catch (Exception ex){
            result = CommonResult.failed(ex.getMessage());
        }
        return result;
    }

    @ApiOperation("微信公众号授权成功回调地址")
    @RequestMapping(value = "/auth/callback",method = RequestMethod.GET)
    @ResponseBody
    public String authCallback(@RequestParam(value = "code") String code,@RequestParam(value = "state") String state){
        System.out.println("微信回调,code："+code+",state："+state);
        LMemberWechat userInfoByCode = mallWxService.callbackWxUserInfoByCode(code,state);
        return "";
    }


}
