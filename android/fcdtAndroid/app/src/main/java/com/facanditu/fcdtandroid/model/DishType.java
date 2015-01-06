package com.facanditu.fcdtandroid.model;

/**
 * Created by fengqin on 15/1/6.
 */
public enum  DishType {

    starter("1", "Entrée", "前菜"),
    maincourse("2","Plat","主菜"),
    dessert("3", "Dessert","甜点");

    private String type;
    private String cnName;
    private String frName;

    DishType(String type,String frName, String cnName){
        this.type=type;
        this.frName=frName;
        this.cnName=cnName;
    }

    public String getType() {
        return type;
    }

    public String getCnName() {
        return cnName;
    }

    public String getFrName() {
        return frName;
    }
}
