package com.xuecheng.framework.utils;

/**
 * date and time pattern
 *
 * @author wzy
 *         Created by wzy on 12/3/2015.
 * @version 0.1
 * @since 0.1
 */
public enum DateTimePattern {
    dt_24_horizontal("yyyy-MM-dd HH:mm:ss"),
    dt_12_horizontal("yyyy-MM-dd hh:mm:ss"),
    dt_24_slashes ("yyyy/MM/dd HH:mm:ss"),
    dt_12_slashes ("yyyy/MM/dd hh:mm:ss"),
    dtm_24_horizontal("yyyy-MM-dd HH:mm:ss.SSSS"),
    dtm_12_horizontal("yyyy-MM-dd hh:mm:ss.SSSS"),
    dtm_24_slashes ("yyyy/MM/dd HH:mm:ss.SSSS"),
    dtm_12_slashes ("yyyy/MM/dd hh:mm:ss.SSSS"),
    date_slashes("yyyy-MM-dd"),
    date_horizontal("yyyy/MM/dd"),
    time_24 ("HH:mm:ss"),
    time_12 ("hh:mm:ss")
    ;

    private String pattern;

    DateTimePattern(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String toString() {
        return this.pattern;
    }
}
