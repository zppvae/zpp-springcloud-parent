package org.zpp.common.exception;

import org.zpp.common.util.Result;

/**
 * @author zpp
 * @date 2018/12/6 18:25
 */
public class ServiceException extends RuntimeException {

    private Result result;

    public ServiceException(){}

    public ServiceException(Result result) {
        this.result = result;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
