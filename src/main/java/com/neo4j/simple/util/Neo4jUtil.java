package com.neo4j.simple.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.neo4j.simple.VO.Neo4jBasicRelationReturnVO;
import com.neo4j.simple.VO.Neo4jSaveRelationDTO;
import com.neo4j.simple.VO.RelationDTO;
import com.neo4j.simple.VO.RelationVO;
import com.neo4j.simple.common.CommonFunction;
import com.neo4j.simple.entity.Neo4jBasicNode;
import com.neo4j.simple.entity.Neo4jBasicRelation;
import com.neo4j.simple.entity.Neo4jQueryRelation;
import lombok.SneakyThrows;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.lang3.StringUtils;
import org.neo4j.driver.internal.InternalPath;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Relationship;
import org.neo4j.ogm.model.Property;
import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.response.model.NodeModel;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author 1190201107
 * Neo4J工具类
 */
@Component
public class Neo4jUtil {

    /**
     * init map序列号
     */
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false);
    }


    @SneakyThrows
    public static String propertiesMapToPropertiesStr(Map<String,Object> map) {
        map.entrySet().removeIf(entry -> CommonFunction.isEmpty(entry.getValue()));
        return mapper.writeValueAsString(map);
    }

    @Resource
    private Session session;

    public Session getSession() {
        return this.session;
    }

    /**
     * 获取所有的标签名称
     *
     * @return 返回所有的标签名称
     */
    public List<String> getAllLabelName() {
        String cypherSql = "match (n) return distinct labels(n) as name";
        Result query = session.query(cypherSql, new HashMap<>());
        ArrayList<String> labelNames = new ArrayList<>();
        for (Map<String, Object> map : query.queryResults()) {
            String[] names = (String[]) map.get("name");
            labelNames.addAll(Arrays.asList(names));

        }
        return labelNames;
    }

    /**
     * 获取所有的关系名称
     *
     * @return 返回所有的关系名称
     */
    public List<String> getAllRelationName() {
        String cypherSql = "MATCH ()-[r]-() RETURN distinct type(r) as name";
        Result query = session.query(cypherSql, new HashMap<>());
        ArrayList<String> relationNames = new ArrayList<>();
        for (Map<String, Object> map : query.queryResults()) {
            relationNames.add(map.get("name").toString());
        }
        return relationNames;
    }

    /**
     * 查找所有节点属性值
     *
     * @return 返回所有节点属性值
     */
    public List<String> getAllNodeProperty() {
        String cypherSql = "MATCH (n) RETURN distinct keys(n) as name";
        Result query = session.query(cypherSql, new HashMap<>());
        Set<String> propertyNames = new HashSet<>();
        for (Map<String, Object> map : query.queryResults()) {
            if(map.get("name") == null || map.get("name").getClass().getName().equals("[Ljava.lang.Void;")){
                continue;
            }
            String[] names = (String[]) map.get("name");
            propertyNames.addAll(Arrays.asList(names));
        }
        return new ArrayList<>(propertyNames);
    }

    /**
     * 给定property名称，查找该property的所有属性值
     *
     * @return 返回关系属性值
     */
    public <T> List<T> getAllNodePropertyValue(String propertyName) {
        String cypherSql = "MATCH (n) WHERE EXISTS(n." + propertyName +") RETURN DISTINCT n." + propertyName +" as value";
        Result query = session.query(cypherSql, new HashMap<>());
        Set<T> propertyValues = new HashSet<>();
        for (Map<String, Object> map : query.queryResults()) {
            if(map.get("value") == null || map.get("value").getClass().getName().equals("[Ljava.lang.Void;")){
                continue;
            }

            propertyValues.add((T)map.get("value"));
        }
        return new ArrayList<>(propertyValues);
    }



    /**
     * 按条件查询节点
     *
     * @param node
     * @return 返回节点集合
     */
    public List<Neo4jBasicNode> queryNode(Neo4jBasicNode node) {
        String cypherSql = "";
        if (CommonFunction.isNotEmpty(node.getId())) {
            cypherSql = String.format("MATCH (n) where id(n)=%s return n", node.getId());
        } else {
            String labels = "";
            if (CommonFunction.isNotEmpty(node.getLabels())) {
                labels = ":`" + String.join("`:`", node.getLabels()) + "`";
            }
            String property = "";
            if (CommonFunction.isNotEmpty(node.getProperties())) {
                property = Neo4jUtil.propertiesMapToPropertiesStr(node.getProperties());
            }
            cypherSql = String.format("match(n%s%s) return n", labels, property);
            System.out.println(cypherSql);
        }
        Result query = session.query(cypherSql, new HashMap<>());
        ArrayList<Neo4jBasicNode> nodeList = new ArrayList<>();
        Iterable<Map<String, Object>> maps = query.queryResults();
        for (Map<String, Object> map : maps) {
            NodeModel queryNode = (NodeModel) map.get("n");
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
        session.clear();
        return nodeList;
    }

    /**
     * 创建节点
     *
     * @param node  节点
     * @param nodup 是否去重。 true去重 false不去重
     * @return
     */
    public boolean createNode(Neo4jBasicNode node, Boolean nodup) {
        String labels = "";

        if (CommonFunction.isNotEmpty(node.getLabels())) {
            labels = ":`" + String.join("`:`", node.getLabels()) + "`";
        }
        String property = "";
        if (CommonFunction.isNotEmpty(node.getProperties())) {
            property = Neo4jUtil.propertiesMapToPropertiesStr(node.getProperties());
        }
        String cypherSql = String.format("%s(%s%s)", nodup ? "MERGE" : "create", labels, property);
        System.out.println(cypherSql);
        Result query = session.query(cypherSql, new HashMap<>());
        session.clear();
        return query.queryStatistics().getNodesCreated() > 0;
    }


    /**
     * 创建节点(不去重)
     *
     * @param node 节点
     * @param
     * @return
     */
    public boolean createNode(Neo4jBasicNode node) {
        return this.createNode(node, false);
    }

    /**
     * 更新节点，（去重增强型）
     * 创建节点，如果节点存在，先把它删除，在重新创建
     * 这个方法的目的是因为 createNode方法所谓的去重，
     * 是指如果 ，已有节点A,需要创建的节点B,如果A的属性个数大于B的属性且属性对应的值一模一样，
     * 就会创建一个新的A。所以现在的方式是对B新增A中B缺少的属性
     * @param node
     * @return
     */
    public boolean updateNode(Neo4jBasicNode node){
        System.out.println("updateNode" + node.toString());
        String addLabelCypherSql = "";
        List<String> saveLabels = node.getLabels();

        //新增标签
        List<String> addLabels = new ArrayList<>();
        for (String saveLabel : saveLabels) {
            addLabels.add(saveLabel);
        }
        String addLabelStr = addLabels.isEmpty()?"":("e:"+String.join(":",addLabels));

        //移除多余的标签
        Neo4jBasicNode queryNode = new Neo4jBasicNode();
        queryNode.setId(node.getId());
        List<Neo4jBasicNode> queryNodeList = this.queryNode(queryNode);
        if(queryNodeList.isEmpty()){
            return this.createNode(node);
        }
        Neo4jBasicNode queryNodeVo = queryNodeList.get(0);
        List<String> queryLabels = queryNodeVo.getLabels();
        //找出queryLabels中node中没有的标签
        List<String> removeLabels = new ArrayList<>();
        for (String queryLabel : queryLabels) {
            if(!saveLabels.contains(queryLabel)){
                removeLabels.add(queryLabel);
            }
        }
        String removeLabelStr = removeLabels.isEmpty()?"":("e:"+String.join(":",removeLabels));
        removeLabelStr = removeLabels.isEmpty()? "": "remove "+removeLabelStr;



        if(!(node.getProperties() == null || node.getProperties().isEmpty())){
            Map<String, Object> saveProperty = node.getProperties();
            Set<String> savePropertyKeySet = saveProperty.keySet();

            //新增属性
            HashMap<String, Object> addPropertyMap = new HashMap<>();
            for (String savePropertyKey: savePropertyKeySet) {
                addPropertyMap.put(savePropertyKey,saveProperty.get(savePropertyKey));
            }
            String addPropertyStr="";
            if(addLabels.isEmpty()){
                addPropertyStr = addPropertyMap.isEmpty()?"":("e="+ Neo4jUtil.propertiesMapToPropertiesStr(addPropertyMap));
            }else{
                addPropertyStr = addPropertyMap.isEmpty()?"":(",e="+ Neo4jUtil.propertiesMapToPropertiesStr(addPropertyMap));
            }

            //移除多余的属性
            Map<String, Object> queryProperty = queryNodeVo.getProperties();
            Set<String> queryPropertyKeySet = queryProperty.keySet();
            List<String> removeProperty = new ArrayList<>();
            for (String queryPropertyKey : queryPropertyKeySet) {
                if(!savePropertyKeySet.contains(queryPropertyKey)){
                    removeProperty.add(queryPropertyKey);
                }
            }
            //将removeProperty中的值转化为 e.removePropertyKey e.removePropertyKey2的形式
            String removePropertyStr = removeProperty.isEmpty()?"":("e."+String.join(",e.",removeProperty));
            if(removeLabels.isEmpty() && !removeProperty.isEmpty()){
                removePropertyStr = "remove "+removePropertyStr;
            }
            if(!removeProperty.isEmpty() && !removeLabels.isEmpty()){
                removeLabelStr = removeLabelStr+",";
            }

            if (StringUtils.isAllEmpty(addLabelStr, addPropertyStr, removeLabelStr, removePropertyStr)) {
                return true;
            }
            addLabelCypherSql =String.format("MERGE (e) with e where id(e)=%s set %s %s %s %s return count(e) as count", node.getId(), addLabelStr, addPropertyStr, removeLabelStr, removePropertyStr);
        }else{
            addLabelCypherSql =String.format("MERGE (e) with e where id(e)=%s set %s %s return count(e) as count", node.getId(), addLabelStr, removeLabelStr);

        }

        System.out.println(addLabelCypherSql);
        Result query = session.query(addLabelCypherSql, new HashMap<>());
        System.out.println("更新了："+node.getId());

        session.clear();

        //创建不重复节点
        return true;
    };

    /**
     * 批量创建节点(存在的节点将会被重复创建)
     *
     * @param nodeList 节点的list集合
     * @return 创建成功条数
     *
     */
    public Long batchCreateNode(List<Neo4jBasicNode> nodeList) {
        return this.batchCreateNode(nodeList, false);
    }

    /**
     * 批量创建节点
     *
     * @param nodeList 节点的list集合
     * @param nodup    是否去重。 true去重（存在的节点将不会被创建） false不去重
     * @return 创建成功条数
     */
    public Long batchCreateNode(List<Neo4jBasicNode> nodeList, Boolean nodup) {
        ArrayList<Neo4jBasicNode> addNode = new ArrayList<>();
        //去重，验证
        if (nodup){
            ArrayList<String> distinctList = new ArrayList<>();
            for (Neo4jBasicNode node : nodeList) {

                String labels = "";
                if (CommonFunction.isNotEmpty(node.getLabels())) {
                    labels = ":`" + String.join("`:`", node.getLabels()) + "`";
                }
                String property = "";
                if (CommonFunction.isNotEmpty(node.getProperties())) {
                    property = Neo4jUtil.propertiesMapToPropertiesStr(node.getProperties());
                }
                String sql = String.format("(%s%s)", labels, property);
                //对cql去重，如果有就跳过
                if (distinctList.contains(sql)) {
                    continue;
                }
                distinctList.add(sql);

                if (this.queryNode(node).size() == 0) {
                    addNode.add(node);
                }
            }
        }else {
            addNode.addAll(nodeList);
        }

        //cql拼接
        String cypherSql = "create";
        ArrayList<String> content = new ArrayList<>();
        for (Neo4jBasicNode node : addNode) {
            String labels = "";
            if (CommonFunction.isNotEmpty(node.getLabels())) {
                labels = ":`" + String.join("`:`", node.getLabels()) + "`";
            }
            String property = "";
            if (CommonFunction.isNotEmpty(node.getProperties())) {
                property = Neo4jUtil.propertiesMapToPropertiesStr(node.getProperties());
            }
            String sql = String.format("(%s%s)", labels, property);
            content.add(sql);
        }

        cypherSql += String.join(",", content);
        if (content.size() == 0) {
            return 0L;
        }
        Result query = session.query(cypherSql, new HashMap<>());
        return Long.valueOf(query.queryStatistics().getNodesCreated());
    }


    /**
     * 删除节点和相关关系
     *
     * @param node        节点条件
     * @param delRelation true 删除节点相关的关系；false 只删除不存在关系的，存在关系的节点将不会被删除关系
     * @return
     */
    public Integer delNode(Neo4jBasicNode node, boolean delRelation) {
        String cypherSql = "";
        if (CommonFunction.isNotEmpty(node.getId())) {
            cypherSql = String.format("MATCH (n) where id(n)=%s ", node.getId());
        } else {
            String labels = "";
            if (CommonFunction.isNotEmpty(node.getLabels())) {
                labels = ":`" + String.join("`:`", node.getLabels()) + "`";
            }
            String property = "";
            if (CommonFunction.isNotEmpty(node.getProperties())) {
                property = Neo4jUtil.propertiesMapToPropertiesStr(node.getProperties());
            }
            cypherSql = String.format("match(n%s%s) ", labels, property);
        }
        if (delRelation) {
            cypherSql += "DETACH DELETE n";
        } else {
            //删除不存在关系的节点
            cypherSql += " where not exists((n)-[]-()) DELETE n";

        }
        Result query = session.query(cypherSql, new HashMap<>());
        session.clear();
        return query.queryStatistics().getNodesDeleted();
    }


    /**
     * 删除节点和相关关系
     * 只删除不存在关系的，存在关系的节点将不会被删除关系
     *
     * @param node 节点条件 有关系的节点不会删除
     * @return
     */
    public Integer delNode(Neo4jBasicNode node) {
        return this.delNode(node, false);
    }

    public int queryNodeCreateRelation(Neo4jBasicNode start, Neo4jBasicNode end, Neo4jBasicRelation relation) {
        Neo4jSaveRelationDTO dto = new Neo4jSaveRelationDTO();
        dto.setStart(start);
        dto.setEnd(end);
        dto.setRelationship(relation);
        return queryNodeCreateRelation(dto);
    }

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
    public int queryNodeCreateRelation(Neo4jSaveRelationDTO saveRelation) {
        //开始节点和结束节点验证

        String cypherSql = "";
        String startLabel = "";
        if (CommonFunction.isNotEmpty(saveRelation.getStart().getLabels())) {
            startLabel = ":`" + String.join("`:`", saveRelation.getStart().getLabels()) + "`";
        }
        String startProperty = "";
        if (CommonFunction.isNotEmpty(saveRelation.getStart().getProperties())) {
            startProperty = Neo4jUtil.propertiesMapToPropertiesStr(saveRelation.getStart().getProperties());
        }

        String endLabel = "";
        if (CommonFunction.isNotEmpty(saveRelation.getEnd().getLabels())) {
            endLabel = ":`" + String.join("`:`", saveRelation.getEnd().getLabels()) + "`";
        }
        String endProperty = "";
        if (CommonFunction.isNotEmpty(saveRelation.getEnd().getProperties())) {
            endProperty = Neo4jUtil.propertiesMapToPropertiesStr(saveRelation.getEnd().getProperties());
        }
        String startWhere = "";
        if (CommonFunction.isNotEmpty(saveRelation.getStart().getId())) {
            startWhere += " where id(start)=" + saveRelation.getStart().getId();
            startLabel = "";
            startProperty = "";
        }
        String endWhere = "";
        if (CommonFunction.isNotEmpty(saveRelation.getEnd().getId())) {
            endWhere += " where id(end)=" + saveRelation.getEnd().getId();
            endLabel = "";
            endProperty = "";
        }


        String relationType = "";
        if (CommonFunction.isNotEmpty(saveRelation.getRelationship().getType())) {

            relationType = ":`" + saveRelation.getRelationship().getType() + "`";
        }
        if (CommonFunction.isEmpty(relationType)) {
            throw new RuntimeException("关系名称不能为空！");
        }
        String relationProperty = "";
        if (CommonFunction.isNotEmpty(saveRelation.getRelationship().getProperties())) {
            relationProperty = Neo4jUtil.propertiesMapToPropertiesStr(saveRelation.getRelationship().getProperties());
        }
        cypherSql = String.format("MATCH (start%s%s) %s with start MATCH (end%s%s) %s MERGE (start)-[rep%s%s]->(end)", startLabel, startProperty, startWhere, endLabel, endProperty, endWhere, relationType, relationProperty);
        System.out.println(cypherSql);
        Result query = session.query(cypherSql, new HashMap<>());
        session.clear();
        return query.queryStatistics().getRelationshipsCreated();
    }

    /**
     * 创建节点同时创建关系
     * 重复的不会被创建
     *
     * @param saveRelation
     * @return
     */
    public boolean createNodeAndRelation(Neo4jSaveRelationDTO saveRelation) {
        String cypherSql = "";
        String startLable = "";
        if (CommonFunction.isNotEmpty(saveRelation.getStart().getLabels())) {
            startLable = ":`" + String.join("`:`", saveRelation.getStart().getLabels()) + "`";
        }
        String startProperty = "";
        if (CommonFunction.isNotEmpty(saveRelation.getStart().getProperties())) {
            startProperty = Neo4jUtil.propertiesMapToPropertiesStr(saveRelation.getStart().getProperties());
        }

        String endLable = "";
        if (CommonFunction.isNotEmpty(saveRelation.getEnd().getLabels())) {
            endLable = ":`" + String.join("`:`", saveRelation.getEnd().getLabels()) + "`";
        }
        String endProperty = "";
        if (CommonFunction.isNotEmpty(saveRelation.getEnd().getProperties())) {
            endProperty = Neo4jUtil.propertiesMapToPropertiesStr(saveRelation.getEnd().getProperties());
        }

        String relationType = "";
        if (CommonFunction.isNotEmpty(saveRelation.getRelationship().getType())) {
            relationType = ":`" + saveRelation.getRelationship().getType() + "`";
        }
        if (CommonFunction.isEmpty(relationType)) {
            throw new RuntimeException("关系名称不能为空！");
        }
        String relationProperty = "";
        if (CommonFunction.isNotEmpty(saveRelation.getRelationship().getProperties())) {
            relationProperty = Neo4jUtil.propertiesMapToPropertiesStr(saveRelation.getRelationship().getProperties());
        }
        cypherSql = String.format("MERGE (start%s%s)-[rep%s%s]->(end%s%s)", startLable, startProperty, relationType, relationProperty, endLable, endProperty);
        Result query = session.query(cypherSql, new HashMap<>());
        session.clear();
        return query.queryStatistics().getRelationshipsCreated() > 0;

    }

    /**
     * 查询关系
     *
     * @param relationDTO
     * @return
     */
    public List<Neo4jBasicRelationReturnVO> queryRelation(RelationDTO relationDTO) {
        RelationVO relationVO = formatRelation(relationDTO);

        //拼接sql
        String cypherSql = String.format("MATCH p=(a%s%s)-[r%s%s]->(b%s%s)-[*0..%s]->() RETURN p", relationVO.getStartLabelName(), relationVO.getStartNodeProperties(), relationVO.getRelationLabelName(), relationVO.getRelationProperties(), relationVO.getEndLabelName(), relationVO.getEndNodeProperties(), relationVO.getLevel());
        System.out.println(cypherSql);
        long startTime = System.currentTimeMillis();
        Result query = session.query(cypherSql, new HashMap<>());
        System.out.println(String.format("耗时%d秒", System.currentTimeMillis() - startTime));

        Iterable<Map<String, Object>> maps = query.queryResults();
        ArrayList<Neo4jBasicRelationReturnVO> returnList = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            InternalPath.SelfContainedSegment[] ps = (InternalPath.SelfContainedSegment[]) map.get("p");
            for (InternalPath.SelfContainedSegment p : ps) {
                returnList.add(changeToNeo4jBasicRelationReturnVO(p));
            }
        }
        session.clear();
        return returnList;
    }
    /**
     * 查询边
     *
     * @param relationDTO
     * @return
     */
    public List<Neo4jQueryRelation> queryRelationRelationships(RelationDTO relationDTO) {
        RelationVO relationVO = formatRelation(relationDTO);

        //拼接sql
        String cypherSql = String.format("MATCH p=(a%s%s)-[r%s%s]->(b%s%s)-[*0..%s]->() RETURN p", relationVO.getStartLabelName(), relationVO.getStartNodeProperties(), relationVO.getRelationLabelName(), relationVO.getRelationProperties(), relationVO.getEndLabelName(), relationVO.getEndNodeProperties(), relationVO.getLevel());
        System.out.println(cypherSql);
        long startTime = System.currentTimeMillis();
        Result query = session.query(cypherSql, new HashMap<>());
        System.out.println(String.format("耗时%d秒", System.currentTimeMillis() - startTime));

        Iterable<Map<String, Object>> maps = query.queryResults();
        ArrayList<Neo4jQueryRelation> returnList = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            InternalPath.SelfContainedSegment[] ps = (InternalPath.SelfContainedSegment[]) map.get("p");
            for (InternalPath.SelfContainedSegment p : ps) {
                returnList.add(changeToNeo4jBasicRelationReturnVOOnlyRelation(p));
            }
        }
        session.clear();
        return returnList;
    }
    /**
     * 格式化
     *
     * @param relationDTO
     * @return
     */
    public RelationVO formatRelation(RelationDTO relationDTO) {
        RelationVO relationVO = new RelationVO();

        //验证

        if (CommonFunction.isNotEmpty(relationDTO.getRelationLabelName())) {
            relationVO.setRelationLabelName(":`" + relationDTO.getRelationLabelName()+"`");
        } else {
            relationVO.setRelationLabelName("");
        }
        if (CommonFunction.isNotEmpty(relationDTO.getStartLabelName())) {
            relationVO.setStartLabelName(":`" + relationDTO.getStartLabelName()+"`");
        } else {
            relationVO.setStartLabelName("");
        }
        if (CommonFunction.isNotEmpty(relationDTO.getEndLabelName())) {
            relationVO.setEndLabelName(":`" + relationDTO.getEndLabelName()+"`");
        } else {
            relationVO.setEndLabelName("");
        }
        if (CommonFunction.isNotEmpty(relationDTO.getStartNodeProperties())) {
            relationVO.setStartNodeProperties(Neo4jUtil.propertiesMapToPropertiesStr(relationDTO.getStartNodeProperties()));
        } else {
            relationVO.setStartNodeProperties("");
        }

        if (CommonFunction.isNotEmpty(relationDTO.getRelationProperties())) {
            relationVO.setRelationProperties(Neo4jUtil.propertiesMapToPropertiesStr(relationDTO.getRelationProperties()));
        } else {
            relationVO.setRelationProperties("");
        }

        if (CommonFunction.isNotEmpty(relationDTO.getEndNodeProperties())) {
            relationVO.setEndNodeProperties(Neo4jUtil.propertiesMapToPropertiesStr(relationDTO.getEndNodeProperties()));
        } else {
            relationVO.setEndNodeProperties("");
        }
        if (CommonFunction.isNotEmpty(relationDTO.getLevel())) {
            relationVO.setLevel(relationDTO.getLevel());
        } else {
            relationVO.setLevel("");
        }

        return relationVO;
    }

    /**
     * 转化neo4j默认查询的参数为自定返回类型(只包含relationships)
     *
     * @param selfContainedSegment
     * @return Neo4jQueryRelation
     */
    public Neo4jQueryRelation changeToNeo4jBasicRelationReturnVOOnlyRelation(InternalPath.SelfContainedSegment selfContainedSegment) {
        //relationship
        Neo4jQueryRelation neo4JQueryRelation = new Neo4jQueryRelation();
        Relationship relationship = selfContainedSegment.relationship();
        neo4JQueryRelation.setStartNode(relationship.startNodeId());
        neo4JQueryRelation.setEndNode(relationship.endNodeId());
        neo4JQueryRelation.setId(relationship.id());
        neo4JQueryRelation.setType(relationship.type());
        neo4JQueryRelation.setProperties(relationship.asMap());
        return neo4JQueryRelation;
    }
    /**
     * 转化neo4j默认查询的参数为自定返回类型
     *
     * @param selfContainedSegment
     * @return Neo4jBasicRelationReturn
     */
    public Neo4jBasicRelationReturnVO changeToNeo4jBasicRelationReturnVO(InternalPath.SelfContainedSegment selfContainedSegment) {
        Neo4jBasicRelationReturnVO neo4JBasicRelationReturnVO = new Neo4jBasicRelationReturnVO();
        //start
        Node start = selfContainedSegment.start();
        Neo4jBasicNode startNodeVo = new Neo4jBasicNode();
        startNodeVo.setId(start.id());
        startNodeVo.setLabels(IteratorUtils.toList(start.labels().iterator()));
        startNodeVo.setProperties(start.asMap());
        neo4JBasicRelationReturnVO.setStart(startNodeVo);
        //end
        Node end = selfContainedSegment.end();
        Neo4jBasicNode endNodeVo = new Neo4jBasicNode();
        endNodeVo.setId(end.id());
        endNodeVo.setLabels(IteratorUtils.toList(end.labels().iterator()));
        endNodeVo.setProperties(end.asMap());
        neo4JBasicRelationReturnVO.setEnd(endNodeVo);
        //relationship
        Neo4jQueryRelation neo4JQueryRelation = new Neo4jQueryRelation();
        Relationship relationship = selfContainedSegment.relationship();
        neo4JQueryRelation.setStartNode(relationship.startNodeId());
        neo4JQueryRelation.setEndNode(relationship.endNodeId());
        neo4JQueryRelation.setId(relationship.id());
        neo4JQueryRelation.setType(relationship.type());
        neo4JQueryRelation.setProperties(relationship.asMap());
        neo4JBasicRelationReturnVO.setRelationship(neo4JQueryRelation);
        return neo4JBasicRelationReturnVO;
    }


}