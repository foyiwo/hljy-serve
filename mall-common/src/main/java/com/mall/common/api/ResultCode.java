package com.mall.common.api;

/**
 * 枚举了一些常用API操作码
 * Created by macro on 2019/4/19.
 */
public enum ResultCode implements IErrorCode {
    SUCCESS(200, "操作成功"),
    FAILED(500, "服务器错误，请联系管理员"),
    VALIDATE_FAILED(404, "参数检验失败"),
    SHOW_MESSAGE(801, "显示信息到前端"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    WXUNAUTHORIZED(4011, "暂未登录或token已经过期，准备调用微信支付"),
    FORBIDDEN(405, "没有相关权限"),
    NO_WX_PUBLIC_BINDING(406, "微信没有绑定"),
    UNVERIFIED(402, "未认证"),
    NULL(402, "禁止"),
    NOT_BIND(1234, "请先绑定手机号"),
    FAULTINESS(405, "信息不完善"),
    INFOFULL(2000, "信息完善"),
    INVALID_PARAM(500, "非法参数"),
    TEMP_CODE(500, "...");

    private long code;

    private String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
