package com.example.demo.enums;

public enum LocalCacheType {

    CONSTANT("constant"),
    MENU("menu"),

    ;

    LocalCacheType(String value) {
        this.value = value;
    }

    private String value;


    public String value(){
        return value;
    }


}
