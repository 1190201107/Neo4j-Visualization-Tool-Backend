package com.neo4j.simple.service.impl;

import com.neo4j.simple.entity.Neo4jBasicNode;
import com.neo4j.simple.service.ExportNodesService;
import com.neo4j.simple.util.JsonWriter;
import com.neo4j.simple.util.Neo4jBasicNodeExcelWriter;
import com.neo4j.simple.util.Neo4jUtil;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lgz
 * @e-mail 2821626468@qq.com
 * @create 2023-03-05 15:25
 */
public class ExportNodesServiceImpl implements ExportNodesService {

    @Override
    public boolean exportNodesToExcel(List<Neo4jBasicNode> nodes) {
        //todo 应该返回文件流，之后更改
        try {
            Neo4jBasicNodeExcelWriter.writeExcel(nodes, "D:\\test.xlsx");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean exportNodesToJson(List<Neo4jBasicNode> nodes) {
        //todo 应该返回文件流，之后更改
        try {
            JsonWriter.writeToJson(nodes, "D:\\test.json");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
