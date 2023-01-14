package com.neo4j.simple.VO;

import lombok.Data;

/**
 * 将关系的实体类，转换换成cypherSql需要字符串类型的vo
 * Util里面会用到
 */
@Data
public class RelationVO {
    /**
     * 关系名称
     */
    private String relationLabelName;
    /**
     * 开始标签名称
     */
    private String startLabelName;

    /**
     * 开始节点条件
     */
    private String startNodeProperties;
    /**
     * 关系的属性
     */
    private String relationProperties;
    /**
     * 结束节点条件
     */
    private String endNodeProperties;
    /**
     * 结束标签名称
     */
    private String endLabelName;
    /**
     * 查询层级
     */
    private String level;
}