package org.zpp.gateway.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.zpp.common.constant.BootCommonConstants;
import org.zpp.common.core.constant.Constant;
import org.zpp.common.validate.code.ValidateCodeProcessorHolder;
import reactor.core.publisher.Mono;


@Slf4j
@Component
@AllArgsConstructor
public class ImageCodeHandler implements HandlerFunction<ServerResponse> {

	private final ValidateCodeProcessorHolder validateCodeProcessorHolder;

	@Override
	public Mono<ServerResponse> handle(ServerRequest serverRequest) {

		FastByteArrayOutputStream os = new FastByteArrayOutputStream();

		try {
			validateCodeProcessorHolder.findValidateCodeProcessor(BootCommonConstants.IMAGE_CODE_TYPE)
					.createToServerRequest(serverRequest,os);
		}catch (Exception e) {
			log.error("[图片验证码生成错误] - [{}]",e);
			return Mono.error(e);
		}

		return ServerResponse
			.status(HttpStatus.OK)
			.contentType(MediaType.IMAGE_JPEG)
			.body(BodyInserters.fromResource(new ByteArrayResource(os.toByteArray())));
	}
}
