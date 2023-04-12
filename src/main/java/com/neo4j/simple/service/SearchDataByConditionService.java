package com.neo4j.simple.service;

import java.util.HashMap;
import java.util.Map;

public interface SearchDataByConditionService {
    public HashMap<String, Map> searchDataByLabel(String label);

    public HashMap<String, Map> searchDataByProperty(String property);

}
