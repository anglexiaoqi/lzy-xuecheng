package com.xuecheng.framework.utils;

import com.xuecheng.framework.utils.validator.EmailValidator;
import com.xuecheng.framework.utils.validator.UrlValidator;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * check utils
 *
 * <p>参数检查的工具类</p>
 *
 * @since 0.1
 * @author wzy
 *
 * Created by wzy on 12/3/2015.
 */
public class CheckUtils {

    /**
     * 判断给定值是否为null
     *
     * @param object value
     * @return boolean
     */
    public static boolean isNull(Object object) {
        return object == null;
    }

    /**
     * 判断给定值是否非null
     *
     * @param object value
     * @return boolean
     */
    public static boolean isNotNull(Object object) {
        return !isNull(object);
    }

    /**
     * 判断是否非空
     *
     * <p>非空判断包含了null判断，当为null的时候返回false</p>
     *
     * @param str value
     * @return boolean
     */
    public static boolean hasLength(final CharSequence str) {
        return (str != null && str.length() > 0);
    }

    /**
     * 判断是否非空
     *
     * <p>非空判断包含了null判断，当为null的时候返回false</p>
     *
     * @param str value
     * @return boolean
     */
    public static boolean hasLength(final String str) {
        return hasLength((CharSequence) str);
    }

    /**
     * 判断是否空
     *
     * <p>非空判断包含了null判断，当为null的时候返回true</p>
     *
     * @param str value
     * @return boolean
     */
    public static boolean isEmpty(final CharSequence str) {
        return isNull(str) || !hasLength(str);
    }

    /**
     * 是否空
     *
     * <p>包含了null判断</p>
     *
     * @param collection 集合
     * @param <E> 泛型
     * @return boolean
     */
    public static <E> boolean isEmpty(final Collection<E> collection) {
        return isNull(collection) || collection.isEmpty();
    }

    /**
     * 是否空
     *
     * <p>包含了null判断</p>
     *
     * @param map map
     * @param <E> 泛型
     * @param <K> 泛型
     * @return boolean
     */
    public static <K, E> boolean isEmpty(final Map<K, E> map) {
        return isNull(map) || map.isEmpty();
    }

    /**
     * 是否为空
     *
     * 包含了空数组判断
     *
     * @param ts 数组
     * @param <T> T
     * @return boolean
     */
    public static <T> boolean isEmpty(final T[] ts) {
        return isNull(ts) || ts.length == 0;
    }

    /**
     * 是空
     *
     * <p>包含了null判断</p>
     *
     * @param str value
     * @return boolean
     */
    public static boolean isNotEmpty(final CharSequence str) {
        return !isEmpty(str);
    }

    /**
     * 是空
     *
     * <p>包含了null判断</p>
     *
     * @param <E> 泛型
     * @param collection value
     * @return boolean
     */
    public static <E> boolean isNotEmpty(final Collection<E> collection) {
        return !isEmpty(collection);
    }

    /**
     * 是空
     *
     * <p>包含了null判断</p>
     *
     * @param <K> 泛型
     * @param <E> 泛型
     * @param map value
     * @return boolean
     */
    public static <K, E> boolean isNotEmpty(final Map<K, E> map) {
        return !isEmpty(map);
    }

    /**
     * 是否不为空
     *
     * 包含了空数组判断
     *
     * @param ts 数组
     * @param <T> T
     * @return boolean
     */
    public static <T> boolean isNotEmpty(final T[] ts) {
        return !isEmpty(ts);
    }

    /**
     * 是邮件
     *
     * <p>邮件格式判断</p>
     *
     * @param email value
     * @return boolean
     */
    public static boolean isEmail(final String email) {
        return isNotEmpty(email) && EmailValidator.getInstance().isValid(email);
    }

    /**
     * 是否合法的url地址
     *
     * @param url url
     * @return boolean
     */
    public static boolean isUrl(final String url) {
        return isNotEmpty(url) && UrlValidator.getInstance().isValid(url);
    }

    /**
     * 是否为空或者包含空白符
     *
     * <p>当为空白符时候，返回true</p>
     *
     * @param str value
     * @return boolean
     */
    public static boolean isBlank(final CharSequence str) {
        if (isEmpty(str)) {
            return true;
        }

        for (int i = 0, strLen = str.length(); i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 字符串是否全部是数字
     *
     * @param value value
     * @return value
     */
    public static boolean isNumberic(final String value) {
        if (isEmpty(value)) {
            return false;
        }

        char[] tmpArrays = value.toCharArray();
        boolean result = true;

        for (char c : tmpArrays) {
            if (!Character.isDigit(c)) {
                result = false;
                break;
            }
        }

        return result;
    }

    /**
     * 字符串是否全部是字母
     *
     * @param value value
     * @return boolean
     */
    public static boolean isAlphabetic(final String value) {
        if (value == null) {
            return false;
        }

        char[] tmpArrays = value.toCharArray();
        boolean result = true;

        for (char c : tmpArrays) {
            if (!Character.isAlphabetic(c)) {
                result = false;
                break;
            }
        }

        return result;
    }

    /**
     * 判断当前值是否为数字(包括小数)
     *
     * @param value
     *            要判断的对象
     * @return 是否为数字
     */
    public static boolean isDigit(String value) {
        if (CheckUtils.isEmpty(value)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[0-9]+(.[0-9]{1,2})?$");
        return pattern.matcher(value).matches();
    }

}
