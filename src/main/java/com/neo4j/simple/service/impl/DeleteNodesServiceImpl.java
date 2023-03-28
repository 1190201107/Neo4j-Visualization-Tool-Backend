package com.neo4j.simple.service.impl;

import com.neo4j.simple.entity.Neo4jBasicNode;
import com.neo4j.simple.service.DeleteNodesService;
import com.neo4j.simple.util.Neo4jUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lgz
 * @e-mail 2821626468@qq.com
 * @create 2023-03-05 15:25
 */
@Service
public class DeleteNodesServiceImpl implements DeleteNodesService {
    @Resource
    private Neo4jUtil neo4jUtil;

    /**
     * 删除节点(包括关系, 会删除所有与节点相关的关系)
     * @param neo4jBasicNode
     * @return 删除的节点数量
     */
    @Override
    public Integer deleteNode(Neo4jBasicNode neo4jBasicNode) {
        return neo4jUtil.delNode(neo4jBasicNode, true);
    }

    /**
     * 删除节点(不包括关系, 只删除不存在关系的，存在关系的节点将不会被删除关系)
     * @param neo4jBasicNode
     * @return 删除的节点数量
     */
    @Override
    public Integer deleteNodeNoRelation(Neo4jBasicNode neo4jBasicNode) {
        return neo4jUtil.delNode(neo4jBasicNode);
    }
}
