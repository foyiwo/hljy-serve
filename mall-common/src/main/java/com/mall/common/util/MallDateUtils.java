package com.mall.common.util;


import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/***
 * 时间工具类
 *
 * @author cgr
 *
 */

public class MallDateUtils {
    /*** yyyy-MM-dd **/
    public final static String ymd = "yyyy-MM-dd";
    /** yyyy-MM-dd HH:mm:ss **/
    public final static String ymdhms = "yyyy-MM-dd HH:mm:ss";
    /** yyyy-MM-dd HH:mm */
    public final static String ymdhm = "yyyy-MM-dd HH:mm";
    /** HH:mm **/
    public final static String hm = "HH:mm";
    /** MM-dd HH:mm */
    public final static String mdhm = "MM-dd HH:mm";
    /** yyyy-MM */
    public final static String ym = "yyyy-MM";
    /** MM-dd HH **/
    public final static String mdh = "MM-dd HH";
    /** yyyy年MM月 ****/
    public final static String ymz = "yyyy年MM月";
    /** yyyy年MM月dd日 */
    public final static String ymdz = "yyyy年MM月dd日";
    /** yyyy年MM月dd日 HH时mm分 */
    public final static String ymdhmz = "yyyy年MM月dd日 HH时mm分";
    /** yyyy年MM月dd日 HH时mm分ss秒*/
    public final static String ymdhmzs = "yyyy年MM月dd日 HH时mm分ss秒";
    /** MM月dd日 HH时mm分 */
    public final static String mdhmz = "MM月dd日 HH时mm分";
    /** MM月dd日 */
    public final static String mdz = "MM月dd日";
    /** yyyyMM **/
    public final static String ymGapless = "yyyyMM";
    /** yyyyMMdd **/
    public final static String ymdGapless = "yyyyMMdd";
    /** yyyyMMddHHmmss **/
    public final static String ymdhmsGapless = "yyyyMMddHHmmss";
    /** yyyyMMddHHmmssffff **/
    public final static String ymdhmsfGapless = "yyyyMMddHHmmssSSSS";

    /**yyyy/MM/dd HH:mm***/
    public final static String ymdhmSlant = "yyyy/MM/dd HH:mm";
    /** yyyy/MM/dd HH:mm:ss **/
    public final static String ymdhmsSlant = "yyyy/MM/dd HH:mm:ss";
    /** DateFormat缓存 */
    public final static Map<String, DateFormat> FORMAT_CACHE = new HashMap<String, DateFormat>();

    /***
     * 根据指定格式将时间字符串转化为时间
     *
     * @param dateStr
     * @param parseStr
     * @return
     * @throws ParseException
     *
     *
     */
    public static Date parse(String parseStr, String dateStr) {
        DateFormat format = getFormat(parseStr);
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /***
     * 根据formatStr获得DateFormat对象
     *
     * @param formatStr
     * @return
     */
    private static DateFormat getFormat(String formatStr) {
        DateFormat format = FORMAT_CACHE.get(formatStr);
        if (format == null) {
            format = new SimpleDateFormat(formatStr);
            FORMAT_CACHE.put(formatStr, format);
        }
        return format;
    }

    /****
     * 判断字符串格式是否正确
     *
     * @param parseStr
     * @param dateStr
     * @return
     */
    public static boolean testParse(String parseStr, String dateStr) {
        try {
            parse(parseStr, dateStr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /***
     * 根据指定格式将时间转化为字符串
     *
     * @param date
     * @param formatStr
     * @return
     */
    public static String format(String formatStr, Date date) {
        if(date==null) {
            return null;
        }
        DateFormat format = getFormat(formatStr);

        return format.format(date);
    }

    /**
     * String转换日期 格式"yyyyMMdd"
     *
     * @param str
     * @return
     * @throws ParseException
     */
    public static Date YMDparseLineStr(String str) {
        return parse(ymdGapless, str);
    }

    /**
     * String转换日期 格式"yyyyMMddHHmm"
     *
     * @param str
     * @return
     * @throws ParseException
     */
    public static Date YMDHMparseLineStr(String str) {
        return parse(ymdhmsfGapless, str);
    }

    /**
     * String转换日期 格式"yyyyMMddHHmmss"
     *
     * @param str
     * @return
     * @throws ParseException
     */
    public static Date YMDHMSparseLineStr(String str) {
        return parse(ymdhmsGapless, str);
    }

    /**
     * String转换日期 格式"yyyy-MM-dd"
     *
     * @param str
     * @return
     * @throws ParseException
     */
    public static Date YMDparseStr(String str) {
        return parse(ymd, str);
    }

    /**
     * String转换日期 格式"yyyy-MM-dd HH:mm"
     *
     * @param str
     * @return
     * @throws ParseException
     */
    public static Date YMDHMparseStr(String str) {
        return parse(ymdhm, str);
    }

    /**
     * String转换日期 格式"yyyy-MM-dd HH:mm:ss"
     *
     * @param str
     * @return
     * @throws ParseException
     */
    public static Date YMDHMSparseStr(String str) {
        return parse(ymdhms, str);
    }

    /**
     * String转换日期 格式"yyyy-MM"
     *
     * @param str
     * @return
     * @throws ParseException
     */
    public static Date YMparseStr(String str) {
        return parse(ym, str);
    }

    /**
     * String转换成 格式日期"yyyyMM"
     *
     * @param
     * @return
     */
    public static Date YMParseDate(String str) {
        return parse(ymGapless, str);
    }

    /**
     * String转换成 格式日期"yyyyMMdd"
     *
     * @param
     * @return
     */
    public static Date YMDParseDate(String str) {
        return parse(ymdGapless, str);
    }

    /**
     * String转换成 格式日期"yyyyMMdd"
     *
     * @param
     * @return
     */
    public static Date YMZhParseStr(String str) {
        return parse(ymz, str);
    }

    /**
     * 日期转换成String 格式"yyyy-MM-dd"
     *
     * @param date
     * @return
     */
    public static String YMDformatDate(Date date) {
        return format(ymd, date);
    }

    /**
     * 日期转换成String 格式"yyyy-MM-dd HH:mm:ss"
     *
     * @param date
     * @return
     */
    public static String YMDHMSformatDate(Date date) {
        return format(ymdhms, date);
    }

    /**
     * 日期转换成String 格式"yyyy-MM-dd HH:mm"
     *
     * @param date
     * @return
     */
    public static String YMDHMformatDate(Date date) {
        return format(ymdhm, date);
    }

    /**
     * 日期转换成String 格式"MM-dd HH:mm"
     *
     * @param date
     * @return
     */
    public static String HMformatDate(Date date) {
        return format(mdhm, date);
    }

    /**
     * 日期转换成String 格式"MM-dd HH"
     *
     * @param date
     * @return
     */
    public static String MDHformatDate(Date date) {
        return format(mdh, date);
    }

    /***
     * yyyy-MM
     *
     * @param
     * @return
     * @throws ParseException
     */
    public static String YMformatDate(Date date) {
        return format(ym, date);
    }

    /***
     * yyyyMM
     *
     * @param
     * @return
     * @throws ParseException
     */
    public static String YMformatDate2(Date date) {
        return format(ymGapless, date);
    }

    /**
     * 日期转换成String 格式"yyyy年MM月"
     *
     * @param date
     * @return
     */
    public static String YMZformatDate(Date date) {
        return format(ymz, date);
    }

    /**
     * 日期转换成String 格式"yyyy年MM月DD日"
     *
     * @param date
     * @return
     */
    public static String YMDZformatDate(Date date) {
        return format(ymdz, date);
    }

    /**
     * 日期转换成String 格式"MM月DD日"
     *
     * @param date
     * @return
     */
    public static String MDZformatDate(Date date) {
        return format(mdz, date);
    }

    /**
     * 日期转换成String 格式"yyyy年MM月DD日 HH时mm分"
     *
     * @param date
     * @return
     */
    public static String YMDHMZformatDate(Date date) {
        return format(ymdhmz, date);
    }

    /**
     * 日期转换成String 格式"yyyy年MM月DD日 HH时mm分ss秒"
     *
     * @param date
     * @return
     */
    public static String YMDHMZSformatDate(Date date) {
        return format(ymdhmzs, date);
    }

    /**
     * 日期转换成String 格式"MM月DD日 HH时mm分"
     *
     * @param date
     * @return
     */
    public static String MDHMZformatDate(Date date) {
        return format(mdhmz, date);
    }

    /**
     * 将未指定格式的日期字符串转化成java.util.Date类型日期对象 <br>
     *
     * @param date,待转换的日期字符串
     * @return
     */
    public static Date parseStringToDate(String date) {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        String parse = date;
        parse = parse.replaceFirst("^[0-9]{4}([^0-9])", "yyyy$1");
        parse = parse.replaceFirst("^[0-9]{2}([^0-9])", "yy$1");
        parse = parse.replaceFirst("([^0-9])[0-9]{1,2}( ?)", "$1MM$2");
        parse = parse.replaceFirst("([^0-9])[0-9]{1,2}( ?)", "$1dd$2");
        parse = parse.replaceFirst("( )[0-9]{1,2}([^0-9])", "$1HH$2");
        parse = parse.replaceFirst("([^0-9])[0-9]{1,2}([^0-9])", "$1mm$2");
        parse = parse.replaceFirst("([^0-9])[0-9]{1,2}([^0-9]?)", "$1ss$2");
        return parse(parse, date);

    }

    /**
     * 获得指定时间的月份
     *
     * @param time
     * @return
     */
    public static int getMonth(Date time) {
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        return c.get(Calendar.MONTH) + 1;
    }

    public static int getYear(Date time) {
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        return c.get(Calendar.YEAR);
    }

    /***
     * 获得月的最后一天
     *
     * @param time
     * @return
     */
    public static int getMonthLastDate(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        calendar.set(Calendar.DATE, 1);
        calendar.add(Calendar.DATE, -1);
        return calendar.get(Calendar.DATE);
    }

    /***
     * String转换日期格式"yyyy"
     *
     * @param str
     * @return
     * @throws ParseException
     */
    public static Date YParseStr(String str) {
        Calendar c = Calendar.getInstance();
        c.set(Integer.valueOf(str), 0, 1, 0, 0, 0);
        return c.getTime();
    }

    /***
     * 根据时间的天数进行偏移
     *
     * @param date
     *            指定时间
     * @param next
     *            需要偏移的天数
     * @return
     *
     */
    public static Date dateAfter(Date date, int next) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + next);
        return c.getTime();
    }

    /***
     * 求两个时间的天数差
     *
     * @param start
     *            时间时间
     * @param end
     *            结束时间
     * @return
     */
    public static int subtractDate(Date start, Date end) {
        long diff = end.getTime() - start.getTime();
        long dateNum = diff / (1000 * 24 * 60 * 60);// 相差天数
        return (int) dateNum;
    }

    // 获取当天的开始时间
    public static Date getDayBegin() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    // 获取当天的结束时间
    public static Date getDayEnd() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    /***
     * 时间转描述字符串
     *
     * @param date
     *            时间
     * @return
     */
    public static String dateToDescStr(Date date) {
        long thisTime = System.currentTimeMillis() / (1000 * 60);
        long dateTime = date.getTime() / (1000 * 60);
        /*if (thisTime - dateTime <= 5) {
            // 5分钟内为刚刚
            return "刚刚";
        } else*/
        if(thisTime-dateTime <=0) {
            return "1分钟前";
        }
        if (thisTime - dateTime < 60) {
            // 一个小时内直接显示时间
            return thisTime - dateTime + "分钟前";
        } else if ((thisTime - dateTime) < 60 * 24) {
            // 一天内
            return (thisTime - dateTime) / 60 + "小时前";
        }
        thisTime = thisTime / 60 / 24;
        dateTime = dateTime / 60 / 24;
        if (thisTime - dateTime == 1) {
            return "昨天";
        } else if (thisTime - dateTime == 2) {
            return "前天";
        }
        return YMDHMformatDate(date);
    }

    /***
     * 获得两个时间之间的时间
     *
     * @param beginDate
     *            开始时间
     * @param endDate
     *            结束时间
     * @return 返回时间集合
     *
     */
    public static List<Date> betweenDates(Date beginDate, Date endDate) {
        List<Date> result = new ArrayList<Date>();
        Date temp = beginDate;
        while (!temp.after(endDate)) {
            result.add(temp);
            temp = MallDateUtils.dateAfter(temp, 1);
        }
        return result;
    }

}
