package com.neo4j.simple.service.impl;

import com.neo4j.simple.VO.Neo4jBasicRelationReturnVO;
import com.neo4j.simple.service.ImportNodesAndRelationService;
import com.neo4j.simple.util.Neo4jBasicRelationReturnVOExcelReader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ImportNodesAndRelationServiceImpl implements ImportNodesAndRelationService {
    @Override
    public boolean importNodesAndRelationToExcel(String filePath) {
        try {
            List<Neo4jBasicRelationReturnVO> data = Neo4jBasicRelationReturnVOExcelReader.readExcel(filePath);
            // todo something with data
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
