package com.neo4j.simple.service.impl;

import com.neo4j.simple.entity.Neo4jBasicNode;
import com.neo4j.simple.service.CreateNodesService;
import com.neo4j.simple.util.Neo4jUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lgz
 * @e-mail 2821626468@qq.com
 * @create 2023-03-05 15:25
 */
@Service
public class CreateNodesServiceImpl implements CreateNodesService {
    private final Neo4jTransactionManager neo4jTransactionManager;

    public CreateNodesServiceImpl(@Qualifier("neo4jTransaction") Neo4jTransactionManager neo4jTransactionManager) {
        this.neo4jTransactionManager = neo4jTransactionManager;
    }

    @Resource
    private Neo4jUtil neo4jUtil;

    /**
     * 创建节点(不去重)
     * @param neo4jBasicNode
     * @return 是否创建成功
     */
    @Override
    @Transactional(transactionManager = "neo4jTransaction")
    public boolean createNode(Neo4jBasicNode neo4jBasicNode) {
        return neo4jUtil.createNode(neo4jBasicNode);
    }

    /**
     * 创建节点(去重,如果存在则不创建)
     * @param neo4jBasicNode
     * @return 是否创建成功
     */
    @Override
    @Transactional(transactionManager = "neo4jTransaction")
    public boolean createNodeNoRepeat(Neo4jBasicNode neo4jBasicNode) {
        return neo4jUtil.createNode(neo4jBasicNode, true);
    }

    /**
     * 创建节点(去重,如果存在则重新创建)
     * @param neo4jBasicNode
     * @return 是否创建成功
     */
    @Override
    @Transactional(transactionManager = "neo4jTransaction")
    public boolean recreateNodeNoRepeat(Neo4jBasicNode neo4jBasicNode) {
        return neo4jUtil.updateNode(neo4jBasicNode);
    }
    /**
     * 批量创建节点(不去重)
     * @param neo4jBasicNodes
     * @return 创建成功的节点数量
     */
    @Override
    @Transactional(transactionManager = "neo4jTransaction")
    public Long batchCreateNode(List<Neo4jBasicNode> neo4jBasicNodes) {
        return neo4jUtil.batchCreateNode(neo4jBasicNodes);
    }

    /**
     * 批量创建节点(去重,如果存在则重新创建)
     * @param neo4jBasicNodes
     * @return 创建成功的节点数量
     */
    @Override
    @Transactional(transactionManager = "neo4jTransaction")
    public Long batchCreateNodeNoRepeat(List<Neo4jBasicNode> neo4jBasicNodes) {
        return neo4jUtil.batchCreateNode(neo4jBasicNodes, true);
    }


}
