package com.xuecheng.framework.result;

import com.xuecheng.framework.convert.AbstractConvert;
import jodd.bean.BeanCopy;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 抽象基础类
 *
 * <p>
 *     全局通用的抽象基础类，封装了分页属性,和通用的字段属性:如createTime和updateTime
 * </p>
 *
 * @author wzy
 * @version 0.1
 * @since 0.1
 *
 * Created by wzy on 11/25/2015.
 */
@ToString(doNotUseGetters = true)
@Setter
@Getter
public abstract class BaseBean extends AbstractConvert implements IBean,Serializable {

    private static final long serialVersionUID = 8163380293500332837L;

    /**
     * 将po转换为map
     *
     * @return map
     */
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        toMap(map);
        return map;
    }

    private void toMap(Map<String, ?> map) {
        BeanCopy.from(this).toMap(map).copy();
    }

    /**
     * 将po转换为map
     *
     * @return map
     */
    public Map<String, String> toMapString() {
        Map<String, String> map = new HashMap<>();
        toMap(map);
        return map;
    }

    /**
     * 将map转换为po
     *
     * @param map map
     * @return baseBean
     */
    public BaseBean fromMap(Map<String, ?> map) {
        BeanCopy.fromMap(map).toBean(this).copy();
        return this;
    }
}
