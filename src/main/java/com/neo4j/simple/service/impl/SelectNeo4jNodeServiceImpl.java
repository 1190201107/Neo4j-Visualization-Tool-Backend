package com.neo4j.simple.service.impl;


import com.neo4j.simple.VO.Neo4jBasicRelationReturnVO;
import com.neo4j.simple.VO.RelationDTO;
import com.neo4j.simple.entity.Neo4jBasicNode;
import com.neo4j.simple.service.SelectNeo4jNodeService;
import com.neo4j.simple.util.Neo4jUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.* ;

@Service
public class SelectNeo4jNodeServiceImpl implements SelectNeo4jNodeService {
    @Resource
    Neo4jUtil neo4jUtil;

    @Override
    public List<String> selectAllLabels(){
        List<String> allLabelName = neo4jUtil.getAllLabelName();
        return allLabelName;
    }

    @Override
    public HashMap<String, Map> selectAllGraph() {
        Neo4jBasicNode neo4jBasicNode = new Neo4jBasicNode();
        List<Neo4jBasicNode> neo4jBasicNodes = neo4jUtil.queryNode(neo4jBasicNode);
        RelationDTO relationDTO = new RelationDTO();
        List<Neo4jBasicRelationReturnVO> neo4jBasicRelationReturnVOS = neo4jUtil.queryRelation(relationDTO);
        HashMap<String, List> temp = new HashMap<>();
        temp.put("nodes", neo4jBasicNodes);
        temp.put("relationships", neo4jBasicRelationReturnVOS);
        HashMap<String, Map> graph = new HashMap<>();
        graph.put("graph", temp);
        return graph;
    }

}
