package org.zpp.gateway.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.web.server.ServerWebExchange;
import org.zpp.gateway.decorator.RecorderServerHttpResponseDecorator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;
import static org.zpp.gateway.common.GatewayConstant.CACHE_REQUEST_BODY_OBJECT_KEY;

/**
 * 网关日志工具
 *
 * @author zpp
 */
@Slf4j
public class GatewayLogUtil {
    /**
     * 请求开始时间标识
     */
    private final static String START_TIME = "start_time";

    /**
     * 请求标识
     */
    private final static String REQUEST_ID = "request_id";

    private static boolean hasBody(HttpMethod method) {
        if (method == HttpMethod.POST || method == HttpMethod.PUT
                || method == HttpMethod.PATCH) {
            return true;
        }
        return false;
    }

    private static Mono<Void> doRecordBody(ServerWebExchange exchange,
                                           Flux<DataBuffer> body, Charset charset,
                                           boolean isRequest) {
        String id = exchange.getAttribute(REQUEST_ID);
        return DataBufferUtilFix.join(body)
                .doOnNext(wrapper -> {
                    if (isRequest) {
                        exchange.getAttributes().put(CACHE_REQUEST_BODY_OBJECT_KEY, new String(wrapper.getData(), charset));
                        log.info("[{}] - 请求体：{}",id,new String(wrapper.getData(), charset));
                    } else {
                        log.info("[{}] - 响应体：{}",id,new String(wrapper.getData(), charset));
                        long startTime = (Long)exchange.getAttributes().get(START_TIME);
                        log.info("[{}] - 请求耗时：{}ms",id,System.currentTimeMillis() - startTime);
                    }
                    wrapper.clear();
                }).then();
    }

    private static boolean shouldRecordBody(MediaType contentType) {
        String type = contentType.getType();
        String subType = contentType.getSubtype();

        if ("application".equals(type)) {
            return "json".equals(subType) || "x-www-form-urlencoded".equals(subType) || "xml".equals(subType) || "atom+xml".equals(subType) || "rss+xml".equals(subType);
        } else if ("text".equals(type)) {
            return true;
        }
        return false;
    }

    private static Charset getMediaTypeCharset(@Nullable MediaType mediaType) {
        if (mediaType != null && mediaType.getCharset() != null) {
            return mediaType.getCharset();
        } else {
            return StandardCharsets.UTF_8;
        }
    }

    public static Mono<Void> recorderOriginalRequest(ServerWebExchange exchange) {
        exchange.getAttributes().put(START_TIME,System.currentTimeMillis());
        exchange.getAttributes().put(REQUEST_ID,UUID.randomUUID().toString());
        ServerHttpRequest request = exchange.getRequest();
        return recorderRequest(exchange, request.getURI());
    }

    public static Mono<Void> recorderRouteRequest(ServerWebExchange exchange) {
        URI requestUrl = exchange.getRequiredAttribute(GATEWAY_REQUEST_URL_ATTR);
        return recorderRequest(exchange, requestUrl);
    }

    private static Mono<Void> recorderRequest(ServerWebExchange exchange, URI uri) {
        String id = exchange.getAttribute(REQUEST_ID);
        ServerHttpRequest request = exchange.getRequest();
        if (uri == null) {
            uri = request.getURI();
        }
        HttpMethod method = request.getMethod();
        HttpHeaders headers = request.getHeaders();
        log.info("[{}] - 请求uri：{}",id,uri.toString());
        log.info("[{}] - 请求方式：{}",id,method.toString());
        log.info("[{}] - 请求头：{}",id,headers);

        Charset bodyCharset = null;
        if (hasBody(method)) {
            long length = headers.getContentLength();
            if (length > 0){
                MediaType contentType = headers.getContentType();
                if (contentType != null && shouldRecordBody(contentType)) {
                    bodyCharset = getMediaTypeCharset(contentType);
                }
            }
        }

        if (bodyCharset != null) {
            return doRecordBody(exchange, request.getBody(), bodyCharset,true);
        } else {
            return Mono.empty();
        }
    }

    public static Mono<Void> recorderResponse(ServerWebExchange exchange) {
        String id = exchange.getAttribute(REQUEST_ID);
        RecorderServerHttpResponseDecorator response = (RecorderServerHttpResponseDecorator) exchange.getResponse();
        HttpStatus code = response.getStatusCode();
        if (code == null) {
            log.info("[{}] - [响应异常]",id);
            return Mono.empty();
        }

        HttpHeaders headers = response.getHeaders();

        log.info("[{}] - 响应码：{},{}",id,code.value(),code.getReasonPhrase());
        log.info("[{}] - 响应头：{}",id,headers);

        Charset bodyCharset = null;
        MediaType contentType = headers.getContentType();
        if (contentType != null && shouldRecordBody(contentType)) {
            bodyCharset = getMediaTypeCharset(contentType);
        }

        if (bodyCharset != null) {
            return doRecordBody(exchange, response.copy(), bodyCharset,false);
        } else {
            long startTime = exchange.getAttribute(START_TIME);
            log.info("[{}] - 请求耗时：{}ms",id,System.currentTimeMillis() - startTime);
            return Mono.empty();
        }
    }
}