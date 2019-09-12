package org.zpp.gateway.common;

/**
 * @author zpp
 * @date 2019/8/15 17:27
 */
public interface GatewayConstant {

    /**
     * request body 缓存key
     */
    String CACHE_REQUEST_BODY_OBJECT_KEY = "cachedRequestBodyObject";

    /**
     * 异常回调 URL
     */
    String FALLBACK_URL = "/fallback";

    /**
     * 验证码存储前缀
     */
    String VALIDATE_CODE_PREFIX = "code:";

    /**
     * 图片验证码储存 redis key
     */
    String IMAGE_CODE_KEY = VALIDATE_CODE_PREFIX + "image:";

    /**
     * 图片验证码 URL
     */
    String IMAGE_CODE_URL = "/code/image";

    /**
     * 短信验证码 URL
     */
    String SMS_CODE_URL = "/code/sms";

    /**
     * 图片验证码超时时间，单位：s
     */
    Integer IMAGE_CODE_TIMEOUT = 60;

    /**
     * png 图片格式
     */
    String PNG = "png";

    /**
     * oauth2.0 token url
     */
    String OAUTH_TOKEN_URL = "/oauth/token";

    /**
     * 用户名密码登录请求处理url
     */
    String DEFAULT_LOGIN_PROCESSING_URL_FORM = "/authentication/form";

    /**
     * 手机验证码登录请求处理url
     */
    String DEFAULT_LOGIN_PROCESSING_URL_MOBILE = "/authentication/mobile";

}
