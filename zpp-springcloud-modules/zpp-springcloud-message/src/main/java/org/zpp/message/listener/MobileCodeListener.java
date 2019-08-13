package org.zpp.message.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.zpp.common.constant.MQConstant;
import org.zpp.common.template.MobileCodeTemplate;
import org.zpp.message.handler.SmsMessageHandler;

import java.util.Map;

/**
 *  接收短信验证码队列消息
 * @author zpp
 * @date 2018/12/17 10:32
 */
@Configuration
@RabbitListener(queues = MQConstant.QUEUE_MOBILE_CODE)
public class MobileCodeListener {

    private Logger log = LoggerFactory.getLogger(MobileCodeListener.class);

    /**
     * 对应多个短信服务通道  Component
     */
    @Autowired
    private Map<String,SmsMessageHandler> smsMessageHandlerMap;

    @RabbitHandler
    public void receive(MobileCodeTemplate template) {
        SmsMessageHandler handler = smsMessageHandlerMap.get(template.getChannel());
        if (handler == null) {
            log.error("不存在的消息通道");
            return;
        }
        handler.execute(template);
    }
}
