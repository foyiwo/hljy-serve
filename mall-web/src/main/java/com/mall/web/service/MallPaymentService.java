package com.mall.web.service;

import com.mall.web.dto.OrderStatusDto;
import com.mall.web.param.OrderPayParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


/**
 * BdmPaymentService 服务接口
 * @date 2019-06-13 08:55:11
 * @version 1.0
 */

public interface MallPaymentService {

	Object getOrderPayConfig(OrderPayParam bdmOrder, Boolean parent);

	/** 小程序微信支付回调通知 */
	void notifyResult(HttpServletRequest request, HttpServletResponse response) throws Exception;

	/** 轮询该订单是否已支付 */
	OrderStatusDto findOrderIsPay(String orderSn);
}