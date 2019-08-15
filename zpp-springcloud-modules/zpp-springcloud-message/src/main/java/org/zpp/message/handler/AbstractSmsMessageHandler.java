package org.zpp.message.handler;

import org.zpp.common.core.template.MobileCodeTemplate;

/**
 * @author zpp
 * @date 2018/12/17 10:49
 */
public abstract class AbstractSmsMessageHandler implements SmsMessageHandler {

    @Override
    public void execute(MobileCodeTemplate template) {
        checkData(template);
        if (!process(template)) {
            fail(template);
        }
    }

    @Override
    public abstract void checkData(MobileCodeTemplate template);

    @Override
    public abstract void fail(MobileCodeTemplate template);

    @Override
    public abstract boolean process(MobileCodeTemplate template);
}
