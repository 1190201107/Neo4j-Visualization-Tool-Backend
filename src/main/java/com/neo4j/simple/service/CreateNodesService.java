package com.neo4j.simple.service;


import com.neo4j.simple.entity.Neo4jBasicNode;

import java.util.*;

public interface CreateNodesService {

    public boolean createNode(Neo4jBasicNode neo4jBasicNode);
    public boolean createNodeNoRepeat(Neo4jBasicNode neo4jBasicNode);
    public boolean recreateNodeNoRepeat(Neo4jBasicNode neo4jBasicNode);
    public Long batchCreateNode(List<Neo4jBasicNode> neo4jBasicNodes);
    public Long batchCreateNodeNoRepeat(List<Neo4jBasicNode> neo4jBasicNodes);
}
