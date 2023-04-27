package com.neo4j.simple.controller;

import com.neo4j.simple.VO.DeleteRelationVO;
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
    public CommonResult<Integer> queryNodeCreateRelation(@RequestBody Neo4jSaveRelationDTO saveRelation){
        return new CommonResult<Integer>().success().data(createRelationService.queryNodeCreateRelation(saveRelation));
    }

    @PostMapping("/createNodeAndRelation")
    public CommonResult<Boolean> createNodeAndRelation(@RequestBody Neo4jSaveRelationDTO saveRelation){
        if(createRelationService.createNodeAndRelation(saveRelation)){
            return new CommonResult<Boolean>().success();
        }else{
            return new CommonResult<Boolean>().fail();
        }
    }
    /**
     * 删除两点之间的关系
     */
    @PostMapping("/deleteRelation")
    public CommonResult<Boolean> deleteRelation(@RequestBody DeleteRelationVO deleteRelationVO){
        if(createRelationService.deleteRelation(deleteRelationVO)){
            return new CommonResult<Boolean>().success();
        }else{
            return new CommonResult<Boolean>().fail();
        }
    }
}
