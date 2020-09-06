package com.mall.common.util;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;

import java.util.Date;

public class DateTimeUtil {
    public static String formatDateToNorm(Date date){
        return DateUtil.format(date, DatePattern.NORM_DATETIME_PATTERN);
    }
}
