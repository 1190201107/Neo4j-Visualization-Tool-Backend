package com.neo4j.simple.controller;

import com.neo4j.simple.entity.CommonResult;
import com.neo4j.simple.service.CreateIndexService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class CreateIndexController {
    @Resource
    CreateIndexService createIndexService;

    @GetMapping("/createIndex")
    public CommonResult<Boolean> createIndex(@RequestParam String label, @RequestParam String property) {
        return new CommonResult<Boolean>().success().data(createIndexService.createIndex(label, property));
    }

}
