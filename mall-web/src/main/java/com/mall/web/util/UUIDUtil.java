package com.mall.web.util;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.UUID;

/****************************************************
 *
 * 封装各种生成唯一性ID算法的工具类.
 *
 * @version 1.0
 **************************************************/
@Service
@Lazy(false)
public class UUIDUtil  implements Serializable{

    private static SecureRandom random = new SecureRandom();

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0,32);
    }

    /**
     * 使用SecureRandom随机生成Long.
     */
    public static long randomLong() {
        return Math.abs(random.nextLong());
    }

}