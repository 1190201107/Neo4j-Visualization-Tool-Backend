package com.neo4j.simple.service.impl;

import com.neo4j.simple.entity.Neo4jBasicNode;
import com.neo4j.simple.entity.Neo4jQueryRelation;
import com.neo4j.simple.service.ImportNodesService;
import com.neo4j.simple.util.Neo4jUtil;
import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lgz
 * @e-mail 2821626468@qq.com
 * @create 2023-03-05 15:24
 */
@Service
public class ImportNodesServiceImpl implements ImportNodesService {

    @Resource
    private Neo4jUtil neo4jUtil;

    @Resource
    private Session session;


    /**
     * 批量添加节点
     * @param nodes
     * @return
     */
    @Override
//    @Transactional(transactionManager = "neo4jTransaction")
    public Long batchAddNodes(List<Neo4jBasicNode> nodes) {
        return neo4jUtil.batchCreateNode(nodes, false);
    }

    /**
     * 批量添加关系
     * @param relations
     * @return
     */
    @Override
//    @Transactional(transactionManager = "neo4jTransaction")
    public int batchAddRelations(List<Neo4jQueryRelation> relations) {
        int result = 0;
        for(Neo4jQueryRelation relation : relations){
            try{
                Long startNode = relation.getStartNode();
                Long endNode = relation.getEndNode();
                String cypherSql = String.format("MATCH (n) WHERE id(n) = %s MATCH (m) WHERE id(m) = %s ", startNode, endNode);
                Map<String, Object> properties = relation.getProperties();
                String propertiesStr = "{";
                boolean isFirst = true;
                for(String key : properties.keySet()){
                    if(isFirst){
                        isFirst = false;
                    }else{
                        propertiesStr += ",";
                    }
                    propertiesStr += String.format("%s:%s", key, properties.get(key));

                }
                propertiesStr += "}";
                String relationCql = String.format("CREATE (n)-[r:%s %s]->(m) RETURN r", relation.getType(), propertiesStr);
                cypherSql += relationCql;
                Result query = session.query(cypherSql, new HashMap<>());
                result++;
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return result;
    }
}
