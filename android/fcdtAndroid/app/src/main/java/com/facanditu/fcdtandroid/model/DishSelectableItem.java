package com.facanditu.fcdtandroid.model;

import java.io.Serializable;

/**
 * Created by fengqin on 15/1/6.
 */
public class DishSelectableItem extends DishItem implements Serializable{

    private int selectedCount;

    public int getSelectedCount() {
        return selectedCount;
    }

    public void setSelectedCount(int selectedCount) {
        this.selectedCount = selectedCount;
    }

    public DishSelectableItem(String cnName, String frName, String price, String dishType, boolean isTitle, int selected) {
        super(cnName, frName, price, dishType, isTitle);
        this.selectedCount = selected;
    }
}
