package com.mall.web.service;


import com.mall.mbg.Model.LMember;
import com.mall.mbg.Model.LOrder;
import com.mall.web.dto.LoginResultDto;
import com.mall.web.param.MemberLoginParam;
import com.mall.web.param.OrderQueryParam;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.InputStream;
import java.util.List;


@Api(tags = "订单管理", description = "MallMemberController")
@Controller
@RequestMapping(value = "/order")
public interface MallOrderService {

    List<LOrder> orderList(OrderQueryParam orderQueryParam);

    LOrder getOrderByOrderSn(String OrderSn);

    String importOrderByExcel(InputStream inputStream);
}
