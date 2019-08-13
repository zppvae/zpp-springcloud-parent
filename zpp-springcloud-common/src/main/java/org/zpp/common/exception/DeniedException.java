package org.zpp.common.exception;

/**
 * @author zpp
 * @date 2018/12/6 18:25
 */
public class DeniedException extends RuntimeException {

    public DeniedException(){}

    public DeniedException(String message) {
        super(message);
    }
}
