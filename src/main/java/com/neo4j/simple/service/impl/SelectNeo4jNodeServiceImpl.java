package com.neo4j.simple.service.impl;


import com.neo4j.simple.VO.Neo4jBasicRelationReturnVO;
import com.neo4j.simple.VO.RelationDTO;
import com.neo4j.simple.common.CommonFunction;
import com.neo4j.simple.entity.Neo4jBasicNode;
import com.neo4j.simple.entity.Neo4jQueryRelation;
import com.neo4j.simple.service.SelectNeo4jNodeService;
import com.neo4j.simple.util.Neo4jUtil;
import org.neo4j.ogm.model.Property;
import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.response.model.NodeModel;
import org.neo4j.ogm.response.model.RelationshipModel;
import org.neo4j.ogm.session.Session;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.* ;

@Service
public class SelectNeo4jNodeServiceImpl implements SelectNeo4jNodeService {
    @Resource
    private Neo4jUtil neo4jUtil;

    @Resource
    private Session session;

    @Override
    public List<String> selectAllLabels(){
        List<String> allLabelName = neo4jUtil.getAllLabelName();
        //对allLabelName内的字符串进行去重
        Set<String> set = new HashSet<>(allLabelName);
        //将set转换为list
        allLabelName = new ArrayList<>(set);
        return allLabelName;
    }

    @Override
    public List<String> selectAllRelationName() {
        List<String> allRelationName = neo4jUtil.getAllRelationName();
        return allRelationName;
    }

    @Override
    public List<String> selectAllPropertiesName() {
        List<String> allPropertiesName = neo4jUtil.getAllNodeProperty();
        return allPropertiesName;
    }

    //查询neo4j数据库中所有properties中对应的值
    @Override
    public <T> Map<String, List<T>> selectAllPropertiesValue() {
        List<String> allPropertiesName = neo4jUtil.getAllNodeProperty();
        Map<String, List<T>> allPropertiesValue = new HashMap<>();
        for (String propertyName : allPropertiesName) {
            List<T> propertyValue = neo4jUtil.getAllNodePropertyValue(propertyName);
            allPropertiesValue.put(propertyName, propertyValue);
        }
        return allPropertiesValue;
    }


    @Override
    public HashMap<String, Map> selectAllGraph() {
        Neo4jBasicNode neo4jBasicNode = new Neo4jBasicNode();
        List<Neo4jBasicNode> neo4jBasicNodes = neo4jUtil.queryNode(neo4jBasicNode);
        RelationDTO relationDTO = new RelationDTO();
        List<Neo4jQueryRelation> neo4jQueryRelationS = neo4jUtil.queryRelationRelationships(relationDTO);
        HashMap<String, List> temp = new HashMap<>();
        temp.put("nodes", neo4jBasicNodes);
        temp.put("relationships", neo4jQueryRelationS);
        HashMap<String, Map> graph = new HashMap<>();
        graph.put("graph", temp);
        return CommonFunction.getResultMapLabelAndTypeCount(graph);
    }

    @Override
    public HashMap<String, Map> selectNode(Neo4jBasicNode node) {
        String cypherSql = "";
        String labels = "";
        if (CommonFunction.isNotEmpty(node.getLabels())) {
            labels = ":`" + String.join("`:`", node.getLabels()) + "`";
        }
        String property = "";
        if (CommonFunction.isNotEmpty(node.getProperties())) {
            property = Neo4jUtil.propertiesMapToPropertiesStr(node.getProperties());
        }
        cypherSql = String.format("match(n%s%s)-[r]-(m) return n, r, m", labels, property);
        System.out.println(cypherSql);

        Result query = session.query(cypherSql, new HashMap<>());
        List<Neo4jBasicNode> nodeList = new ArrayList<>();
        List<Neo4jQueryRelation> relationsList = new ArrayList<>();

        //用于去重
        List<Long> nodeIdList = new ArrayList<>();

        Iterable<Map<String, Object>> maps = query.queryResults();
        for (Map<String, Object> map : maps) {
            //get node
            NodeModel queryNode = (NodeModel) map.get("n");
            if(!nodeIdList.contains(queryNode.getId())){
                nodeIdList.add(queryNode.getId());
                Neo4jBasicNode startNodeVo = new Neo4jBasicNode();
                startNodeVo.setId(queryNode.getId());
                startNodeVo.setLabels(Arrays.asList(queryNode.getLabels()));
                List<Property<String, Object>> propertyList = queryNode.getPropertyList();
                HashMap<String, Object> proMap = new HashMap<>();
                for (Property<String, Object> stringObjectProperty : propertyList) {
                    if (proMap.containsKey(stringObjectProperty.getKey())) {
                        throw new RuntimeException("数据重复");
                    }
                    proMap.put(stringObjectProperty.getKey(), stringObjectProperty.getValue());
                }
                startNodeVo.setProperties(proMap);
                nodeList.add(startNodeVo);
            }
            NodeModel queryNode2 = (NodeModel) map.get("m");
            if(!nodeIdList.contains(queryNode2.getId())){
                nodeIdList.add(queryNode2.getId());
                Neo4jBasicNode endNodeVo = new Neo4jBasicNode();
                endNodeVo.setId(queryNode2.getId());
                endNodeVo.setLabels(Arrays.asList(queryNode2.getLabels()));
                List<Property<String, Object>> propertyList = queryNode2.getPropertyList();
                HashMap<String, Object> proMap = new HashMap<>();
                for (Property<String, Object> stringObjectProperty : propertyList) {
                    if (proMap.containsKey(stringObjectProperty.getKey())) {
                        throw new RuntimeException("数据重复");
                    }
                    proMap.put(stringObjectProperty.getKey(), stringObjectProperty.getValue());
                }
                endNodeVo.setProperties(proMap);
                nodeList.add(endNodeVo);
            }
            //get relation
            RelationshipModel queryRelation = (RelationshipModel) map.get("r");
            Neo4jQueryRelation neo4jQueryRelation = new Neo4jQueryRelation();
            neo4jQueryRelation.setId(queryRelation.getId());
            neo4jQueryRelation.setStartNode(queryRelation.getStartNode());
            neo4jQueryRelation.setEndNode(queryRelation.getEndNode());
            neo4jQueryRelation.setType(queryRelation.getType());
            List<Property<String, Object>> propertyList2 = queryRelation.getPropertyList();
            HashMap<String, Object> tranMap = new HashMap<>();
            for (Property<String, Object> stringObjectProperty : propertyList2) {
                if (tranMap.containsKey(stringObjectProperty.getKey())) {
                    throw new RuntimeException("数据重复");
                }
                tranMap.put(stringObjectProperty.getKey(), stringObjectProperty.getValue());
            }
            neo4jQueryRelation.setProperties(tranMap);
            relationsList.add(neo4jQueryRelation);
        }

        HashMap<String, List> temp = new HashMap<>();
        if(nodeList.size() == 0){
            temp.put("nodes", neo4jUtil.queryNode(node));
        }else{
            temp.put("nodes", nodeList);
        }
        temp.put("relationships", relationsList);
        HashMap<String, Map> graph = new HashMap<>();
        graph.put("graph", temp);
        return CommonFunction.getResultMapLabelAndTypeCount(graph);
    }

}
