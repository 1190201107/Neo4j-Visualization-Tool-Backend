package com.neo4j.simple.util;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.TypeReference;
import com.neo4j.simple.VO.Neo4jBasicRelationReturnVO;
import com.neo4j.simple.entity.Neo4jBasicNode;
import com.neo4j.simple.entity.Neo4jQueryRelation;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Neo4jBasicRelationReturnVOExcelReader {

    public static List<Neo4jBasicRelationReturnVO> readExcel(String filePath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        Workbook workbook = WorkbookFactory.create(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);

        List<Neo4jBasicRelationReturnVO> resultList = new ArrayList<>();
        int rowNum = 0;
        for (Row row : sheet) {
            if (rowNum == 0) {
                rowNum++;
                continue;
            }
            Neo4jBasicRelationReturnVO relationReturnVO = new Neo4jBasicRelationReturnVO();
            relationReturnVO.setStart(readNodeFromRow(row, 0));
            relationReturnVO.setRelationship(readRelationFromRow(row, 3));
            relationReturnVO.setEnd(readNodeFromRow(row, 7));
            resultList.add(relationReturnVO);
            rowNum++;
        }
        return resultList;
    }

    private static Neo4jBasicNode readNodeFromRow(Row row, int startColIndex) {
        Neo4jBasicNode node = new Neo4jBasicNode();
        node.setId(getLongCellValue(row.getCell(startColIndex)));
        node.setLabels(getLabelsFromCell(row.getCell(startColIndex + 1)));
        node.setProperties(getPropertiesFromCell(row.getCell(startColIndex + 2)));
        return node;
    }

    private static Neo4jQueryRelation readRelationFromRow(Row row, int startColIndex) {
        Neo4jQueryRelation relation = new Neo4jQueryRelation();
        relation.setStartNode(getLongCellValue(row.getCell(startColIndex)));
        relation.setEndNode(getLongCellValue(row.getCell(startColIndex + 1)));
        relation.setType(getStringCellValue(row.getCell(startColIndex + 2)));
        relation.setId(getLongCellValue(row.getCell(startColIndex + 3)));
        relation.setProperties(getPropertiesFromCell(row.getCell(startColIndex + 4)));
        return relation;
    }

    private static List<String> getLabelsFromCell(Cell cell) {
        List<String> labels = new ArrayList<>();
        String[] labelArray = getStringCellValue(cell).split(",");
        for (String label : labelArray) {
            if (label.trim().length() > 0) {
                labels.add(label.trim());
            }
        }
        return labels;
    }

    private static Map<String, Object> getPropertiesFromCell(Cell cell) {
        String json = getStringCellValue(cell);
        Map<String, Object> properties = null;
        try {
            properties = JSON.parseObject(json, new TypeReference<Map<String, Object>>() {});
        } catch (JSONException e) {
            // do something
        }
        return properties;
    }

    private static Long getLongCellValue(Cell cell) {
        if (cell != null) {
            return (long) cell.getNumericCellValue();
        } else {
            return null;
        }
    }

    private static String getStringCellValue(Cell cell) {
        if (cell != null) {
            return cell.getStringCellValue();
        } else {
            return null;
        }
    }
}
