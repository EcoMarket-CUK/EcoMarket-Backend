package com.api.jaebichuri.global.config;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SshDataSourceConfig {

    @Value("${ssh.database_url}")
    private String databaseUrl;

    @Value("${ssh.database_port}")
    private int databasePort;

    @Value("${profile}")
    String profile;

    private final SshTunnelingInitializer initializer;

    @Bean("dataSource")
    @Primary
    public DataSource dataSource(DataSourceProperties properties) {
        String url;
        if (profile.equals("local")) {
            Integer forwardedPort = initializer.buildSshConnection(databaseUrl, databasePort);  // ssh 연결 및 터널링 설정
            url = properties.getUrl().replace("[forwardedPort]", Integer.toString(forwardedPort));
        } else {
            url = properties.getUrl().replace("[forwardedPort]", Integer.toString(5432));
            url = url.replace("127.0.0.1", databaseUrl);
        }

        log.info(url);
        return DataSourceBuilder.create()
            .url(url)
            .username(properties.getUsername())
            .password(properties.getPassword())
            .driverClassName(properties.getDriverClassName())
            .build();
    }
}