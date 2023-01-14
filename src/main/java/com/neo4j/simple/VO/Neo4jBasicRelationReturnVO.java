package com.neo4j.simple.VO;


import com.neo4j.simple.entity.Neo4jBasicNode;
import com.neo4j.simple.entity.Neo4jQueryRelation;
import lombok.Data;

import java.io.Serializable;


@Data
public class Neo4jBasicRelationReturnVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Neo4jBasicNode start;
    private Neo4jQueryRelation relationship;
    private Neo4jBasicNode end;
}