package org.zpp.user.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zpp.common.constant.MQConstant;

/**
 * @author zpp
 * @date 2018/12/17 10:13
 */
@Configuration
public class RabbitmqConfig {

    @Bean
    public Queue logQueue() {
        return new Queue(MQConstant.QUEUE_LOG);
    }

    @Bean
    public Queue mobileCodeQueue() {
        return new Queue(MQConstant.QUEUE_MOBILE_CODE);
    }
}
