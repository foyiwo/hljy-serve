package com.mall.web.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户登录参数
 * Created by macro on 2018/4/26.
 */

@Data
public class MemberLoginParam {
    @ApiModelProperty(value = "登录类型(1:账号密码登录)", required = true)
    private int type;

    @ApiModelProperty(value = "用户名", required = true)
    private String userName;

    @ApiModelProperty(value = "密码", required = true)
    private String passWord;
}
