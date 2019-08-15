package org.zpp.common.core.exception;

import lombok.Getter;
import lombok.Setter;
import org.zpp.common.core.util.R;

/**
 * @author zpp
 * @date 2018/12/6 18:25
 */

public class ServiceException extends RuntimeException {

    @Getter
    @Setter
    private R r;

    public ServiceException(String msg){
        super(msg);
    }

    public ServiceException(R r) {
        super();
        this.r = r;
    }
}
