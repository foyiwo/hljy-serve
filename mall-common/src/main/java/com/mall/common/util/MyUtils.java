package com.mall.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

/**
 * @author Carl Don
 * @Version 1.0
 */
public class MyUtils {

    private static final ThreadLocal<SimpleDateFormat> formatterHolder = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    @Getter
    private static ObjectMapper objectMapper;

    static {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(simpleDateFormat);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     * @return 线程安全的SimpleDateFormat  format： yyyy-MM-dd HH:mm:ss
     */
    public static SimpleDateFormat getSimpleDateFormat() {
        return formatterHolder.get();
    }

    public static BigDecimal ParseDecimal(String price) {
        if (StringUtils.isBlank(price)) return null;
        price = price.replaceAll("[^0-9.]", "");
        if (StringUtils.isBlank(price)) return null;
        return new BigDecimal(price);
    }

    /**
     * 去掉字符串中所有非数字字符和"."，然后返回 Double
     */
    public static Double ParseDouble(String str) {
        if (StringUtils.isBlank(str)) return null;
        str = str.replaceAll("[^0-9.]", "");
        if (StringUtils.isBlank(str)) return null;
        return Double.parseDouble(str);
    }

    /**
     * 去掉字符串中所有非数字字符，然后返回integer
     */
    public static Integer parseInteger(String str) {
        if (StringUtils.isBlank(str)) return null;
        str = str.replaceAll("[^0-9]", "");
        if (StringUtils.isBlank(str)) return null;
        return Integer.valueOf(str);
    }
}
