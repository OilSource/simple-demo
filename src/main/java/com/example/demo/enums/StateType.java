package com.example.demo.enums;

public enum StateType {
    Valid(1,"启用"),
    Invalid(0,"禁用");


    StateType(int value, String label) {
        this.value = value;
        this.label = label;
    }

    private int value;

    private String label;

    public int value(){
        return value;
    }
}
