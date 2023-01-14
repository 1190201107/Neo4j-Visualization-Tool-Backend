package com.neo4j.simple.VO;

import lombok.Data;

import java.util.Map;

@Data
public class RelationDTO {
    public String relationLabelName;

    public String startLabelName;

    public String endLabelName;

    public Map<String, Object> startNodeProperties;

    public Map<String, Object> relationProperties;

    public Map<String, Object> endNodeProperties;

    public String level;


}
