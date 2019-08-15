package org.zpp.message.handler;

import org.zpp.common.core.template.MobileCodeTemplate;

/**
 * @author zpp
 * @date 2018/12/17 10:45
 */
public interface SmsMessageHandler {

    /**
     * 执行逻辑入口
     * @param template
     */
    void execute(MobileCodeTemplate template);

    /**
     * 数据校验
     * @param template
     */
    void checkData(MobileCodeTemplate template);

    /**
     * 业务失败处理
     * @param template
     */
    void fail(MobileCodeTemplate template);

    /**
     * 业务逻辑处理
     * @param template
     * @return
     */
    boolean process(MobileCodeTemplate template);
}
