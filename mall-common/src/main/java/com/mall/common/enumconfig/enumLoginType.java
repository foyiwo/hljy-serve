package com.mall.common.enumconfig;

public enum enumLoginType {
    AccountLogin(1, "学号密码登录"),
    WeChatLogin(3, "微信授权登录");

    private int code;
    private String message;

    private enumLoginType(int code, String message) {
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
