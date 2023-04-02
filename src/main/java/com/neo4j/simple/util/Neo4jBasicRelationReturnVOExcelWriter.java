package com.neo4j.simple.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.neo4j.simple.VO.Neo4jBasicRelationReturnVO;
import com.neo4j.simple.entity.Neo4jBasicNode;
import com.neo4j.simple.entity.Neo4jQueryRelation;

public class Neo4jBasicRelationReturnVOExcelWriter {

    public static void write(List<Neo4jBasicRelationReturnVO> objects, String filename) throws IOException, IllegalArgumentException, IllegalAccessException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        // Write header row
        Row headerRow = sheet.createRow(0);
        Field[] fields = Neo4jBasicRelationReturnVO.class.getDeclaredFields();
        int colIndex = 0;
        for (Field field : fields) {
            field.setAccessible(true);
            Class<?> fieldType = field.getType();
            if (fieldType == Neo4jBasicNode.class || fieldType == Neo4jQueryRelation.class) {
                Field[] nestedFields = fieldType.getDeclaredFields();
                for (Field nestedField : nestedFields) {
                    nestedField.setAccessible(true);
                    Cell cell = headerRow.createCell(colIndex++);
                    cell.setCellValue(field.getName() + "." + nestedField.getName());
                }
            } else {
                Cell cell = headerRow.createCell(colIndex++);
                cell.setCellValue(field.getName());
            }
        }

        // Write data rows
        for (int i = 0; i < objects.size(); i++) {
            Neo4jBasicRelationReturnVO object = objects.get(i);
            Row row = sheet.createRow(i + 1);
            colIndex = 0;
            for (Field field : fields) {
                field.setAccessible(true);
                Class<?> fieldType = field.getType();
                if (fieldType == Neo4jBasicNode.class || fieldType == Neo4jQueryRelation.class) {
                    Field[] nestedFields = fieldType.getDeclaredFields();
                    for (Field nestedField : nestedFields) {
                        nestedField.setAccessible(true);
                        Cell cell = row.createCell(colIndex++);
                        Object nestedValue = nestedField.get(field.get(object));
                        if (nestedValue != null) {
                            cell.setCellValue(nestedValue.toString());
                        }
                    }
                } else {
                    Cell cell = row.createCell(colIndex++);
                    Object value = field.get(object);
                    if (value != null) {
                        cell.setCellValue(value.toString());
                    }
                }
            }
        }

        // Write workbook to file
        try (FileOutputStream outputStream = new FileOutputStream(filename)) {
            workbook.write(outputStream);
        }
    }
}
