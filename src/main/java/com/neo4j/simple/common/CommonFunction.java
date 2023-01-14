package com.neo4j.simple.common;

public class CommonFunction {

    public static boolean isEmpty(Object label){
        return label == null;
    }

    public static boolean isNotEmpty(Object label){
        return label != null;
    }
}
