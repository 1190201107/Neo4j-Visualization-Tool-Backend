package com.neo4j.simple.VO;

import lombok.Data;

import java.io.Serializable;

@Data
public class FormatTypesCount implements Serializable {
    /**
     * 关系类型
     *
     */
    String type;

    /**
     * 关系类型数量
     *
     */
    int count;

    public FormatTypesCount(String type, int count) {
        this.type = type;
        this.count = count;
    }
}
