package com.neo4j.simple.controller;

import com.alibaba.fastjson.JSONObject;
import com.neo4j.simple.service.ExportNodesService;
import com.neo4j.simple.service.SearchDataByConditionService;
import com.neo4j.simple.service.SelectNeo4jNodeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lgz
 * @e-mail 2821626468@qq.com
 * @create 2023-03-05 15:19
 */
@RestController
public class ExportNodesController {
    @Resource
    private SearchDataByConditionService searchDataByConditionService;

    @Resource
    private SelectNeo4jNodeService selectNeo4jNodeService;

    @PostMapping("/exportNodesAndRelationToJson")
    public <T> void downloadFile(@RequestBody Map<String, List<T>> labelsAndProperties , HttpServletResponse response) throws IOException {
        HashMap<String, Map> data = searchDataByConditionService.searchDataByLabelsAndProperties(labelsAndProperties, false);
        String filename = "data.json";
        String jsonData = JSONObject.toJSONString(data);

        response.setHeader("Content-Disposition", "attachment; filename=" + filename);
        response.setContentType("application/json");
        response.getOutputStream().write(jsonData.getBytes());
        response.flushBuffer();
    }

    @GetMapping("/exportAllDataToJson")
    public <T> void downloadFile(HttpServletResponse response) throws IOException {
        HashMap<String, Map> data = selectNeo4jNodeService.selectAllGraph(false);
        String filename = "All data.json";
        String jsonData = JSONObject.toJSONString(data);
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);
        response.setContentType("application/json");
        response.getOutputStream().write(jsonData.getBytes());
        response.flushBuffer();
    }




}
