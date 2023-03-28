package com.neo4j.simple.service;

import com.neo4j.simple.VO.Neo4jSaveRelationDTO;

public interface CreateRelationService {

    public int queryNodeCreateRelation(Neo4jSaveRelationDTO saveRelation);

    public boolean createNodeAndRelation(Neo4jSaveRelationDTO saveRelation);
}
