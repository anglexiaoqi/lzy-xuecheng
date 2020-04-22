package com.xuecheng.framework.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class RouteResult<T> implements Serializable {

    public static <T> RouteResult<T> built() {
        return new RouteResult<>();
    }

    private int code;

    private String msg;

    private Long count;

    private T data;

    public RouteResult<T> success() {
        this.code = Constants.SUCCESS;
        return this;
    }

    public RouteResult<T> success(T data) {
        success();
        this.data = data;
        return this;
    }

    public RouteResult<T> success(T data, Object count) {
        addResult("data", data);
        addResult("count", count);
        success();
        return this;
    }

    public RouteResult<T> failure(String message) {
        this.code = Constants.FAILURE;
        this.msg = message;
        return this;
    }

    public RouteResult<T> failure(T data, String message) {
        this.data = data;
        this.failure(message);
        return this;
    }

    public RouteResult<T> error() {
        return error(null);
    }

    public RouteResult<T> error(String message) {
        this.code = Constants.ERROR;
        this.msg = message;
        return this;
    }

    public RouteResult<T> addResult(String key, Object value) {
        Map<String, Object> map;
        if (this.data == null) {
            map = new HashMap<>();
            this.data = (T) map;
        }

        if (this.data instanceof Map) {
            map = Map.class.cast(this.data);
        } else {
            throw new RuntimeException("value of resultData is not map, can't add result to "
                    + "value");
        }

        map.put(key, value);
        return this;
    }

    @JsonIgnore
    public boolean isFailure() {
        return this.code == Constants.FAILURE;
    }

    @JsonIgnore
    public boolean isError() {
        return this.code == Constants.ERROR;
    }

    @JsonIgnore
    public boolean isOk() {
        return this.code == Constants.SUCCESS;
    }

}
