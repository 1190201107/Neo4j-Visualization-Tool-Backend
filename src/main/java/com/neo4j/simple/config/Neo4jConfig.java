package com.neo4j.simple.config;


import org.neo4j.ogm.session.SessionFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;

@Configuration
@Component
@EnableTransactionManagement
@RefreshScope
public class Neo4jConfig {

//    private final ConfigurableApplicationContext context;
//
//    public Neo4jConfig(ConfigurableApplicationContext context) {
//        this.context = context;
//    }

//    @Value("${spring.data.neo4j.uri}")
//    private String uri;
//    @Value("${spring.data.neo4j.username}")
//    private String userName;
//    @Value("${spring.data.neo4j.password}")
//    private String password;
    @Resource
    private DatabaseConfig databaseConfig;

    @Bean
    @RefreshScope
    public org.neo4j.ogm.config.Configuration getConfiguration() {
//        ConfigurableEnvironment env = context.getEnvironment();

        System.out.println("config-uri: " + databaseConfig.getUri());
        System.out.println("config-username: " + databaseConfig.getUsername());
        System.out.println("config-password: " + databaseConfig.getPassword());
        return new org.neo4j.ogm.config.Configuration.Builder()
                .uri(databaseConfig.getUri())
                .credentials(databaseConfig.getUsername(), databaseConfig.getPassword())
                .build();
    }

    @Bean
    @RefreshScope
    public SessionFactory sessionFactory() {
        return new SessionFactory(getConfiguration(),"com.neo4j.simple.entity");
    }


    @Bean("neo4jTransaction")
    @RefreshScope
    public Neo4jTransactionManager neo4jTransactionManager(SessionFactory sessionFactory) {
        return new Neo4jTransactionManager(sessionFactory);
    }
}
