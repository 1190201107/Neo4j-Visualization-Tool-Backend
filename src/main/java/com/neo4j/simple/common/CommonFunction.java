package com.neo4j.simple.common;

import com.neo4j.simple.VO.FormatLabelsCount;
import com.neo4j.simple.VO.FormatTypesCount;
import com.neo4j.simple.entity.Neo4jBasicNode;
import com.neo4j.simple.entity.Neo4jQueryRelation;
import org.neo4j.register.Register;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonFunction {

    public static boolean isEmpty(Object label){
        return label == null;
    }

    public static boolean isNotEmpty(Object label){
        return label != null;
    }

    public static HashMap<String, Map> getResultMapLabelAndTypeCount(HashMap<String, Map> result){
        if (result != null && result.size() > 0){
            Map<String, List> graph = result.get("graph");
            List<Neo4jBasicNode> nodes = graph.get("nodes");
            List<Neo4jQueryRelation> relationships = graph.get("relationships");
            HashMap<String, List> resultMap = new HashMap<>();

            List<FormatLabelsCount> labelsCount = new ArrayList<>();
            Map<String, Integer> labelsIndexMap = new HashMap<>();
            for (Neo4jBasicNode node : nodes) {
                List<String> labels = node.getLabels();
                for (String label : labels) {
                    if(labelsIndexMap.containsKey(label)){
                        int index = labelsIndexMap.get(label);
                        FormatLabelsCount formatLabelsCount = labelsCount.get(index);
                        formatLabelsCount.setCount(formatLabelsCount.getCount() + 1);
                    }else {
                        FormatLabelsCount formatLabelsCount = new FormatLabelsCount(label, 1);
                        labelsCount.add(formatLabelsCount);
                        labelsIndexMap.put(label, labelsCount.size() - 1);
                    }
                }
            }
            resultMap.put("labelsCount", labelsCount);

            List<FormatTypesCount> typesCount = new ArrayList<>();
            Map<String, Integer> typesIndexMap = new HashMap<>();
            for (Neo4jQueryRelation relationship : relationships) {
                String type = relationship.getType();
                if(typesIndexMap.containsKey(type)){
                    int index = typesIndexMap.get(type);
                    FormatTypesCount formatTypesCount = typesCount.get(index);
                    formatTypesCount.setCount(formatTypesCount.getCount() + 1);
                }else {
                    FormatTypesCount formatTypesCount = new FormatTypesCount(type, 1);
                    typesCount.add(formatTypesCount);
                    typesIndexMap.put(type, typesCount.size() - 1);
                }
            }
            resultMap.put("typesCount", typesCount);
            result.put("countMessage", resultMap);
            return result;
        }
        return null;
    }
}
