package com.neo4j.simple.service;

import com.neo4j.simple.entity.Neo4jBasicNode;
import com.neo4j.simple.entity.Neo4jBasicRelation;
import com.neo4j.simple.entity.Neo4jQueryRelation;

import java.util.List;

public interface ImportNodesService {

    //批量添加节点
    public Long batchAddNodes(List<Neo4jBasicNode> nodes);

    //批量添加关系

    public int batchAddRelations(List<Neo4jQueryRelation> relations);
}


