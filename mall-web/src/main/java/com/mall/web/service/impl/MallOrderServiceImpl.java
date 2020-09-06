package com.mall.web.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.mall.common.api.CommonResult;
import com.mall.common.enumconfig.enumWxPlatformStatusCode;
import com.mall.mbg.Mapper.LMemberWechatMapper;
import com.mall.mbg.Mapper.LOrderMapper;
import com.mall.mbg.Model.LMemberWechat;
import com.mall.mbg.Model.LMemberWechatExample;
import com.mall.mbg.Model.LOrder;
import com.mall.mbg.Model.LOrderExample;
import com.mall.web.config.WeChatConfig;
import com.mall.web.param.OrderQueryParam;
import com.mall.web.service.MallMemberService;
import com.mall.web.service.MallOrderService;
import com.mall.web.service.MallWxService;
import com.mall.web.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@Service
public class MallOrderServiceImpl implements MallOrderService {


    @Autowired
    private MallMemberService mallMemberService;
    @Autowired
    private LOrderMapper      orderMapper;

    @Override
    public List<LOrder> orderList(OrderQueryParam orderQueryParam) {

        Integer currentMemberId = mallMemberService.getCurrentMemberId();
        if(currentMemberId == null || currentMemberId <= 0){
            throw new BadCredentialsException("未登录，无法查询");
        }

        LOrderExample orderExample = new LOrderExample();
        LOrderExample.Criteria criteria = orderExample.createCriteria();
        criteria.andMemberIdEqualTo(currentMemberId);

        if(orderQueryParam != null){
            if(orderQueryParam.getPatStatus() != null){
                criteria.andPayStatusEqualTo(orderQueryParam.getPatStatus());
            }
        }

        return  orderMapper.selectByExample(orderExample);
    }
}
