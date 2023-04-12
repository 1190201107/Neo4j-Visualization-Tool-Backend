package com.neo4j.simple.controller;

import com.neo4j.simple.config.Neo4jConfig;
import com.neo4j.simple.entity.CommonResult;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lgz
 * @e-mail 2821626468@qq.com
 * @create 2023-03-05 15:41
 */
@RestController
public class ModifyDatabaseConfigController {

    private final ConfigurableApplicationContext context;

    public ModifyDatabaseConfigController(ConfigurableApplicationContext context) {
        this.context = context;
    }

    @PostMapping("/neo4j-config")
    public CommonResult<String> updateNeo4jConfig(@RequestParam String uri,
                                                  @RequestParam String username,
                                                  @RequestParam String password) {
//        ConfigurableApplicationContext context = Neo4jConfig.getContext();
        ConfigurableEnvironment env = context.getEnvironment();

        Map<String, Object> properties = new HashMap<>();
        properties.put("spring.data.neo4j.uri", uri);
        properties.put("spring.data.neo4j.username", username);
        properties.put("spring.data.neo4j.password", password);

        MapPropertySource propertySource = new MapPropertySource("my-property-source", properties);

        MutablePropertySources propertySources = env.getPropertySources();
        propertySources.addFirst(propertySource);

        return new CommonResult<String>().code(200).message("success");
    }
}
