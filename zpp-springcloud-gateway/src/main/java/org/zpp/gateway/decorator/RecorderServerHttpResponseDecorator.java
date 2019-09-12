package org.zpp.gateway.decorator;

import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.zpp.gateway.util.DataBufferUtilFix;
import org.zpp.gateway.util.DataBufferWrapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应解析器
 *
 * @author zpp
 */
public class RecorderServerHttpResponseDecorator extends ServerHttpResponseDecorator {
    private DataBufferWrapper data = null;

    public RecorderServerHttpResponseDecorator(ServerHttpResponse delegate) {
        super(delegate);
    }

    @Override
    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
        return DataBufferUtilFix.join(Flux.from(body))
                .doOnNext(d -> this.data = d)
                .flatMap(d -> super.writeWith(copy()));
    }

    @Override
    public Mono<Void> writeAndFlushWith(Publisher<? extends Publisher<? extends DataBuffer>> body) {
        return writeWith(Flux.from(body)
                .flatMapSequential(p -> p));
    }

    public Flux<DataBuffer> copy() {
        DataBuffer buffer = this.data.newDataBuffer();
        if (buffer == null)
            return Flux.empty();

        return Flux.just(buffer);
    }
}