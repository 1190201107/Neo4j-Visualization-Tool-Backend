package com.neo4j.simple.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neo4j.simple.VO.ImportDataVO;
import com.neo4j.simple.entity.CommonResult;
import com.neo4j.simple.service.ImportNodesService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    /**
     * 接收前端发来的json文件并解析持久化到neo4j数据库中
     * @param importDataVO
     * @return
     */
    @PostMapping("/importDataFromJson")
    public CommonResult<String> handleFileUpload(@RequestBody ImportDataVO importDataVO) {
        System.out.println("文件上传...");
        if(importDataVO == null){
            return new CommonResult<String>().fail().message("File upload failed");
        }else{
            System.out.println(importDataVO);
            return new CommonResult<String>().success().message("File uploaded successfully");
        }


    }







}
