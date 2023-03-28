package com.neo4j.simple.controller;

import com.neo4j.simple.entity.CommonResult;
import com.neo4j.simple.entity.Neo4jBasicNode;
import com.neo4j.simple.service.SelectNeo4jNodeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lgz
 * @e-mail 2821626468@qq.com
 * @create 2023-03-05 15:16
 */
@RestController
public class SearchNodesController {
    @Resource
    private SelectNeo4jNodeService selectNeo4jNodeService;

    /**
     *  按条件查询节点
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
    @PostMapping("/searchNodes")
    public CommonResult<List<Neo4jBasicNode>> searchNodes(@RequestBody Neo4jBasicNode neo4jBasicNode){
        return new CommonResult<List<Neo4jBasicNode>>().success().data(selectNeo4jNodeService.selectNode(neo4jBasicNode));
    }

}
