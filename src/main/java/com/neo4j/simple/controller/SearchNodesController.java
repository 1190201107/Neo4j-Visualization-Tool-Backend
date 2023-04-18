package com.neo4j.simple.controller;

import com.neo4j.simple.entity.CommonResult;
import com.neo4j.simple.entity.Neo4jBasicNode;
import com.neo4j.simple.service.SearchDataByConditionService;
import com.neo4j.simple.service.SelectNeo4jNodeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lgz
 * @e-mail 2821626468@qq.com
 * @create 2023-03-05 15:16
 */
@RestController
public class SearchNodesController {
    @Resource
    private SearchDataByConditionService searchDataByConditionService;

    @Resource
    private SelectNeo4jNodeService selectNeo4jNodeService;

    /**
     *  按条件查询节点
     * @param neo4jBasicNode
     *        {
     *            id: 1,
     *            labels: [
     *                "Person",
     *                "Actor"
     *            ],
     *            properties: {
     *              "name": "Tom Hanks",
     *              "born": 1956
     *            }
     *        }
     * @return 节点列表
     */
    @PostMapping("/searchGraphByNodes")
    public CommonResult<HashMap<String, Map>> searchGraphByNodes(@RequestBody Neo4jBasicNode neo4jBasicNode){
        System.out.println(neo4jBasicNode.toString());
        return new CommonResult<HashMap<String, Map>>().success().data(selectNeo4jNodeService.selectNode(neo4jBasicNode));
    }

    /**
     * 根据Label查询节点
     * @return 节点列表
     */
    @GetMapping("/searchDataByLabel")
    public CommonResult<HashMap<String, Map>> searchDataByLabel(@RequestParam("label") String label){
        return new CommonResult<HashMap<String, Map>>().success().data(searchDataByConditionService.searchDataByLabel(label));
    }

    /**
     * 根据property查询节点
     * @return 节点列表
     */
    @GetMapping("/searchDataByProperty")
    public CommonResult<HashMap<String, Map>> searchDataByProperty(@RequestParam("property") String property){
        return new CommonResult<HashMap<String, Map>>().success().data(searchDataByConditionService.searchDataByProperty(property));
    }

    /**
     * 根据label和properties查询满足条件的节点
     * @return 节点列表
     */
    @PostMapping("/searchDataByLabelsAndProperties")
    public <T> CommonResult<HashMap<String, Map>> searchDataByLabelsAndProperties(@RequestBody Map<String, List<T>> labelsAndProperties){
        return new CommonResult<HashMap<String, Map>>().success().data(searchDataByConditionService.searchDataByLabelsAndProperties(labelsAndProperties));
    }

}
