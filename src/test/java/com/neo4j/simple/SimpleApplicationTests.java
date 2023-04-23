package com.neo4j.simple;

import com.neo4j.simple.entity.Neo4jBasicNode;
import com.neo4j.simple.util.Neo4jUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SimpleApplicationTests {
    @Resource
    Neo4jUtil neo4jUtil;

    @Test
    void contextLoads() {
    }

    @Test
    public void neo4jUtilTest(){
        List<String> allLabelName = neo4jUtil.getAllLabelName();
        for (String s : allLabelName) {
            System.out.println(s);
        }

    }
    
    @Test
    public void testCreateNode(){
        Neo4jBasicNode neo4jBasicNode = new Neo4jBasicNode();
        List<String> label = new ArrayList<>();
        label.add("lgz");
        Map<String, Object> stringObjectHashMap = new HashMap<>();
        neo4jBasicNode.setLabels(label);
        neo4jBasicNode.setProperties(stringObjectHashMap);
        neo4jBasicNode.setId(1L);

        neo4jUtil.createNode(neo4jBasicNode);

        List<String> allLabelName = neo4jUtil.getAllLabelName();
        for (String s : allLabelName) {
            System.out.println(s);
        }
    }

}
