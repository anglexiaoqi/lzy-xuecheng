package com.xuecheng.framework.convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import static com.xuecheng.framework.convert.TypeConvert.getConvertBean;

/**
 * 抽象的转换类
 *
 * <p>
 *     封装了基本所有的类型转换的工具类
 * </p>
 *
 * @author wzy
 *         Created by wzy on 12/14/2015.
 * @version 0.1
 * @since 0.1
 */
public abstract class AbstractConvert {

    protected Boolean toBoolean(Object value) {
        return getConvertBean().toBoolean(value);
    }

    protected Boolean toBoolean(Object value, Boolean defaultValue) {
        return getConvertBean().toBoolean(value, defaultValue);
    }

    protected boolean toBooleanValue(Object value) {
        return getConvertBean().toBooleanValue(value);
    }

    protected boolean toBooleanValue(Object value, boolean defaultValue) {
        return getConvertBean().toBooleanValue(value, defaultValue);
    }

    protected Integer toInteger(Object object) {
        return getConvertBean().toInteger(object);
    }

    protected Integer toInteger(Object object, Integer defaultValue) {
        return getConvertBean().toInteger(object, defaultValue);
    }

    protected int toIntValue(Object object) {
        return getConvertBean().toIntValue(object);
    }

    protected int toIntValue(Object object, int defaultValue) {
        return getConvertBean().toIntValue(object, defaultValue);
    }

    protected Long toLong(Object object) {
        return getConvertBean().toLong(object);
    }

    protected Long toLong(Object object, Long defaultValue) {
        return getConvertBean().toLong(object, defaultValue);
    }

    protected long toLongValue(Object object) {
        return getConvertBean().toLongValue(object);
    }

    protected long toLongValue(Object object, long defaultValue) {
        return getConvertBean().toLongValue(object, defaultValue);
    }

    protected Float toFloat(Object object) {
        return getConvertBean().toFloat(object);
    }

    protected Float toFloat(Object object, Float defaultValue) {
        return getConvertBean().toFloat(object, defaultValue);
    }

    protected Double toFloat(Object object, Double defaultValue) {
        return getConvertBean().toDouble(object, defaultValue);
    }

    protected float toFloatValue(Object object) {
        return getConvertBean().toFloatValue(object);
    }

    protected float toFloatValue(Object object, float defaultValue) {
        return getConvertBean().toFloatValue(object, defaultValue);
    }

    protected Double toDouble(Object object) {
        return getConvertBean().toDouble(object);
    }

    protected double toDoubleValue(Object object) {
        return getConvertBean().toDoubleValue(object);
    }

    protected double toDoubleValue(Object object, double defaultValue) {
        return getConvertBean().toDoubleValue(object, defaultValue);
    }

    protected Short toShort(Object object) {
        return getConvertBean().toShort(object);
    }

    protected Short toShort(Object object, Short defaultValue) {
        return getConvertBean().toShort(object, defaultValue);
    }

    protected short toShortValue(Object object) {
        return getConvertBean().toShortValue(object);
    }

    protected short toShortValue(Object object, short defaultValue) {
        return getConvertBean().toShortValue(object, defaultValue);
    }

    protected Character toCharacter(Object value) {
        return getConvertBean().toCharacter(value);
    }

    protected Character toCharacter(Object value, Character defaultValue) {
        return getConvertBean().toCharacter(value, defaultValue);
    }

    protected char toCharValue(Object value) {
        return getConvertBean().toCharValue(value);
    }

    protected char toCharValue(Object value, char defaultValue) {
        return getConvertBean().toCharValue(value, defaultValue);
    }

    protected Byte toByte(Object object) {
        return getConvertBean().toByte(object);
    }

    protected Byte toByte(Object object, Byte defaultValue) {
        return getConvertBean().toByte(object, defaultValue);
    }

    protected byte toByteValue(Object object) {
        return getConvertBean().toByteValue(object);
    }

    protected byte toByteValue(Object object, byte defaultValue) {
        return getConvertBean().toByteValue(object, defaultValue);
    }

    protected boolean[] toBooleanArray(Object value) {
        return getConvertBean().toBooleanArray(value);
    }

    protected int[] toIntegerArray(Object value) {
        return getConvertBean().toIntegerArray(value);
    }

    protected long[] toLongArray(Object value) {
        return getConvertBean().toLongArray(value);
    }

    protected float[] toFloatArray(Object value) {
        return getConvertBean().toFloatArray(value);
    }

    protected double[] toDoubleArray(Object value) {
        return getConvertBean().toDoubleArray(value);
    }

    protected short[] toShortArray(Object value) {
        return getConvertBean().toShortArray(value);
    }

    protected char[] toCharacterArray(Object value) {
        return getConvertBean().toCharacterArray(value);
    }

    protected String toString(Object value) {
        return getConvertBean().toString(value);
    }

    protected String toString(Object value, String defaultValue) {
        return getConvertBean().toString(value, defaultValue);
    }

    public String[] toStringArray(Object value) {
        return getConvertBean().toStringArray(value);
    }

    public Class toClass(Object value) {
        return getConvertBean().toClass(value);
    }

    public Class[] toClassArray(Object value) {
        return getConvertBean().toClassArray(value);
    }

    public BigInteger toBigInteger(Object value) {
        return getConvertBean().toBigInteger(value);
    }

    public BigInteger toBigInteger(Object value, BigInteger defaultValue) {
        return getConvertBean().toBigInteger(value, defaultValue);
    }

    public BigDecimal toBigDecimal(Object value) {
        return getConvertBean().toBigDecimal(value);
    }

    public BigDecimal toBigDecimal(Object value, BigDecimal defaultValue) {
        return getConvertBean().toBigDecimal(value);
    }
}
