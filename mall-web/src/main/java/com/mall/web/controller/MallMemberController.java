package com.mall.web.controller;


import cn.hutool.core.bean.BeanUtil;
import com.mall.common.api.CommonResult;
import com.mall.mbg.Mapper.LMemberWechatMapper;
import com.mall.mbg.Model.LMember;
import com.mall.mbg.Model.LMemberWechat;
import com.mall.mbg.Model.LMemberWechatExample;
import com.mall.web.dto.LMemberDto;
import com.mall.web.dto.LoginResultDto;
import com.mall.web.param.MemberLoginParam;
import com.mall.web.service.MallMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = "用户管理")
@Controller
@RequestMapping(value = "/member")
public class MallMemberController {

    @Autowired
    private LMemberWechatMapper memberWechatMapper;
    @Autowired
    private MallMemberService mallMemberService;

    @ApiOperation(value = "账号密码登陆")
    @RequestMapping(value = "/loginByuserName", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult loginByuserName(@RequestBody MemberLoginParam input)
    {
        try {
            LoginResultDto result = mallMemberService.loginByUserName(input);
            return CommonResult.success(result);
        }catch (Exception e) {
            return CommonResult.failed(e.getMessage());
        }

    }

    @ApiOperation("获取当前登陆的用户")
    @RequestMapping(value = "/getCurrentMember", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<LMemberDto> getCurrentMember(){
        try {
            LMemberDto memberDto = new LMemberDto();
            LMember bdmMember = mallMemberService.getCurrentMember();
            if(bdmMember != null && bdmMember.getId()>0){
                BeanUtil.copyProperties(bdmMember,memberDto);

                LMemberWechatExample memberWechatExample = new LMemberWechatExample();
                memberWechatExample.createCriteria()
                        .andMemberIdEqualTo(bdmMember.getId());
                List<LMemberWechat> lMemberWechats = memberWechatMapper.selectByExample(memberWechatExample);
                if(lMemberWechats != null && lMemberWechats.size() > 0){
                    memberDto.setWechat(lMemberWechats.get(0));
                }
                return CommonResult.success(memberDto);
            }
        }catch (Exception ex){
            return CommonResult.failed(ex.getMessage());
        }
        return CommonResult.validateFailed("操作异常");
    }
}
