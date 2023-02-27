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

    @GetMapping("/getAllLabel")
    public CommonResult<List<String>> getAllLabel(){
        return new CommonResult<List<String>>().success().data(selectNeo4jNodeService.selectAllLabels()) ;
    }

    @GetMapping("/getAllRelationName")
    public CommonResult<List<String>> getAllRelationName(){
        return new CommonResult<List<String>>().success().data(selectNeo4jNodeService.selectAllRelationName()) ;
    }

    @GetMapping("/getAllGraph")
    public CommonResult<HashMap<String, Map>> getAllGraph(){
        return new CommonResult<HashMap<String, Map>>().success().data(selectNeo4jNodeService.selectAllGraph());
    }

}
