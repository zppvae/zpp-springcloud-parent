package org.zpp.common.util;

import org.zpp.common.enums.result.ResultCodeable;

import java.io.Serializable;

/**
 *
 * 请求响应体
 * @author zpp
 * @date 2018/12/6 17:44
 */
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int SUCCESS = 200;

    public static final int FAIL = 500;

    private String msg = "success";

    private int code = SUCCESS;

    private T data;

    public Result() {
        super();
    }

    public Result(T data) {
        super();
        this.data = data;
    }

    public Result(ResultCodeable codeable) {
        super();
        this.code = codeable.getCode();
        this.msg = codeable.getMsg();
    }

    public Result(T data, String msg) {
        super();
        this.data = data;
        this.msg = msg;
    }

    public Result(Throwable e) {
        super();
        this.msg = e.getMessage();
        this.code = FAIL;
    }

    public Result(int code,String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
