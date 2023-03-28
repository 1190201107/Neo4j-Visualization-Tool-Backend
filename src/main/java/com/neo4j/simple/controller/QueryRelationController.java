package com.neo4j.simple.controller;

import com.neo4j.simple.VO.Neo4jBasicRelationReturnVO;
import com.neo4j.simple.VO.RelationDTO;
import com.neo4j.simple.entity.CommonResult;
import com.neo4j.simple.entity.Neo4jQueryRelation;
import com.neo4j.simple.service.QueryRelationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class QueryRelationController {
    @Resource
    private QueryRelationService queryRelationService;

    @PostMapping("/queryRelation")
    public CommonResult<List<Neo4jBasicRelationReturnVO>> queryRelation(RelationDTO relationDTO){
        return new CommonResult<List<Neo4jBasicRelationReturnVO>>().success().data(queryRelationService.queryRelation(relationDTO));
    }

    @PostMapping("/queryRelationRelationships")
    public CommonResult<List<Neo4jQueryRelation>> queryRelationRelationships(RelationDTO relationDTO){
        return new CommonResult<List<Neo4jQueryRelation>>().success().data(queryRelationService.queryRelationRelationships(relationDTO));
    }


}
