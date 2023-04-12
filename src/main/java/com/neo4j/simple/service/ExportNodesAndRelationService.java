package com.neo4j.simple.service;

import com.neo4j.simple.VO.Neo4jBasicRelationReturnVO;

import java.util.List;

public interface ExportNodesAndRelationService {

    public boolean exportNodesAndRelationToExcel(List<Neo4jBasicRelationReturnVO> data);

    public boolean exportNodesAndRelationToJson(List<Neo4jBasicRelationReturnVO> data);
}
