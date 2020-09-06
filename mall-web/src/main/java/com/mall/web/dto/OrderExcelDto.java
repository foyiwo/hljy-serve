package com.mall.web.dto;

import com.mall.mbg.Model.LMember;
import com.mall.mbg.Model.LOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderExcelDto extends LMember {

    @ApiModelProperty(value = "金额 ")
    private    Double orderAmount;

    @ApiModelProperty(value = "订单SN")
    private    String orderSn;


}
