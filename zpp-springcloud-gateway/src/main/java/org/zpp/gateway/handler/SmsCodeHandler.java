package org.zpp.gateway.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.zpp.common.constant.BootCommonConstants;
import org.zpp.common.core.util.R;
import org.zpp.common.validate.code.ValidateCodeProcessorHolder;
import org.zpp.common.validate.code.sms.SmsCodeSender;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR;


/**
 * 短信验证码
 *
 * @author zpp
 */
@Slf4j
@Component
@AllArgsConstructor
public class SmsCodeHandler implements HandlerFunction<ServerResponse> {

	private final ValidateCodeProcessorHolder validateCodeProcessorHolder;

	@Override
	public Mono<ServerResponse> handle(ServerRequest serverRequest) {
		try {
			validateCodeProcessorHolder.findValidateCodeProcessor(BootCommonConstants.SMS_CODE_TYPE)
					.createToServerRequest(serverRequest,null);
		} catch (Exception e) {
			log.error("[短信验证码发送错误] - [{}]",e);
			return Mono.error(e);
		}

		return ServerResponse.status(HttpStatus.OK.value())
			.contentType(MediaType.APPLICATION_JSON_UTF8).body(
					BodyInserters.fromObject(new R())
				);
	}
}
