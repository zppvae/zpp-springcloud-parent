package org.zpp.gateway.filter;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.cloud.gateway.support.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.DefaultServerRequest;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import static org.zpp.gateway.common.GatewayConstant.CACHE_REQUEST_BODY_OBJECT_KEY;

/**
 * 请求体过滤器
 *
 * @author zpp
 */
@Slf4j
@Component
public class RequestBodyFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        Class inClass = String.class;

        ServerRequest serverRequest = new DefaultServerRequest(exchange);
        ServerHttpRequest request = exchange.getRequest();

        //只记录 http 请求(包含 https)
        String schema = request.getURI().getScheme();
        if ((!"http".equals(schema) && !"https".equals(schema))) {
            return chain.filter(exchange);
        }

        String contentType = request.getHeaders().getFirst("Content-Type");
        String upload = request.getHeaders().getFirst("upload");

        //get 方法获取参数
        if ("GET".equals(request.getMethod().name())) {
            if (!request.getQueryParams().isEmpty()) {
                Map<String, String> map = new HashMap<>();
                request.getQueryParams().toSingleValueMap().forEach((key, val) -> map.put(key, getURLDecoder(val)));
                String param = new Gson().toJson(map);
                exchange.getAttributes().put(CACHE_REQUEST_BODY_OBJECT_KEY, param);
            }
            return chain.filter(exchange);
        }

        //没有内容类型不读取body
        if (contentType == null || contentType.length() == 0) {
            return chain.filter(exchange);
        }

        //文件上传不读取body
        if ("true".equals(upload)) {
            return chain.filter(exchange);
        }

        Mono<?> modifiedBody = serverRequest.bodyToMono(inClass).flatMap(o -> {
            exchange.getAttributes().put(CACHE_REQUEST_BODY_OBJECT_KEY, o);
            return Mono.justOrEmpty(o);
        });

        BodyInserter bodyInserter = BodyInserters.fromPublisher(modifiedBody, inClass);
        HttpHeaders headers = new HttpHeaders();
        headers.putAll(exchange.getRequest().getHeaders());

        CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, headers);
        return bodyInserter.insert(outputMessage, new BodyInserterContext())
                .then(Mono.defer(() -> {
                    ServerHttpRequestDecorator decorator = new ServerHttpRequestDecorator(exchange.getRequest()) {

                        @Override
                        public HttpHeaders getHeaders() {
                            long contentLength = headers.getContentLength();
                            HttpHeaders httpHeaders = new HttpHeaders();
                            httpHeaders.putAll(super.getHeaders());
                            if (contentLength > 0) {
                                httpHeaders.setContentLength(contentLength);
                            } else {
                                httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
                            }
                            return httpHeaders;
                        }

                        @Override
                        public Flux<DataBuffer> getBody() {
                            return outputMessage.getBody();
                        }
                    };
                    return chain.filter(exchange.mutate().request(decorator).build());
                }));
    }

    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * URL 转码
     * @param val
     * @return
     */
    private String getURLDecoder(String val){
        try{
            return URLDecoder.decode(val, "utf-8");
        }catch (Exception e){
            log.error("getURLDecoder error",e);
        }
        return val;
    }
}