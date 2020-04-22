package com.xuecheng.framework.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 结果返回的基类
 *
 * Created by wzy on 2017/3/27.
 */
@Getter
@ToString
public abstract class BaseResult implements IBean, Serializable {

    private static final long serialVersionUID = -8708787622875136682L;

    private int status;

    private String message;

    public BaseResult success() {
        this.status = Constants.SUCCESS;
        return this;
    }

    public BaseResult failure(String message) {
        this.status = Constants.FAILURE;
        this.message = message;
        return this;
    }

    public BaseResult error() {
        return error(null);
    }

    public BaseResult error(String message) {
        this.status = Constants.ERROR;
        this.message = message;
        return this;
    }

    @JsonIgnore
    public boolean isFailure() {
        return this.status == Constants.FAILURE;
    }

    @JsonIgnore
    public boolean isError() {
        return this.status == Constants.ERROR;
    }

    @JsonIgnore
    public boolean isOk() {
        return this.status == Constants.SUCCESS;
    }

}
