package com.neo4j.simple.config;

import org.neo4j.ogm.config.ClasspathConfigurationSource;
import org.neo4j.ogm.config.ConfigurationSource;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
//@EnableNeo4jRepositories("com.neo4j.simple.repository") // 声明neo4j repository存放地址
public class Neo4jConfig {

    @Value("${spring.data.neo4j.uri}")
    private String uri;
    @Value("${spring.data.neo4j.username}")
    private String userName;
    @Value("${spring.data.neo4j.password}")
    private String password;

    @Bean
    public org.neo4j.ogm.config.Configuration getConfiguration() {
//        ConfigurationSource properties = new ClasspathConfigurationSource("application.properties");
//        org.neo4j.ogm.config.Configuration configuration = new org.neo4j.ogm.config.Configuration.Builder(properties).build();
        org.neo4j.ogm.config.Configuration configuration = new org.neo4j.ogm.config.Configuration.Builder()
                .uri(uri).connectionPoolSize(100).credentials(userName, password)
                .withBasePackages("com.neo4j.simple.repository").build();
        return configuration;
    }

    @Bean
    public SessionFactory sessionFactory() {
        return new SessionFactory(getConfiguration(),"com.neo4j.simple.entity");
    }

    @Bean("neo4jTransaction")
    public Neo4jTransactionManager neo4jTransactionManager(SessionFactory sessionFactory) {
        return new Neo4jTransactionManager(sessionFactory);
    }
}
