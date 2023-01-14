package com.neo4j.simple.VO;

import com.neo4j.simple.entity.Neo4jBasicNode;
import com.neo4j.simple.entity.Neo4jBasicRelation;
import lombok.Data;

@Data
public class Neo4jSaveRelationDTO {
    /**
     * 开始节点id
     */
    private Neo4jBasicNode start;
    /**
     * 结束节点id
     */
    private Neo4jBasicNode end;
    /**
     * 关系类型
     */
    private Neo4jBasicRelation relationship;
}
