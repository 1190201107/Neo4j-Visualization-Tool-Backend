package com.neo4j.simple.service.impl;

import java.util.List;
import com.neo4j.simple.VO.Neo4jBasicRelationReturnVO;
import com.neo4j.simple.service.ExportNodesAndRelationService;
import com.neo4j.simple.util.Neo4jBasicRelationReturnVOExcelWriter;
import com.neo4j.simple.util.JsonWriter;
import org.springframework.stereotype.Service;


@Service
public class ExportNodesAndRelationServiceImpl implements ExportNodesAndRelationService {

    @Override
    public boolean exportNodesAndRelationToExcel() {
        List<Neo4jBasicRelationReturnVO> data = null;// get data from somewhere
        try {
            Neo4jBasicRelationReturnVOExcelWriter.write(data, "output.xlsx");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean exportNodesAndRelationToJson() {
        List<Neo4jBasicRelationReturnVO> data = null;// get data from somewhere
        try {
            JsonWriter.writeToJson(data, "output.json");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
