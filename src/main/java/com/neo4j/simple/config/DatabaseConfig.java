package com.neo4j.simple.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.data.neo4j")
@RefreshScope
@Data
public class DatabaseConfig {

    private String uri;
    private String username;
    private String password;

}