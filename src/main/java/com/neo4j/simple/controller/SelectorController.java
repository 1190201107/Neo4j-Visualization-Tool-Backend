package com.neo4j.simple.controller;


import com.neo4j.simple.entity.CommonResult;
import com.neo4j.simple.entity.Neo4jBasicNode;
import com.neo4j.simple.service.SelectNeo4jNodeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SelectorController {
    @Resource
    private SelectNeo4jNodeService selectNeo4jNodeService;

    /**
     * 查询节点的所有标签
     * @return
     */
    @GetMapping("/getAllLabel")
    public CommonResult<List<String>> getAllLabel(){
        return new CommonResult<List<String>>().success().data(selectNeo4jNodeService.selectAllLabels()) ;
    }

    /**
     * 查询边的所有名称
     * @return
     */
    @GetMapping("/getAllRelationName")
    public CommonResult<List<String>> getAllRelationName(){
        return new CommonResult<List<String>>().success().data(selectNeo4jNodeService.selectAllRelationName()) ;
    }

    /**
     * 查询节点的所有属性
     * @return
     */
    @GetMapping("/getAllPropertiesName")
    public CommonResult<List<String>> getAllPropertiesName(){
        return new CommonResult<List<String>>().success().data(selectNeo4jNodeService.selectAllPropertiesName()) ;
    }

    /**
     * 查询所有节点
     * @return
     */
    @GetMapping("/getAllGraph")
    public CommonResult<HashMap<String, Map>> getAllGraph(){
        return new CommonResult<HashMap<String, Map>>().success().data(selectNeo4jNodeService.selectAllGraph());
    }


}
