package org.zpp.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;
import org.zpp.common.core.bean.FilterIgnoreConfig;
import org.zpp.security.core.authorize.AuthorizeConfigProvider;

/**
 * @author zpp
 * @date 2019/8/14 16:23
 */
@Component
@Order(Integer.MIN_VALUE)
public class CloudAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Autowired
    private FilterIgnoreConfig filterIgnoreConfig;

    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        //不拦截配置的URL
        filterIgnoreConfig.getUrls().forEach(url -> config.antMatchers(url).permitAll());
        return false;
    }
}
