package com.neo4j.simple.controller;

import com.neo4j.simple.entity.CommonResult;
import com.neo4j.simple.entity.Neo4jBasicNode;
import com.neo4j.simple.service.CreateNodesService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lgz
 * @e-mail 2821626468@qq.com
 * @create 2023-03-05 15:15
 */
public class CreateNodesController {
    @Resource
    private CreateNodesService createNodesService;

    @PostMapping("/createNode")
    public CommonResult createNode(@RequestBody Neo4jBasicNode neo4jBasicNode){
        if(createNodesService.createNode(neo4jBasicNode)){
            return new CommonResult().success();
        }else{
            return new CommonResult().fail();
        }
    }

    @PostMapping("/createNodeNoRepeat")
    public CommonResult createNodeNoRepeat(@RequestBody Neo4jBasicNode neo4jBasicNode){
        if(createNodesService.createNodeNoRepeat(neo4jBasicNode)){
            return new CommonResult().success();
        }else{
            return new CommonResult().fail();
        }
    }

    @PostMapping("/recreateNodeNoRepeat")
    public CommonResult recreateNodeNoRepeat(@RequestBody Neo4jBasicNode neo4jBasicNode){
        if(createNodesService.recreateNodeNoRepeat(neo4jBasicNode)){
            return new CommonResult().success();
        }else{
            return new CommonResult().fail();
        }
    }

    @PostMapping("/batchCreateNode")
    public CommonResult batchCreateNode(@RequestBody List<Neo4jBasicNode> neo4jBasicNodes){
        Long count = createNodesService.batchCreateNode(neo4jBasicNodes);
        if(count > 0){
            return new CommonResult().success().data(count);
        }else{
            return new CommonResult().fail();
        }
    }

    @PostMapping("/batchCreateNodeNoRepeat")
    public CommonResult batchCreateNodeNoRepeat(@RequestBody List<Neo4jBasicNode> neo4jBasicNodes){
        Long count = createNodesService.batchCreateNodeNoRepeat(neo4jBasicNodes);
        if(count > 0){
            return new CommonResult().success().data(count);
        }else{
            return new CommonResult().fail();
        }
    }
}
