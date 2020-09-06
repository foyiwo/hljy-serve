package com.mall.web.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderStatusDto {

    @ApiModelProperty(value = "支付状态：0->未支付；1->已支付; ")
    private Integer payStatus;
    @ApiModelProperty(value = "状态名称")
    private String payStatusName;

    @ApiModelProperty(value = "订单状态：0->未确认；1->已确认；2->已完成；3->已取消；4->无效订单")
    private Integer orderStatus;
    @ApiModelProperty(value = "订单状态名称")
    private String orderStatusName;

}
