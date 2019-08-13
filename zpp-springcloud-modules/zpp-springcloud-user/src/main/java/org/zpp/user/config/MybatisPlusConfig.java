package org.zpp.user.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author zpp
 * @date 2019/8/12 17:18
 */
@Configuration
@MapperScan("org.zpp.user.mapper")
public class MybatisPlusConfig {
}
