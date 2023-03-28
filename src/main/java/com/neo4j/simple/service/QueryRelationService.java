package com.neo4j.simple.service;

import com.neo4j.simple.VO.Neo4jBasicRelationReturnVO;
import com.neo4j.simple.VO.RelationDTO;
import com.neo4j.simple.entity.Neo4jQueryRelation;

import java.util.List;

public interface QueryRelationService {

    public List<Neo4jBasicRelationReturnVO> queryRelation(RelationDTO relationDTO);

    public List<Neo4jQueryRelation> queryRelationRelationships(RelationDTO relationDTO);
}
