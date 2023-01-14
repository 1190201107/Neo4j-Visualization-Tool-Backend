package com.neo4j.simple.service.impl;


import com.neo4j.simple.service.SelectNeo4jNodeService;
import com.neo4j.simple.util.Neo4jUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SelectNeo4jNodeServiceImpl implements SelectNeo4jNodeService {
    @Resource
    Neo4jUtil neo4jUtil;

    @Override
    public void selectAllLabels(){
//        String temp = neo4jUtil.getAllLabelName().toString();
//        System.out.println(temp);
        System.out.println(neo4jUtil.getAllLabelName().toString());
    }

}
