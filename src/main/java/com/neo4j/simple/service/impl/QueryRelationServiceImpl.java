package com.neo4j.simple.service.impl;

import com.neo4j.simple.VO.Neo4jBasicRelationReturnVO;
import com.neo4j.simple.VO.RelationDTO;
import com.neo4j.simple.entity.Neo4jQueryRelation;
import com.neo4j.simple.service.QueryRelationService;
import com.neo4j.simple.util.Neo4jUtil;

import javax.annotation.Resource;
import java.util.List;

public class QueryRelationServiceImpl implements QueryRelationService {
    @Resource
    private Neo4jUtil neo4jUtil;

    @Override
    public List<Neo4jBasicRelationReturnVO> queryRelation(RelationDTO relationDTO) {
        return neo4jUtil.queryRelation(relationDTO);
    }

    @Override
    public List<Neo4jQueryRelation> queryRelationRelationships(RelationDTO relationDTO) {
        return neo4jUtil.queryRelationRelationships(relationDTO);
    }
}
