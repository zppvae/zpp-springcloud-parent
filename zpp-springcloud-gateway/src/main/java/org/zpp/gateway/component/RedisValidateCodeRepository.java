package org.zpp.gateway.component;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.zpp.common.exception.ValidateCodeException;
import org.zpp.common.validate.code.ValidateCode;
import org.zpp.common.validate.code.ValidateCodeRepository;
import org.zpp.common.validate.code.ValidateCodeType;

import java.util.concurrent.TimeUnit;

/**
 * 基于redis的验证码存储
 */
@Component
public class RedisValidateCodeRepository implements ValidateCodeRepository {

	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;


	@Override
	public void save(String deviceId, ValidateCode code, ValidateCodeType type) {
		redisTemplate.opsForValue().set(buildKey(deviceId, type), code, 30, TimeUnit.MINUTES);
	}


	@Override
	public ValidateCode get(String deviceId, ValidateCodeType type) {
		Object value = redisTemplate.opsForValue().get(buildKey(deviceId, type));
		if (value == null) {
			return null;
		}
		return (ValidateCode) value;
	}


	@Override
	public void remove(String deviceId, ValidateCodeType type) {
		redisTemplate.delete(buildKey(deviceId, type));
	}

	/**
	 * @param type
	 * @return
	 */
	private String buildKey(String deviceId, ValidateCodeType type) {
		if (StringUtils.isBlank(deviceId)) {
			throw new ValidateCodeException("请在请求头中携带deviceId参数");
		}
		return "code:" + type.toString().toLowerCase() + ":" + deviceId;
	}

}