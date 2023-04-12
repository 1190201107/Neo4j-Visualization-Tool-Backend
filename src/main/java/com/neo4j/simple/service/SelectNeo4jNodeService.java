package com.neo4j.simple.service;


import com.neo4j.simple.entity.Neo4jBasicNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SelectNeo4jNodeService {


    public List<String> selectAllLabels();

    public List<String> selectAllRelationName();

    public List<String> selectAllPropertiesName();

    public HashMap<String, Map> selectAllGraph();

    public HashMap<String, Map> selectNode(Neo4jBasicNode neo4jBasicNode);

}
