package com.mall.web.bo;

import com.mall.mbg.Model.LMember;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * SpringSecurity需要的用户详情
 * Created by macro on 2018/4/26.
 */
public class MallUserDetails implements UserDetails {
    private LMember member;
    public MallUserDetails(LMember inputMember) {
        this.member = inputMember;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回当前用户的权限
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Object getMember() {
        return member;
    }
}
