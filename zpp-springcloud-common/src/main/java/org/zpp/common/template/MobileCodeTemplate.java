package org.zpp.common.template;

import java.io.Serializable;

/**
 * 短信验证码模板对象
 * @author zpp
 * @date 2018/12/17 10:53
 */
public class MobileCodeTemplate implements Serializable {

    private String mobile;

    /** 短信通道  {@link org.zpp.common.constant.MQConstant}*/
    private String channel;

    /** 短信模板*/
    private String template;

    /** 生成后的json数据*/
    private String json;

    public MobileCodeTemplate(){}

    public MobileCodeTemplate(String mobile, String channel, String template) {
        this.mobile = mobile;
        this.channel = channel;
        this.template = template;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
