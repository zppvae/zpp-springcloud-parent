package org.zpp.zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.Bean;
import zipkin.storage.mysql.MySQLStorage;
import zipkin2.server.internal.EnableZipkinServer;

import javax.sql.DataSource;

/**
 * @author zpp
 * @date 2019/9/19 15:00
 */
@SpringCloudApplication
@EnableZipkinServer
public class ZipkinApplication {

    @Bean
    public MySQLStorage mySQLStorage(DataSource datasource) {
        return MySQLStorage.builder().datasource(datasource).executor(Runnable::run).build();
    }

    public static void main(String[] args) {
        SpringApplication.run(ZipkinApplication.class, args);
    }
}
