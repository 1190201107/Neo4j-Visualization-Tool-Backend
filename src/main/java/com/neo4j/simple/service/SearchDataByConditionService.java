package com.neo4j.simple.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SearchDataByConditionService {
    public HashMap<String, Map> searchDataByLabel(String label);

    public HashMap<String, Map> searchDataByProperty(String property);

    public <T> HashMap<String, Map> searchDataByLabelsAndProperties(Map<String, List<T>> labelsAndProperties);

}
