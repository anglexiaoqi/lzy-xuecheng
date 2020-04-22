package com.xuecheng.framework.convert;

import jodd.typeconverter.ConvertBean;
import jodd.typeconverter.TypeConverterManager;
import jodd.typeconverter.TypeConverterManagerBean;

/**
 * 类型转换
 *
 * Created by wzy on 11/26/2015.
 */
public class TypeConvert {

    /**
     * 获取转换类
     *
     * @return 转换类
     */
    public static ConvertBean getConvertBean() {
        return getTypeConverterManager().getConvertBean();
    }

    public static TypeConverterManagerBean getTypeConverterManager() {
        return TypeConverterManager.getDefaultTypeConverterManager();
    }
}
