package com.neo4j.simple.entity;

import lombok.Data;

/**
 * @author lgz
 * @e-mail 2821626468@qq.com
 * @create 2023-03-05 15:38
 */
@Data
public class Neo4jConfigDto {
    public String uri;
    public String username;
    public String password;
}
