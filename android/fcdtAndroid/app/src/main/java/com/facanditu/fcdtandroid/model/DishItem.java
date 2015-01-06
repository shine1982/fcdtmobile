package com.facanditu.fcdtandroid.model;

import com.facanditu.fcdtandroid.screen.DishLangMode;

/**
 * Created by fengqin on 15/1/6.
 */
public class DishItem {

    private String cnName;
    private String frName;
    private String price;
    private boolean isTitle;

    public DishItem(String cnName, String frName, String price, boolean isTitle) {
        this.cnName = cnName;
        this.frName = frName;
        this.price = price;
        this.isTitle = isTitle;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getFrName() {
        return frName;
    }

    public void setFrName(String frName) {
        this.frName = frName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isTitle() {
        return isTitle;
    }

    public void setTitle(boolean isTitle) {
        this.isTitle = isTitle;
    }

    public String getFrPrice(){
        return getPrice()+" €";
    }

    public String getCnPrice(){
        return getPrice()+" 欧";
    }

    public String getName(final DishLangMode dishLangMode){
        return DishLangMode.FR.equals(dishLangMode)?getFrName():getCnName();
    }

    public String getPriceLabel(final DishLangMode dishLangMode){
        return DishLangMode.FR.equals(dishLangMode)?getFrPrice():getCnPrice();
    }

    public String getInverseName(final DishLangMode dishLangMode){
        return DishLangMode.FR.equals(dishLangMode)?getCnName():getFrName();
    }

    public String getInversePriceLabel(final DishLangMode dishLangMode){
        return DishLangMode.FR.equals(dishLangMode)?getCnPrice():getFrPrice();
    }
}
