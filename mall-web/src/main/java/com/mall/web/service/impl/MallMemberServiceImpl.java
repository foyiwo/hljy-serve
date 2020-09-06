package com.mall.web.service.impl;

import cn.hutool.core.util.StrUtil;
import com.mall.common.api.CommonResult;
import com.mall.common.api.ResultCode;
import com.mall.common.enumconfig.enumLoginType;
import com.mall.mbg.Mapper.LMemberMapper;
import com.mall.mbg.Model.LMember;
import com.mall.mbg.Model.LMemberExample;
import com.mall.web.bo.MallUserDetails;
import com.mall.web.dto.LoginResultDto;
import com.mall.web.param.MemberLoginParam;
import com.mall.web.service.MallMemberService;
import com.mall.web.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class MallMemberServiceImpl implements MallMemberService {
    @Autowired
    private LMemberMapper      memberMapper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil       jwtTokenUtil;

    @Override
    public Integer getCurrentMemberId() {
        return getCurrentMember().getId();
    }

    @Override
    public LMember getCurrentMember() {
        LMember dto = new LMember();
        try {
            SecurityContext securityContext = SecurityContextHolder.getContext();
            Authentication authentication = securityContext.getAuthentication();
            Object principal = authentication.getPrincipal();
            if (principal instanceof MallUserDetails) {
                MallUserDetails userDetails = ((MallUserDetails)principal);
                dto = (LMember)userDetails.getMember();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return dto;
    }

    @Override
    public LMember getMemberByUsername(String username) {

        LMemberExample memberExample = new LMemberExample();
        memberExample.createCriteria()
                .andUserNameEqualTo(username);

        List<LMember> lMembers = memberMapper.selectByExample(memberExample);
        if(lMembers != null && lMembers.size() > 0){
            return lMembers.get(0);
        }

        return new LMember();
    }

    @Override
    public LoginResultDto loginByUserName(MemberLoginParam loginParam) {
        LoginResultDto loginResult = new LoginResultDto();
        String token = null;
        //账号密码登录
        if(loginParam.getType() == enumLoginType.AccountLogin.getCode()){
            if(loginParam.getUserName() == null
                    || loginParam.getPassWord() == null
                    || loginParam.getUserName().isEmpty()
                    || loginParam.getPassWord().isEmpty()){
                throw new BadCredentialsException("您的账号或密码还未填写，请注意查看");
            }
            if(!this.judgeUserNameIsRegister(loginParam.getUserName())){
                throw new BadCredentialsException("检测到该学号不存在，请注意检查");
            }
            LMember member = this.getMemberByStudentNoAndIDCart(loginParam.getUserName());
            String sqlPassword = member.getPassword();

            if(StrUtil.isEmpty(sqlPassword)){
                sqlPassword = member.getIdCart().substring(member.getIdCart().length()-6,member.getIdCart().length());
            }
            if (!sqlPassword.equals(loginParam.getPassWord())){
                throw new BadCredentialsException("密码不正确");
            }
            token = getLoginTokenByUserName(member.getUserName());

        }

        //判断是否登录成功
        if(!StringUtils.isEmpty(token)){
            //检测会员信息是否完整
            loginResult.setToken(token);
            loginResult.setCode(ResultCode.SUCCESS.getCode());
        }
        else {
            throw new BadCredentialsException("登录失败");
        }

        return loginResult;
    }

    @Override
    /**通过用户名获取JWT Token**/
    public String getLoginTokenByUserName(String username){
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenUtil.generateToken(userDetails);
    }

    public LMember getMemberByStudentNoAndIDCart(String username) {
        LMemberExample example = new LMemberExample();
        example.createCriteria().andStudentNoEqualTo(username);
        example.or().andIdCartEqualTo(username);

        List<LMember> bdmMemberList = memberMapper.selectByExample(example);
        if (bdmMemberList != null && bdmMemberList.size() > 0) {
            return bdmMemberList.get(0);
        }
        return null;
    }

    @Override
    public LMember getUserByUsername(String username) {
        LMemberExample example = new LMemberExample();
        example.createCriteria().andStudentNoEqualTo(username);

        List<LMember> bdmMemberList = memberMapper.selectByExample(example);
        if (bdmMemberList != null && bdmMemberList.size() > 0) {
            return bdmMemberList.get(0);
        }
        return null;
    }

    public boolean judgeUserNameIsRegister(String username){

        LMemberExample example = new LMemberExample();
        example.createCriteria().andStudentNoEqualTo(username);
        List<LMember> BdmMemberList = memberMapper.selectByExample(example);
        return BdmMemberList.size() > 0;
    }
}
