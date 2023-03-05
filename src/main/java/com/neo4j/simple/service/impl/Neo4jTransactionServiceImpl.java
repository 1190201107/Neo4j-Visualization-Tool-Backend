package com.neo4j.simple.service.impl;

import com.neo4j.simple.config.Neo4jConfig;
import com.neo4j.simple.service.Neo4jTransactionService;

import javax.annotation.Resource;

/**
 * @author lgz
 * @e-mail 2821626468@qq.com
 * @create 2023-03-05 15:37
 */
public class Neo4jTransactionServiceImpl implements Neo4jTransactionService {
    @Resource
    private Neo4jConfig neo4jConfig;
}
