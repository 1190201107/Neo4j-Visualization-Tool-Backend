package com.neo4j.simple.controller;


import com.neo4j.simple.config.DatabaseConfig;
import com.neo4j.simple.config.Neo4jConfig;
import com.neo4j.simple.entity.CommonResult;
import com.neo4j.simple.entity.UserMessage;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import javax.annotation.Resource;
import java.io.*;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.*;


/**
 * @author lgz
 * @e-mail 2821626468@qq.com
 * @create 2023-03-05 15:41
 */
@RestController
public class ModifyDatabaseConfigController {

//    @Resource
//    Neo4jConfig neo4jConfig;
//
//    private final ConfigurableApplicationContext context;
//
//    public ModifyDatabaseConfigController(ConfigurableApplicationContext context) {
//        this.context = context;
//    }
//
//    @Resource
//    DatabaseConfig databaseConfig;

    @PostMapping("/neo4j-config")
    public CommonResult<String> updateNeo4jConfig(@RequestParam("uri") String uri, @RequestBody UserMessage userMessage) throws SQLException {

//        ConfigurableEnvironment env = context.getEnvironment();
//        Map<String, Object> properties = new HashMap<>();
//        properties.put("spring.data.neo4j.uri", uri);
//        properties.put("spring.data.neo4j.username", userMessage.username);
//        properties.put("spring.data.neo4j.password", userMessage.password);
//
//        MapPropertySource propertySource = new MapPropertySource("my-property-source", properties);
//
//        MutablePropertySources propertySources = env.getPropertySources();
//        propertySources.addFirst(propertySource);

//        System.out.println("b-uri: " + databaseConfig.getUri());
//        System.out.println("b-username: " + databaseConfig.getUsername());
//        System.out.println("b-password: " + databaseConfig.getPassword());
//        databaseConfig.setUri(uri);
//        databaseConfig.setUsername(userMessage.username);
//        databaseConfig.setPassword(userMessage.password);
//        System.out.println("uri: " + databaseConfig.getUri());
//        System.out.println("username: " + databaseConfig.getUsername());
//        System.out.println("password: " + databaseConfig.getPassword());

        try {
            // 加载.properties文件
            InputStream in = getClass().getResourceAsStream("/application.properties");
            System.out.println("in: " + in);
            Properties props = new Properties();
            props.load(in);
            if (in != null) {
                in.close();
            }
            // 修改属性值
            props.setProperty("spring.data.neo4j.uri", uri);
            props.setProperty("spring.data.neo4j.username", userMessage.username);
            props.setProperty("spring.data.neo4j.password", userMessage.password);
            // 保存到文件
            OutputStream out = Files.newOutputStream(new File(Objects.requireNonNull(getClass().getResource("/application.properties")).toURI()).toPath());
            props.store(out, null);
            out.close();

//            // 强制重新加载文件
//            props.clear();
//            in = new FileInputStream("/application.properties");
//            props.load(in);
//            in.close();

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return new CommonResult<String>().fail().message("fail");
        }

        return new CommonResult<String>().success().message("success");
    }
}
