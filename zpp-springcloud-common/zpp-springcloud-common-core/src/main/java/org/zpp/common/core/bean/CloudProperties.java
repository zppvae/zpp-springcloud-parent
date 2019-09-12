package org.zpp.common.core.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 全局配置
 *
 * @author zpp
 */
@Data
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "cloud")
public class CloudProperties {

    /**
     * 不拦截的Url
     */
    private List<String> ignoreUrls = new ArrayList<>();

    /**
     * 需要验证码的url
     */
    private Set<String> validateCodeUrls = new HashSet<>();
}
