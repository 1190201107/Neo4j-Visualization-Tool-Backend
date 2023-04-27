package com.neo4j.simple.VO;

import com.neo4j.simple.entity.Neo4jBasicNode;
import lombok.Data;

import javax.annotation.Resource;

@Data
public class DeleteRelationVO {

    public Neo4jBasicNode start;
    public Neo4jBasicNode end;
}
