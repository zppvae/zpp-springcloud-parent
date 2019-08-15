package org.zpp.common.core.constant;

/**
 * 消息队列常量
 * @author zpp
 * @date 2018/12/17 10:14
 */
public interface MQConstant {

    /**
     * 日志队列
     */
    String QUEUE_LOG = "log";

    /**
     * 短信验证码队列
     */
    String QUEUE_MOBILE_CODE = "mobile_code_queue";

    /**
     * 阿里大鱼短信
     */
    String MOBILE_CODE_CHANNEL_ALI = "ali_sms";
}
