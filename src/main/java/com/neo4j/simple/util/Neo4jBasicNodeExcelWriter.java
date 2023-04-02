package com.neo4j.simple.util;

import com.neo4j.simple.entity.Neo4jBasicNode;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class Neo4jBasicNodeExcelWriter {

    public static void writeExcel(List<Neo4jBasicNode> nodes, String fileName) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        int rowNum = 0;
        Row headerRow = sheet.createRow(rowNum++);
        headerRow.createCell(0).setCellValue("id");
        headerRow.createCell(1).setCellValue("labels");
        headerRow.createCell(2).setCellValue("properties");

        for (Neo4jBasicNode node : nodes) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(node.getId());
            row.createCell(1).setCellValue(node.getLabels().toString());
            row.createCell(2).setCellValue(node.getProperties().toString());
        }

        FileOutputStream outputStream = new FileOutputStream(fileName);
        workbook.write(outputStream);
        workbook.close();
    }
}
