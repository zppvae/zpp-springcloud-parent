package org.zpp.message.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.zpp.common.core.constant.MQConstant;
import org.zpp.common.core.template.MobileCodeTemplate;


/**
 * 阿里大鱼短信服务
 * @author zpp
 * @date 2018/12/17 11:05
 */
@Component(MQConstant.MOBILE_CODE_CHANNEL_ALI)
public class AliMessageHandler extends AbstractSmsMessageHandler {

    private Logger log = LoggerFactory.getLogger(AliMessageHandler.class);

    @Override
    public void checkData(MobileCodeTemplate template) {
        Assert.notNull(template.getMobile(), "手机号不能为空");
        Assert.notNull(template.getTemplate(), "短信模板不能为空");
    }

    @Override
    public void fail(MobileCodeTemplate template) {
        log.error("阿里大鱼短信发送失败,{}",template.getTemplate());
    }

    @Override
    public boolean process(MobileCodeTemplate template) {
        //TODO 业务发送处理
        return false;
    }
}
