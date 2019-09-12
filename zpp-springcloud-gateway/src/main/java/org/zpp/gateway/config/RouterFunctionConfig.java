package org.zpp.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.zpp.gateway.common.GatewayConstant;
import org.zpp.gateway.handler.HystrixFallbackHandler;
import org.zpp.gateway.handler.ImageCodeHandler;
import org.zpp.gateway.handler.SmsCodeHandler;

/**
 * 全局路由配置
 *
 * @author zpp
 * @date 2019/8/16 17:27
 */
@Configuration
public class RouterFunctionConfig {

    @Autowired
    private HystrixFallbackHandler hystrixFallbackHandler;

    @Autowired
    private ImageCodeHandler imageCodeHandler;

    @Autowired
    private SmsCodeHandler smsCodeHandler;

    @Bean
    public RouterFunction routerFunction() {
        return RouterFunctions.route(RequestPredicates.path(GatewayConstant.FALLBACK_URL)
                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), hystrixFallbackHandler)
                .andRoute(RequestPredicates.path(GatewayConstant.IMAGE_CODE_URL)
                    .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),imageCodeHandler)
                .andRoute(RequestPredicates.path(GatewayConstant.SMS_CODE_URL)
                        .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),smsCodeHandler);

    }
}
