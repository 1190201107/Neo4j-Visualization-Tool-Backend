package com.neo4j.simple.controller;

import com.neo4j.simple.VO.Neo4jSaveRelationDTO;
import com.neo4j.simple.entity.CommonResult;
import com.neo4j.simple.service.CreateRelationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class CreateRelationController {
    @Resource
    private CreateRelationService createRelationService;

    @PostMapping("/queryNodeCreateRelation")
    public CommonResult queryNodeCreateRelation(@RequestBody Neo4jSaveRelationDTO saveRelation){
        return new CommonResult().success().data(createRelationService.queryNodeCreateRelation(saveRelation));
    }

    @PostMapping("/createNodeAndRelation")
    public CommonResult createNodeAndRelation(@RequestBody Neo4jSaveRelationDTO saveRelation){
        if(createRelationService.createNodeAndRelation(saveRelation)){
            return new CommonResult().success();
        }else{
            return new CommonResult().fail();
        }
    }
}
