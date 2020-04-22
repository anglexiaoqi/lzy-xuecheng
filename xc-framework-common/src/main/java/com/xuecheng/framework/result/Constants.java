package com.xuecheng.framework.result;

import java.util.concurrent.TimeUnit;

/**
 * 全局常量类
 *
 * @author wzy
 * @version 1.0
 * @since 1.0
 * Created by wzy on 2016/3/22.
 */
public interface Constants {

    /**
     * -1
     */
    int INT_MINUS_ONE = -1;

    /**
     * 0
     */
    int INT_ZERO = 0;

    /**
     * 1
     */
    int INT_ONE = 1;

    /**
     * 2
     */
    int INT_TWO = 2;

    /**
     * 3
     */
    int INT_THREE = 3;

    /**
     * 4
     */
    int INT_FOUR = 4;

    /**
     * 5
     */
    int INT_FIVE = 5;

    /**
     * 6
     */
    int INT_SIX = 6;

    /**
     * 7
     */
    int INT_SEVEN = 7;

    /**
     * 8
     */
    int INT_EIGHT = 8;

    /**
     * 9
     */
    int INT_NINE = 9;

    /**
     * -1
     */
    long LONG_MINUS_ONE = -1L;

    /**
     * 0
     */
    long LONG_ZERO = 0L;

    /**
     * 1
     */
    long LONG_ONE = 1L;

    /**
     * 2
     */
    long LONG_TWO = 2L;

    /**
     * 3
     */
    long LONG_THREE = 3L;

    /**
     * 4
     */
    long LONG_FOUR = 4L;

    /**
     * 5
     */
    long LONG_FIVE = 5L;

    /**
     * 6
     */
    long LONG_SIX = 6L;

    /**
     * 7
     */
    long LONG_SEVEN = 7L;

    /**
     * 8
     */
    long LONG_EIGHT = 8L;

    /**
     * 9
     */
    long LONG_NINE = 9L;

    /**
     * 字符串0
     */
    String STR_ZERO = "0";

    /**
     * 字符串1
     */
    String STR_ONE = "1";

    /**
     * 字符串2
     */
    String STR_TWO = "2";

    //标点符号
    /**
     * 逗号
     */
    String COMMA = ",";

    /**
     * 点号，句号(英文)
     */
    String DOTS = ".";

    /**
     * 分号
     */
    String SEMICOLON = ";";

    /**
     * #号
     */
    String NUMBER_SIGN = "#";

    /**
     * 美元符号
     */
    String DOLLAR = "$";

    /**
     * 人民币符号
     */
    String RMB = "￥";

    /**
     * 星号
     */
    String STAR = "*";

    /**
     * %号
     */
    String PERCENT = "%";

    /**
     * 下划线
     */
    String UNDERLINE = "_";


    /**
     * 冒号
     */
    String COLON = ":";

    /**
     * 空字符串
     */
    String EMPTY_STR = "";

    /**
     * 图片域名占位符
     */
    String REPLACE_IMG_DOMAIN = "#{imgDomain}";

    int SUCCESS = INT_ZERO;

    int FAILURE = INT_ONE;

    int ERROR = INT_NINE;

    byte[] EMPTY_BYTE_ARRAY = new byte[0];

    /**
     * 分隔符@
     */
    String split_AT = "@@@";

    String KEY_A = "a";

    String KEY_B = "b";

    String KEY_C = "c";

    /**
     * 默认的统计时间
     */
    int defaultInrnal = 1;

    TimeUnit defaultSpeedTimeUnit = TimeUnit.MINUTES;

}
