package com.neo4j.simple.service;

import com.neo4j.simple.entity.Neo4jBasicNode;

import java.util.List;

public interface ExportNodesService {

    public boolean exportNodesToExcel(List<Neo4jBasicNode> nodes);

    public boolean exportNodesToJson(List<Neo4jBasicNode> nodes);
}
