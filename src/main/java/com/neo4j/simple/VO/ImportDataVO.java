package com.neo4j.simple.VO;

import com.neo4j.simple.entity.Neo4jBasicNode;
import com.neo4j.simple.entity.Neo4jQueryRelation;
import lombok.Data;

import java.util.*;

@Data
public class ImportDataVO {

    List<Neo4jBasicNode> nodes;

    List<Neo4jQueryRelation> relationships;
}
