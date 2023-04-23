package com.neo4j.simple.VO;

import lombok.Data;

import java.io.Serializable;

@Data
public class FormatLabelsCount implements Serializable {
    /**
     * 标签
     *
     */
    public String label;

    /**
     * 标签数量
     *
     */
    public int count;

    public FormatLabelsCount(String label, int count) {
        this.label = label;
        this.count = count;
    }
}
