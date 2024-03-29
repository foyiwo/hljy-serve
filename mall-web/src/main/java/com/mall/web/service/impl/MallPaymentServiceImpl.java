package com.mall.web.service.impl;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.json.JSONUtil;
import com.mall.mbg.Mapper.LLogsMapper;
import com.mall.mbg.Mapper.LOrderMapper;
import com.mall.mbg.Model.LLogs;
import com.mall.mbg.Model.LOrder;
import com.mall.mbg.Model.LOrderExample;
import com.mall.web.config.WeChatConfig;
import com.mall.web.dto.OrderStatusDto;
import com.mall.web.param.OrderPayParam;
import com.mall.web.param.WxParam;
import com.mall.web.sdk.Signature;
import com.mall.web.service.MallMemberService;
import com.mall.web.service.MallOrderService;
import com.mall.web.service.MallPaymentService;
import com.mall.web.util.*;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * BdmPaymentServiceImpl 服务接口实现类
 *
 * @version 1.0
 * @date 2019-06-13 08:55:11
 */
@Service
public class MallPaymentServiceImpl implements MallPaymentService {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private LOrderMapper      orderMapper;
    @Autowired
    private MallMemberService mallMemberService;
    @Autowired
    private WxParam   wxParam;
    @Autowired
    private Signature    signature;
    @Autowired
    private WeChatConfig     weChatConfig;
    @Autowired
    private MallOrderService mallOrderService;
    @Autowired
    private LLogsMapper logsMapper;
    @Value("${wxpay.mer_id}")
    private String mchId;
    @Value("${wxdcpublic.appid}")
    private String wxspAppid;

    
    //private static Logger logger = LoggerFactory.getLogger(BdmPaymentServiceImpl.class);
    /*微信通知异步回调*/
    public static final String notify_url = "http://hljy.foyiwo.com/api/web/Pay/wxNotifyResult";

    @Override
    public Object getOrderPayConfig(OrderPayParam input, Boolean parent) {
        Object res = null;

        try {
            res = this.miniProgramsWxPay(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    private Object miniProgramsWxPay(OrderPayParam input) throws Exception {
//		"opbgU0XQ7SqU7sgpUr6RZ0j7cLS0";//
        LOrder lOrder = orderMapper.selectByPrimaryKey(input.getOrderId());

        input.setBody(lOrder.getRemark());
        input.setOrderSn(lOrder.getOrderSn());
        input.setOrderPayAmount(lOrder.getOrderAmount().doubleValue());

        Integer memberId = lOrder.getMemberId();
        String openid = mallMemberService.getMemberWechatByMemberId(memberId).getOpenId();
        if (StringUtils.isEmpty(openid)) {
            throw new BadCredentialsException("openid为空，无法支付");
        }
        wxParam.setWxParam(wxParam, input, request, notify_url, openid, wxspAppid, mchId);
        wxParam.setBody("华联教育(" + wxParam.getOut_trade_no()+")"+ cn.hutool.core.date.DateUtil.currentSeconds());
        Map<String, String> wxParamTOMap = BeanUtil.convertBeanToMap(wxParam);
        String sign = signature.getSign(wxParamTOMap, weChatConfig.getKey());
        wxParamTOMap.put("sign", sign);
        //logger.info("微信小程序支付请求的Map===>"+wxParamTOMap);
        String xmlResponse = WxPayHttp.doPostPayUnifiedOrder(weChatConfig.getUnifiedOrderUrl(), XmlUtil.createRequestXml(wxParamTOMap));
        Map<String, String> wxParamVO = parseXmlResponse(xmlResponse);
        wxParamVO.put("nonce_str", wxParamVO.get("nonceStr"));
        return wxParamVO;
    }

    @Override
    public void notifyResult(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		System.out.println("微信回调request===>"+request);
        //获取微信异步通知的数据
        Map<String, String> paramsToMap = XmlUtil.reqParamsToMap(request);
        String[] out_trade_nos = paramsToMap.get("out_trade_no").split("-");
        String orderSn = out_trade_nos[0];
//		System.out.println("微信回调request===>"+JSONUtil.toJsonStr(paramsToMap));
        LLogs logs = new LLogs();
        logs.setLog(JSONUtil.toJsonStr(paramsToMap));
        logs.setType("wxPay");
        logs.setOutTradeNo(orderSn);
        logsMapper.insertSelective(logs);
        //校验微信的sign值
        boolean flag = signature.validateSign(paramsToMap, weChatConfig.getKey());
        if (true) {
//			System.out.println("微信回调订单单号===>"+paramsToMap.get("out_trade_no"));

            LOrder bdmOrder = mallOrderService.getOrderByOrderSn(orderSn);
//			System.out.println("微信回调订单实体===>"+bdmOrder.getOrderSn());
            //判断是否是未支付状态
            //支付状态: 0->未支付；1->付款中；2->已付款；
            if (bdmOrder.getPayStatus() != 1) {
                //更新订单状态
                //bdmOrder.setOrderStatus(2);
                bdmOrder.setPayStatus(1);//支付状态: 0->未支付；1->付款中；2->已付款；
//				bdmOrder.setTransaction_id(paramsToMap.get("transaction_id"));
//				bdmOrder.setPaymentTime(DateUtil.timeToDate(Long.valueOf(paramsToMap.get("time_end")),DateUtil.DATE_TIME_PATTERN));
                bdmOrder.setPaymentTime(new DateTime());
                bdmOrder.setModifyTime(new DateTime());
                //bdmOrder.setNotify_result(JSON.toJson(paramsToMap));
                //bdmOrderMapper.un(bdmOrder);
                System.out.println("准备更新===>" + bdmOrder.getOrderSn());
                orderMapper.updateByPrimaryKeySelective(bdmOrder);

                //如果订单修改成功,通知微信接口不要在回调这个接口
                WxPayHttp.responseXmlSuccess(response);
            }
        }
    }

    /**
     * 轮询该订单是否已支付
     */
    @Override
    public OrderStatusDto findOrderIsPay(Integer orderId) {
        LOrder bdmOrder = orderMapper.selectByPrimaryKey(orderId);
        //订单状态：0->未确认；1->已确认；2->已完成；3->已取消；4->无效订单 && 支付状态：0->未支付；1->已支付;
        OrderStatusDto orderStatusDto = new OrderStatusDto();
        orderStatusDto.setPayStatus(0);
        if (null != bdmOrder && bdmOrder.getOrderId() > 0) {
            orderStatusDto.setPayStatus(bdmOrder.getPayStatus());
            orderStatusDto.setOrderStatus(bdmOrder.getOrderStatus());
            if (bdmOrder.getPayStatus() == 1) {
                orderStatusDto.setOrderStatusName("订单已完成");
                orderStatusDto.setPayStatusName("订单已支付");
                return orderStatusDto;
            }
        }
        return orderStatusDto;
    }

    /*解析微信请求响应的数据并返回小程序需要的数据*/
    private Map<String, String> parseXmlResponse(String xmlResponse) {
        Map<String, String> resultMap = new HashMap<>();
        //判断sign值是否正确
        Boolean flag = signature.validateResponseXmlToMap(xmlResponse);
        if (flag) {
            Map<String, String> map = XmlUtil.xmlToMap(xmlResponse);
            //logger.info("微信支付请求响应的数据的预支付ID====>"+map.get("prepay_id"));
            resultMap.put("appId", weChatConfig.getWxspAppid());
            resultMap.put("timeStamp", String.valueOf(DateUtil.strToTime(DateUtil.format(new DateTime()))));
            resultMap.put("nonceStr", UUIDUtil.uuid());
            resultMap.put("package", "prepay_id=" + map.get("prepay_id"));//预支付 id
            resultMap.put("signType", "MD5");
            String sign = signature.getSign(resultMap, weChatConfig.getKey());
            resultMap.put("paySign", sign);

        }
        return resultMap;
    }

}