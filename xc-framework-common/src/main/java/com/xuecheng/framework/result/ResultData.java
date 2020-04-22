package com.xuecheng.framework.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * result data
 * <p>
 * Created by wzy on 2016/12/21.
 */
@Getter
@ToString(callSuper = true)
public class ResultData<T> extends BaseResult {
    private static final long serialVersionUID = -92759394739288574L;

    public static <T> ResultData<T> built() {
        return new ResultData<>();
    }

    private T value;

    @Override
    public ResultData<T> success() {
        super.success();
        return this;
    }

    public ResultData<T> success(T value) {
        success();
        this.value = value;
        return this;
    }

    /**
     * use for page
     *
     * @param value      data
     * @param totalCount count
     * @return ResultData
     */
    public ResultData<T> success(T value, int totalCount) {
        addResult("data", value);
        addResult("totalCount", totalCount);
        success();
        return this;
    }

    @Override
    public ResultData<T> failure(String message) {
        super.failure(message);
        return this;
    }

    public ResultData<T> failure(T value, String message) {
        this.value = value;
        this.failure(message);
        return this;
    }

    @Override
    public ResultData<T> error() {
        super.error();
        return this;
    }

    @JsonIgnore
    public boolean isNull() {
        return this.value == null;
    }

    @JsonIgnore
    public boolean isEmpty() {
        if (isNull()) {
            return true;
        }

        if (this.value instanceof Number) {
            return false;
        } else if (this.value instanceof String) {
            return ((String) this.value).length() == 0;
        } else if (this.value instanceof List) {
            return ((List) this.value).isEmpty();
        } else if (this.value instanceof Map) {
            return ((Map) this.value).isEmpty();
        } else if (this.value instanceof BaseBean) {
            return true;
        } else if (this.value instanceof Object[]) {
            return ((Object[]) this.value).length == 0;
        } else {
            throw new RuntimeException("未知数据类型");
        }
    }

    @SuppressWarnings("unchecked")
    public ResultData<T> addResult(String key, Object value) {
        Map<String, Object> map;
        if (this.value == null) {
            map = new HashMap<>();
            this.value = (T) map;
        }

        if (this.value instanceof Map) {
            map = Map.class.cast(this.value);
        } else {
            throw new RuntimeException("value of resultData is not map, can't add result to "
                    + "value");
        }

        map.put(key, value);
        return this;
    }

    /**
     * 检查得到结果
     *
     * @return 结果
     * @throws Exception 异常
     */
    public T checkGetValue() throws Exception {
        if (!this.isOk()) {
            throw new Exception(this.getMessage());
        }
        return this.getValue();
    }
}
