package com.xuecheng.framework.utils;

import org.joda.time.DateTimeZone;

/**
 * 系统配置
 *
 * @author wzy
 *         Created by wzy on 12/3/2015.
 * @version 0.1
 * @since 0.1
 */
public class SystemConfig {

    public static DateTimeZone getDateTimeZone() {
        return DateTimeZone.getDefault();
    }
}
