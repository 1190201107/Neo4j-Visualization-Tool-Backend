package com.neo4j.simple.controller;


import com.neo4j.simple.service.SelectNeo4jNodeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class SelectorController {
    @Resource
    private SelectNeo4jNodeService selectNeo4jNodeService;

    @GetMapping("/getAllLable")
    public void getAllLabel(){
        selectNeo4jNodeService.selectAllLabels();
    }

}
