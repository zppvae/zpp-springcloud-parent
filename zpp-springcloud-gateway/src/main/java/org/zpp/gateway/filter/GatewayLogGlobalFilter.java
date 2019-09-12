package org.zpp.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.zpp.gateway.decorator.RecorderServerHttpRequestDecorator;
import org.zpp.gateway.decorator.RecorderServerHttpResponseDecorator;
import org.zpp.gateway.util.GatewayLogUtil;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * 网关日志全局过滤器
 *
 * @author zpp
 */
@Slf4j
@Component
public class GatewayLogGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest originalRequest = exchange.getRequest();
        URI originalRequestUrl = originalRequest.getURI();

        String scheme = originalRequestUrl.getScheme();
        if ((!"http".equals(scheme) && !"https".equals(scheme))) {
            return chain.filter(exchange);
        }

        String upgrade = originalRequest.getHeaders().getUpgrade();
        if ("websocket".equalsIgnoreCase(upgrade)) {
            return chain.filter(exchange);
        }

        // 在 GatewayFilter 之前执行
        RecorderServerHttpRequestDecorator request = new RecorderServerHttpRequestDecorator(exchange.getRequest());

        //发送回客户端的 response
        RecorderServerHttpResponseDecorator response = new RecorderServerHttpResponseDecorator(exchange.getResponse());

        ServerWebExchange ex = exchange.mutate()
                .request(request)
                .response(response)
                .build();

        return GatewayLogUtil.recorderOriginalRequest(ex)
                .then(Mono.defer(() -> chain.filter(ex)))
                .then(Mono.defer(() -> GatewayLogUtil.recorderResponse(ex)));
    }

    @Override
    public int getOrder() {
        //在GatewayFilter之前执行
        return Integer.MIN_VALUE;
    }

}