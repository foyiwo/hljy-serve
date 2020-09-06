package com.mall.web.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderPayParam {

    @ApiModelProperty(value = "订单编号", required = true)
    private String orderSn;

    @ApiModelProperty(value = "订单ID", required = true)
    private Integer orderId;

    @ApiModelProperty(value = "支付方式ID")
    private Integer payId;

    @ApiModelProperty(value = "支付方式名称")
    private String payName;

    @ApiModelProperty(value = "支付配置")
    private Object payConfig;

    @ApiModelProperty(value = "订单来源")
    private Integer sourceType;

    @ApiModelProperty(value = "订单总额")
    private Double orderPayAmount;

    @ApiModelProperty(value = "订单总额")
    private String body;

    @ApiModelProperty(value = "wxCode，用于微信支付")
    private String wxCode;






}
