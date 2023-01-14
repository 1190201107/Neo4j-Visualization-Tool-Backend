package com.neo4j.simple.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


@Data
public class Neo4jBasicNode implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Long id;

    /**
     * 标签
     */
    private List<String> labels;

    /**
     * 标签属性
     */
    private Map<String, Object> property;


}