package com.neo4j.simple.service;

import com.neo4j.simple.VO.Neo4jBasicRelationReturnVO;
import com.neo4j.simple.VO.RelationDTO;
import com.neo4j.simple.VO.RelationshipTypesVO;
import com.neo4j.simple.entity.Neo4jQueryRelation;

import java.util.*;

public interface QueryRelationService {

    public List<Neo4jBasicRelationReturnVO> queryRelation(RelationDTO relationDTO);

    public List<Neo4jQueryRelation> queryRelationRelationships(RelationDTO relationDTO);

    public HashMap<String, Map> searchGraphByRelation(Neo4jQueryRelation Neo4jQueryRelation);

    public HashMap<String, Map> searchGraphByRelationType(String type);

    public HashMap<String, Map> searchRelationshipsbyTypes(RelationshipTypesVO types);
}
