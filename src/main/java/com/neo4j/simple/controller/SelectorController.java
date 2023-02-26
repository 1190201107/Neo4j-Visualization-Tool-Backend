package com.neo4j.simple.controller;


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
    public List<String> getAllLabel(){
        return selectNeo4jNodeService.selectAllLabels();
    }

    @GetMapping("/getAllGraph")
    public HashMap<String, Map> getAllGraph(){
        return selectNeo4jNodeService.selectAllGraph();
    }

}
