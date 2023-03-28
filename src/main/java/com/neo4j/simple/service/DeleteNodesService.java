package com.neo4j.simple.service;

import com.neo4j.simple.entity.Neo4jBasicNode;

public interface DeleteNodesService {

    public Integer deleteNode(Neo4jBasicNode neo4jBasicNode);

    public Integer deleteNodeNoRelation(Neo4jBasicNode neo4jBasicNode);
}
