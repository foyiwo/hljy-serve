package com.mall.common.enumconfig;

public enum enumWxPlatformStatusCode {
    WxMini(1, "微信小程序"),
    WxPublic(2, "微信公号号");

    private int code;
    private String message;

    private enumWxPlatformStatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
