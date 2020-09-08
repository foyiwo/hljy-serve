package com.mall.web.param;

import cn.hutool.core.date.DateUtil;
import com.mall.web.util.IpUtil;
import com.mall.web.util.UUIDUtil;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @description: 微信请求参数
 */
@Component
@Data
public class WxParam {
    /*微信分配的小程序ID*/
    private String appid;

    /*微信支付分配的商户号*/
    private String mch_id;

    /*随机字符串，长度要求在32位以内*/
    private String nonce_str;

    /*签名*/
    private String sign;

    /*商品描述*/
    private String body;

    /*商户订单号*/
    private String out_trade_no;

    /*标价金额 默认分*/
    private Integer total_fee;

    /*终端IP*/
    private String spbill_create_ip;

    /*通知地址*/
    private String notify_url;

    /*交易类型*/
    private String trade_type;

    /*用户的openid*/
    private String openid;



    /*组装微信支付请求的参数*/
    public void setWxParam(WxParam wxParam, OrderPayParam input
            , HttpServletRequest request
            , String  notify_url
            , String openid
            , String wxspAppid
            , String mchId) {

        wxParam.setAppid(wxspAppid);
        wxParam.setMch_id(mchId);
        wxParam.setNonce_str(UUIDUtil.uuid());
        wxParam.setSpbill_create_ip(IpUtil.getIpAddr(request));
        wxParam.setTrade_type("JSAPI");
        wxParam.setOut_trade_no(input.getOrderSn()+"-"+DateUtil.currentSeconds());
        wxParam.setBody(input.getBody());//此处用于商品描述，暂时用备注代替
        wxParam.setOpenid(openid);
        wxParam.setTotal_fee((int)(input.getOrderPayAmount()*100));
        wxParam.setNotify_url(notify_url);
    }
}