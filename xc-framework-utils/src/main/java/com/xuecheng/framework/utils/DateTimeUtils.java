package com.xuecheng.framework.utils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;

import java.util.Date;
import java.util.TimeZone;

/**
 * date and time utils
 *
 * @author wzy
 *         Created by wzy on 12/3/2015.
 * @version 0.1
 * @since 0.1
 */
public class DateTimeUtils {

    private static final DateTimeZone DATE_TIME_ZONE = SystemConfig.getDateTimeZone();

    /**
     * 获取当前的日期和时间
     *
     * @return 当前的日期和时间
     */
    public static Date nowDate() {
        return DateTime.now(DATE_TIME_ZONE).toDate();
    }

    /**
     * 格式化日期
     * <p>使用 <em>yyyy-MM-dd HH:mm:ss</em> 格式进行格式化</p>
     *
     * @param date 需要格式化的日期
     * @return 格式化后的字符串
     * @see DateTimePattern#dt_24_horizontal
     * @see DateTimePattern
     */
    public static String format(Date date) {
        return format(date, DateTimePattern.dt_24_horizontal.toString());
    }

    /**
     * 格式化日期
     *
     * @param date    需要格式化的日期
     * @param pattern 日期格式
     * @return 格式化后的日期字符串
     * @see DateTimePattern
     */
    public static String format(Date date, String pattern) {
        return getDateTime(date).toString(pattern);
    }

    /**
     * 格式化日期
     * <p>使用制定的时区和格式进行格式化</p>
     *
     * @param date     日期
     * @param timeZone 时区
     * @param pattern  日期格式
     * @return 格式化后的日期字符串
     * @see DateTimePattern
     */
    public static String format(Date date, TimeZone timeZone, String pattern) {
        return new DateTime(date, DateTimeZone.forTimeZone(timeZone)).toString(pattern);
    }

    /**
     * 根据给定的毫秒获取日期
     *
     * @param mills 毫秒
     * @return 日期
     */
    public static Date getDate(long mills) {
        return new DateTime(mills, DATE_TIME_ZONE).toDate();
    }

    /**
     * 当前的毫秒
     *
     * @return 当前的毫秒
     */
    public static long currentTimeMillis() {
        return DateTime.now(DATE_TIME_ZONE).getMillis();
    }

    /**
     * 当前日期字符串
     * <p>使用 <em>yyyy-MM-dd HH:mm:ss</em> 格式进行格式化</p>
     *
     * @return 当前日期字符串
     * @see DateTimePattern
     */
    public static String nowDateString() {
        return nowDateString(DateTimePattern.dt_24_horizontal.toString());
    }

    /**
     * 根据制定格式获取当前日期字符串
     *
     * @param pattern 日期格式
     * @return 当前日期字符串
     * @see DateTimePattern
     */
    public static String nowDateString(String pattern) {
        return DateTime.now(DATE_TIME_ZONE).toString(pattern);
    }

    private static DateTime getDateTime(Date date) {
        return new DateTime(date, DATE_TIME_ZONE);
    }

    /**
     * 将给定日期加上<code>days</code>天
     * <p>days可以是正数也可以是负数</p>
     *
     * @param date 日期
     * @param days days
     * @return 新的日期
     */
    public static Date plusDays(Date date, int days) {
        return getDateTime(date).plusDays(days).toDate();
    }

    /**
     * 给定的日期加上<code>hours</code>小时
     * <p>hours可以是正数和负数</p>
     *
     * @param date  日期
     * @param hours hours
     * @return 新日期
     */
    public static Date plusHours(Date date, int hours) {
        return getDateTime(date).plusHours(hours).toDate();
    }

    /**
     * 给定的日期加上<code>months</code>月
     * <p>months可以是正数和负数</p>
     *
     * @param date  日期
     * @param months months
     * @return 新日期
     */
    public static Date plusMonths(Date date, int months) {
        return getDateTime(date).plusMonths(months).toDate();
    }


    /**
     * 给定的日期加上<code>weeks</code>星期
     * <p>weeks可以是正数和负数</p>
     *
     * @param date  日期
     * @param weeks weeks
     * @return 新日期
     */
    public static Date plusWeeks(Date date, int weeks) {
        return getDateTime(date).plusWeeks(weeks).toDate();
    }

    /**
     * 给定的日期加上<code>years</code>年
     * <p>years可以是正数和负数</p>
     *
     * @param date  日期
     * @param years years
     * @return 新日期
     */
    public static Date plusYears(Date date, int years) {
        return getDateTime(date).plusYears(years).toDate();
    }

    /**
     * 给定的日期加上<code>minutes</code>分钟
     * <p>minutes可以是正数和负数</p>
     *
     * @param date  日期
     * @param minutes minutes
     * @return 新日期
     */
    public static Date plusMinutes(Date date, int minutes) {
        return getDateTime(date).plusMinutes(minutes).toDate();
    }

    /**
     * 给定的日期加上<code>seconds</code>秒
     * <p>seconds可以是正数和负数</p>
     *
     * @param date  日期
     * @param seconds seconds
     * @return 新日期
     */
    public static Date plusSeconds(Date date, int seconds) {
        return getDateTime(date).plusSeconds(seconds).toDate();
    }

    /**
     * 给定的日期加上<code>millis</code>毫秒
     * <p>millis可以是正数和负数</p>
     *
     * @param date  日期
     * @param millis millis
     * @return 新日期
     */
    public static Date plusMillis(Date date, int millis) {
        return getDateTime(date).plusMillis(millis).toDate();
    }

    /**
     * 给定的日期加上<code>millis</code>毫秒
     * <p>millis可以是正数和负数，添加一段持续的时间</p>
     *
     * @param date  日期
     * @param millis millis
     * @return 新日期
     */
    public static Date plus(Date date, long millis) {
        return getDateTime(date).plus(millis).toDate();
    }

    /**
     * 给定日期是否在当前日期后面
     *
     * @param date 给定日期
     * @return boolean
     */
    public static boolean isAfterNow(Date date) {
        return getDateTime(date).isAfterNow();
    }

    /**
     * 给定日期是否在当前日期前面
     *
     * @param date 给定日期
     * @return boolean
     */
    public static boolean isBeforeNow(Date date) {
        return getDateTime(date).isBeforeNow();
    }

    /**
     * 获取给定日期的在某一个月中的天位置
     *
     * @param date 给定日期
     * @return 天位置
     */
    public static int getDayOfMonth(Date date) {
        return getDateTime(date).getDayOfMonth();
    }

    /**
     * 获取给定日期在给定的日期年中的天位置
     *
     * @param date 给定日期
     * @return 天位置
     */
    public static int getDayOfYear(Date date) {
        return getDateTime(date).getDayOfYear();
    }

    /**
     * 获取给定日期在给定日期的星期中的天位置
     *
     * @param date 给定日期
     * @return 天位置
     */
    public static int getDayOfWeek(Date date) {
        return getDateTime(date).getDayOfWeek();
    }

    /**
     * 获取给定日期的小时在给定日期的天中的位置
     *
     * @param date 给定日期
     * @return 位置
     */
    public static int getHourOfDay(Date date) {
        return getDateTime(date).getHourOfDay();
    }

    /**
     * 获取给定日期的分钟在给定日期的小时中的位置
     *
     * @param date 给定日期
     * @return 位置
     */
    public static int getMinuteOfHour(Date date) {
        return getDateTime(date).getMinuteOfHour();
    }

    /**
     * 获取给定日期的分钟在给定日期的天中的位置
     *
     * @param date 给定日期
     * @return 位置
     */
    public static int getMinuteOfDay(Date date) {
        return getDateTime(date).getMinuteOfDay();
    }

    /**
     * 获取给定日期的年
     *
     * @param date 给定日期
     * @return 年
     */
    public static int getYear(Date date) {
        return getDateTime(date).getYear();
    }

    /**
     * 转换字符串格式的日期为日期类型
     *
     * <p>使用 <em>yyyy-MM-dd HH:mm:ss</em> 格式进行解析</p>
     *
     * @param dateStr 日期字符串
     * @return 日期
     */
    public static Date parse(String dateStr) {
        return parse(dateStr, DateTimePattern.dt_24_horizontal.toString());
    }

    /**
     * 使用指定格式转换字符串为日期类型
     *
     * @param dateStr 日期字符串
     * @param pattern 日期格式
     * @return 日期
     */
    public static Date parse(String dateStr, String pattern) {
        return DateTime.parse(dateStr, DateTimeFormat.forPattern(pattern)).withZone(DATE_TIME_ZONE).toDate();
    }

    /**
     * 日期比较
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return int
     */
    public static int compare(Date date1, Date date2) {
        return getDateTime(date1).compareTo(getDateTime(date2));
    }

    /**
     * 取得指定时间的最开始时间(00:00:00)
     * @param date 指定时间
     * @return Date
     */
    public static Date getInitStartTime(Date date) {
        return withDateTime(date, 0, 0, 0);
    }

    /**
     * 取得指定时间的最晚时间(23:59:59)
     * @param date 指定时间
     * @return Date
     */
    public static Date getInitEndTime(Date date) {
        return withDateTime(date, 23, 59, 59);
    }

    public static Date withDateTime(Date date, int hours, int minus, int seconds) {
        return withDateTime(date, hours, minus, seconds, 0);
    }

    public static Date withDateTime(Date date, int hours, int minus, int seconds, int mills) {
        DateTime dateTime = new DateTime(date);
        if (hours >= 0) {
            dateTime = dateTime.withHourOfDay(hours);
        }

        if (minus >= 0) {
            dateTime = dateTime.withMinuteOfHour(minus);
        }

        if (seconds >= 0) {
            dateTime = dateTime.withSecondOfMinute(seconds);
        }

        if (seconds >= 0) {
            dateTime = dateTime.withMillisOfSecond(mills);
        }

        return dateTime.toDate();
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDaysByMillisecond(Date date1,Date date2)
    {
        int days = (int) ((date1.getTime() - date2.getTime()) / (1000*3600*24));
        return days;
    }

}
