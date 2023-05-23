package com.neo4j.simple.service.impl;


import com.neo4j.simple.service.CreateIndexService;
import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Session;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
public class CreateIndexServiceImpl implements CreateIndexService {

    @Resource
    Session session;
    @Override
    public boolean createIndex(String label, String property) {
        try{
            String cypherSql = String.format("CREATE INDEX ON :%s(%s)", label, property);
            System.out.println(cypherSql);
            Result query = session.query(cypherSql, new HashMap<>());
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

}
