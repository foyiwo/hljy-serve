package com.mall.common.api;

/**
 * 封装API的错误码
 * {@link ResultCode }是基础实现类，包含了一些基本错误 IErrorCode
 * <p>
 * 各模块可以根据业务参照 {@link ResultCode}实现一个新的枚举类，自定义 IErrorCode
 * <p>
 * Created by macro on 2019/4/19.
 * <p>
 * editBy Carl Don 2020-04-30 09:57:57
 */
public interface IErrorCode {

    long getCode();

    String getMessage();
}
