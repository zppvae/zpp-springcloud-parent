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
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static org.zpp.gateway.common.GatewayConstant.CACHE_REQUEST_BODY_OBJECT_KEY;

/**
 * 请求时间过滤器
 *
 * @author zpp
 */
@Component
@Slf4j
public class RequestTimeFilter implements GlobalFilter, Ordered {

    private static final String START_TIME = "startTime";
    private static final String REQ_ID = "reqId";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        Map<String,Object> attrMap = exchange.getAttributes();
        attrMap.put(START_TIME, System.currentTimeMillis());

        String reqId = UUID.randomUUID().toString();
        attrMap.put(REQ_ID,reqId);

        log.info("[{}] - [请求path] - [{}]",reqId,serverHttpRequest.getURI().getPath());
        log.info("[{}] - [请求方式] - [{}]",reqId,serverHttpRequest.getMethod().name());
        log.info("[{}] - [请求host] - [{}]",reqId,serverHttpRequest.getURI().getHost());
        log.info("[{}] - [请求参数] - [{}]",reqId,attrMap.get(CACHE_REQUEST_BODY_OBJECT_KEY));

        return chain.filter(exchange).then(
                Mono.fromRunnable(() -> {
                    Long startTime = exchange.getAttribute(START_TIME);
                    log.info("[{}] - [消耗时间] - [{}]",exchange.getAttribute(REQ_ID),(System.currentTimeMillis() - startTime) + "ms");
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