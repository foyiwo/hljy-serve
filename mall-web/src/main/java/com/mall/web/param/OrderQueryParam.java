package com.mall.web.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户登录参数
 * Created by macro on 2018/4/26.
 */

@Data
public class OrderQueryParam {
    @ApiModelProperty(value = "支付状态", required = true)
    private Integer patStatus;

}
