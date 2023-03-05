package com.neo4j.simple.service.impl;

import com.neo4j.simple.service.ImportNodesService;
import com.neo4j.simple.util.ExplainExcelDataUtil;
import com.neo4j.simple.util.Neo4jUtil;

import javax.annotation.Resource;

/**
 * @author lgz
 * @e-mail 2821626468@qq.com
 * @create 2023-03-05 15:24
 */
public class ImportNodesServiceImpl implements ImportNodesService {

    @Resource
    private ExplainExcelDataUtil explainExcelDataUtil;

    @Resource
    private Neo4jUtil neo4jUtil;
}
