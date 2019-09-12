package org.zpp.gateway.filter;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import org.zpp.common.constant.BootCommonConstants;
import org.zpp.common.core.bean.CloudProperties;
import org.zpp.common.core.constant.Constant;
import org.zpp.common.core.util.R;
import org.zpp.common.properties.ValidateCodeProperties;
import org.zpp.common.validate.code.ValidateCodeProcessorHolder;
import org.zpp.common.validate.code.ValidateCodeType;
import org.zpp.gateway.common.GatewayConstant;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR;

/**
 * 验证码过滤器
 *
 * @author zpp
 */
@Slf4j
@Component
public class ValidateCodeFilter extends AbstractGatewayFilterFactory implements InitializingBean,Ordered {

	/**
	 * 存放所有需要校验验证码的url
	 */
	private Map<String, ValidateCodeType> urlMap = new HashMap<>();
	/**
	 * 验证请求url与配置的url是否匹配的工具类
	 */
	private AntPathMatcher pathMatcher = new AntPathMatcher();

	@Autowired
	private ValidateCodeProperties validateCodeProperties;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private CloudProperties cloudProperties;

	@Autowired
	private ValidateCodeProcessorHolder validateCodeProcessorHolder;

	@Override
	public GatewayFilter apply(Object config) {
		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();
			try {
				checkCode(exchange,request);
			} catch (Exception e) {
				ServerHttpResponse response = exchange.getResponse();
				HttpHeaders headers = response.getHeaders();
				headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
				response.setStatusCode(HttpStatus.BAD_REQUEST);
				try {
					return response.writeWith(Mono.just(response.bufferFactory()
						.wrap(objectMapper.writeValueAsBytes(
							R.builder().msg(e.getMessage())
								.code(Constant.FAIL).build()))));
				} catch (JsonProcessingException e1) {
					log.error("[验证码拦截异常] - [{}]", e1);
				}
			}

			return chain.filter(exchange);
		};
	}

	@Override
	public void afterPropertiesSet() throws Exception {

		urlMap.put(GatewayConstant.DEFAULT_LOGIN_PROCESSING_URL_FORM, ValidateCodeType.IMAGE);
		addUrlToMap(validateCodeProperties.getImage().getUrl(), ValidateCodeType.IMAGE);

		urlMap.put(GatewayConstant.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, ValidateCodeType.SMS);
		addUrlToMap(validateCodeProperties.getSms().getUrl(), ValidateCodeType.SMS);
	}

	/**
	 * 将系统中需要验证码的url加入到map中
	 * @param urlString
	 * @param type
	 */
	protected void addUrlToMap(String urlString, ValidateCodeType type) {
		if (StringUtils.isNotBlank(urlString)) {
			String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");
			for (String url : urls) {
				urlMap.put(url, type);
			}
		}
	}

	private void checkCode(ServerWebExchange exchange, ServerHttpRequest request) {
		ValidateCodeType type = getValidateCodeType(request);
		if (type != null) {
			String deviceId = request.getHeaders().get(BootCommonConstants.VALIDATE_CODE_HEADER_NAME).get(0);

			String body = exchange.getAttribute(GatewayConstant.CACHE_REQUEST_BODY_OBJECT_KEY);
			Map<String, String> paramMap = HttpUtil.decodeParamMap(body, CharsetUtil.UTF_8);
			String codeVal = paramMap.get(type.getParamNameOnValidate());

			validateCodeProcessorHolder.findValidateCodeProcessor(type)
					.validate(deviceId,codeVal);
			log.info("验证码校验通过");
		}
	}

	private ValidateCodeType getValidateCodeType(ServerHttpRequest request) {
		ValidateCodeType result = null;
		if (!StringUtils.equalsIgnoreCase(request.getMethod().name(), "get")) {
			//需要验证码的url
			Set<String> urls = cloudProperties.getValidateCodeUrls();
			String uri = request.getURI().getPath();
			for (String url : urls) {
				if (pathMatcher.match(url, uri)) {
					result = urlMap.get(url);
				}
			}
		}
		return result;
	}

	@Override
	public int getOrder() {
		return 99;
	}
}
