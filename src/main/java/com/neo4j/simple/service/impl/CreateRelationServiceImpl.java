package com.neo4j.simple.service.impl;

import com.neo4j.simple.VO.DeleteRelationVO;
import com.neo4j.simple.VO.Neo4jSaveRelationDTO;
import com.neo4j.simple.service.CreateRelationService;
import com.neo4j.simple.util.Neo4jUtil;
import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
public class CreateRelationServiceImpl implements CreateRelationService {
    @Resource
    private Neo4jUtil neo4jUtil;

    @Resource
    private Session session;

    /**
     * 查询节点然后创建关系
     * 创建关系(查询开始节点和结束节点然后创造关系)
     * 注意：开始节点和结束节点以及创建的关系参数一定要存在！
     * 关系如果存在，不会重复创建
     * 因为需要返回创建条数 当前方法未做条件判断
     *
     * @param saveRelation 关系构造类
     * @return 返回创建关系的个数
     */
    @Override
//    @Transactional(transactionManager = "neo4jTransaction")
    public int queryNodeCreateRelation(Neo4jSaveRelationDTO saveRelation) {
        return neo4jUtil.queryNodeCreateRelation(saveRelation);
    }

    /**
     * 创建节点同时创建关系
     * 重复的不会被创建
     * @param saveRelation
     * @return 是否创建成功
     */
    @Override
//    @Transactional(transactionManager = "neo4jTransaction")
    public boolean createNodeAndRelation(Neo4jSaveRelationDTO saveRelation) {
        return neo4jUtil.createNodeAndRelation(saveRelation);
    }

    @Override
    public boolean deleteRelation(DeleteRelationVO deleteRelationVO) {
        try{
            String cypherSql = "match (n)-[r]->(m) where id(n) = " + deleteRelationVO.getStart().getId() + " and id(m) = " + deleteRelationVO.getEnd().getId() + " delete r";
            System.out.println(cypherSql);
            Result query = session.query(cypherSql, new HashMap<>());
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
