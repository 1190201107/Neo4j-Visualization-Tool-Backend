package com.neo4j.simple.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.neo4j.simple.VO.ImportDataVO;
import com.neo4j.simple.entity.CommonResult;
import com.neo4j.simple.service.ImportNodesService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author lgz
 * @e-mail 2821626468@qq.com
 * @create 2023-03-05 15:20
 */
@RestController
public class ImportNodesController {
    @Resource
    private ImportNodesService importNodesService;

    //读取一个json文件，将文件中的数据导入到neo4j数据库中
    @PostMapping("/upload")
    public String uploadFile(@RequestBody byte[] fileBytes) {
        // 处理文件内容
        String content = new String(fileBytes);
        System.out.println("content: " +content);
        return "File uploaded successfully";
    }
//    @PostMapping("/upload")
//    public CommonResult<Map<String, Object>> uploadFile(HttpServletRequest request) throws Exception {
//        // 获取上传文件
//        Part filePart = request.getPart("file");
//        // 获取文件名
//        String fileName = filePart.getSubmittedFileName();
//        // 获取文件内容
//        InputStream inputStream = filePart.getInputStream();
//        //将文件内容转换为字符串
//        String inputStreamToString = new Scanner(inputStream, "UTF-8").useDelimiter("\\A").next();
//        System.out.println("inputStreamToString: " +inputStreamToString);
//        //将文件内容转换为json对象
//        JSONObject jsonObject = JSON.parseObject(inputStreamToString);
//        System.out.println("jsonObject: " +jsonObject);
//        //将json对象中的数据映射到ImportDataVO类中
//        ImportDataVO importDataVO = jsonObject.toJavaObject(ImportDataVO.class);
//        System.out.println("importDataVO: " +importDataVO);
//
//        return new CommonResult<Map<String, Object>>().success().message("File uploaded successfully");
//    }
    /**
     * 接收前端发来的json文件并解析持久化到neo4j数据库中
     * @param importDataVO
     * @return
     */
    @PostMapping("/importDataFromJson")
    public CommonResult<Map<String, Object>> handleFileUpload(@RequestBody ImportDataVO importDataVO) {
        System.out.println("文件上传...");
        Map<String, Object> count = new HashMap<>();
        if(importDataVO == null){
            count.put("nodesCount", 0);
            count.put("relationsCount", 0);
            return new CommonResult<Map<String, Object>>().fail().message("File upload failed").data(count);
        }else{
            System.out.println(importDataVO);
            Long nodesCount = importNodesService.batchAddNodes(importDataVO.getNodes());
            int relationsCount = importNodesService.batchAddRelations(importDataVO.getRelationships());

            count.put("nodesCount", nodesCount);
            count.put("relationsCount", relationsCount);
            return new CommonResult<Map<String, Object>>().success().message("File uploaded successfully").data(count);
        }

    }







}
