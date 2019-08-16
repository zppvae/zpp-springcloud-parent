package org.zpp.api.feign.fallback.factory;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import org.zpp.api.feign.APIUserService;
import org.zpp.api.feign.fallback.APIUserServiceFallback;

/**
 * @author zpp
 * @date 2019/8/16 16:33
 */
@Component
public class APIUserServiceFallbackFactory implements FallbackFactory<APIUserService> {

    @Override
    public APIUserService create(Throwable cause) {
        APIUserServiceFallback fallback = new APIUserServiceFallback();
        fallback.setCause(cause);
        return fallback;
    }
}
