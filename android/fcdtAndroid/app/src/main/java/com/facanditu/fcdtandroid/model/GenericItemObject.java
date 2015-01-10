package com.facanditu.fcdtandroid.model;

/**
 * Created by fengqin on 15/1/7.
 */
public class GenericItemObject {

    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public GenericItemObject(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
