package com.mall.web.service;


import com.mall.common.api.CommonResult;
import com.mall.mbg.Model.LMember;
import com.mall.mbg.Model.LMemberWechat;
import com.mall.web.dto.LoginResultDto;
import com.mall.web.param.MemberLoginParam;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Api(tags = "用户管理", description = "MallMemberController")
@Controller
@RequestMapping(value = "/member")
public interface MallMemberService {

    Integer getCurrentMemberId();

    LMember getCurrentMember();

    LMember getMemberByUsername(String username);

    Integer getMemberIdByUsername(String username);

    LoginResultDto loginByUserName(MemberLoginParam input);

    /**通过用户名获取JWT Token**/
    String getLoginTokenByUserName(String username);

    LMember getUserByUsername(String username);

    LMemberWechat getMemberWechatByCode(String wxCode);
}
