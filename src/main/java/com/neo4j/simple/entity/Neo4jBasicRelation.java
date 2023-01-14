package com.neo4j.simple.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 关系
 */
@Data
public class Neo4jBasicRelation implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Long id;

    /**
     * 标签
     */
    private String type;

    /**
     * 标签属性
     */
    private Map<String, Object> property;
}