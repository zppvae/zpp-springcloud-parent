package org.zpp.gateway.filter;

import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 请求时间过滤器
 *
 * @author zpp
 */
@Component
@Slf4j
public class RequestTimeFilter implements GlobalFilter, Ordered {

    private static final String START_TIME = "startTime";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        exchange.getAttributes().put(START_TIME, System.currentTimeMillis());

        log.info("[请求方式] - [{}]",serverHttpRequest.getMethod().name());
        log.info("[请求host] - [{}]",serverHttpRequest.getURI().getHost());
        log.info("[请求path] - [{}]",serverHttpRequest.getURI().getPath());

        if ("GET".equals(serverHttpRequest.getMethodValue())) {
            log.info("[请求参数] - [{}]",serverHttpRequest.getQueryParams());
        }
        return chain.filter(exchange).then(
                Mono.fromRunnable(() -> {
                    Long startTime = exchange.getAttribute(START_TIME);
                    log.info("[消耗时间] - [{}]",(System.currentTimeMillis() - startTime) + "ms");
                })
        );

    }

    /**
     * 设置优先级
     *
     * 值越大则优先级越低
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}