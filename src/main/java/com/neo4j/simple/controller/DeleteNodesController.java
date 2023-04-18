package com.neo4j.simple.controller;

import com.neo4j.simple.entity.CommonResult;
import com.neo4j.simple.entity.Neo4jBasicNode;
import com.neo4j.simple.service.DeleteNodesService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author lgz
 * @e-mail 2821626468@qq.com
 * @create 2023-03-05 15:16
 */
@RestController
public class DeleteNodesController {
    @Resource
    private DeleteNodesService deleteNodesService;

    /**
     *  删除节点(包括关系, 会删除所有与节点相关的关系)
     * @param neo4jBasicNode
     *        {
     *            id: 1,
     *            labels: {
     *                "Person",
     *                "Actor"
     *            },
     *            properties: {
     *              "name": "Tom Hanks",
     *              "born": 1956
     *            }
     *        }
     * @return 节点列表
     */
    @PostMapping("/deleteNode")
    public CommonResult deleteNode(@RequestBody Neo4jBasicNode neo4jBasicNode){
        return new CommonResult().success().data(deleteNodesService.deleteNode(neo4jBasicNode));
    }

    /**
     *  删除节点(不包括关系, 只删除不存在关系的，存在关系的节点将不会被删除关系)
     * @param neo4jBasicNode
     *        {
     *            id: 1,
     *            label: {
     *                "Person",
     *                "Actor"
     *            },
     *            properties: {
     *              "name": "Tom Hanks",
     *              "born": 1956
     *            }
     *        }
     * @return 节点列表
     */
    @PostMapping("/deleteNodeNoRelation")
    public CommonResult deleteNodeNoRelation(@RequestBody Neo4jBasicNode neo4jBasicNode){
        return new CommonResult().success().data(deleteNodesService.deleteNodeNoRelation(neo4jBasicNode));
    }
}
