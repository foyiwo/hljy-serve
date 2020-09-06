package com.mall.web.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginResultDto {

    @ApiModelProperty(value = "登录Token")
    private String token;

    @ApiModelProperty(value = "会员信息是否完整")
    private Long code;

    @ApiModelProperty(value = "提示信息")
    private String message;

    @ApiModelProperty(value = "是否为游客")
    private Boolean isTourists;

}
