package com.neo4j.simple;

import com.neo4j.simple.util.Neo4jUtil;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * Neo4jUtilTest
 * Neo4jUtil测试类
 * @Author 1190201107
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Neo4jUtilTest {

    @Resource
    Neo4jUtil neo4jUtil;

    @Test
    public void getAllLabelNameTest() {
        List<String> allLabelName = neo4jUtil.getAllLabelName();
        for (String s : allLabelName) {
            System.out.println(s);
        }
    }

    @Test
    public void getAllRelationNameTest() {
        List<String> allRelationName = neo4jUtil.getAllRelationName();
        for (String s : allRelationName) {
            System.out.println(s);
        }
    }

    @Test
    public void getAllNodePropertyTest() {
        List<String> allNodeProperty = neo4jUtil.getAllNodeProperty();
        for (String s : allNodeProperty) {
            System.out.println(s);
        }
    }


}
