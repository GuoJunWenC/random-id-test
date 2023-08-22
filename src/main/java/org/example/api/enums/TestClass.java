package org.example.api.enums;

public class TestClass {
    public static void main(String [] args){
        System.out.println(EnumCache.findByName(StatusEnum.class, "SUCCESS", null));
        // 返回默认值StatusEnum.INIT
        System.out.println(EnumCache.findByName(StatusEnum.class, null, StatusEnum.INIT));
        // 返回默认值StatusEnum.INIT
        System.out.println(EnumCache.findByName(StatusEnum.class, "ERROR", StatusEnum.INIT));


        System.out.println(EnumCache.findByValue(StatusEnum.class, "S", null));
        // 返回默认值StatusEnum.INIT
        System.out.println(EnumCache.findByValue(StatusEnum.class, null, StatusEnum.INIT));
        // 返回默认值StatusEnum.INIT
        System.out.println(EnumCache.findByValue(StatusEnum.class, "ERROR", StatusEnum.INIT));
    }
}
