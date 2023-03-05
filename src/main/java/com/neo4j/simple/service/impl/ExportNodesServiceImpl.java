package com.neo4j.simple.service.impl;

import com.neo4j.simple.service.ExportNodesService;
import com.neo4j.simple.util.DataToJsonFileUtil;
import com.neo4j.simple.util.Neo4jUtil;

import javax.annotation.Resource;

/**
 * @author lgz
 * @e-mail 2821626468@qq.com
 * @create 2023-03-05 15:25
 */
public class ExportNodesServiceImpl implements ExportNodesService {
    @Resource
    private DataToJsonFileUtil dataToJsonFileUtil;

    @Resource
    private Neo4jUtil neo4jUtil;
}
