package com.facanditu.fcdtandroid.model;

import java.util.Arrays;
import java.util.List;

/**
 * Created by fengqin on 15/1/20.
 */
public enum PriceCriteria {

    P_0_30("0 ~ 30 欧", 0, 30),
    P_30_50("30 ~ 50 欧", 30, 50),
    P_50_75("50 ~ 75 欧", 50, 75),
    P_75_100("75 ~ 100 欧", 75, 100),
    P_100_200("100 ~ 200 欧", 100, 200),
    P_200_plus(" > 200 欧", 200, 10000);

    private String label;
    private int min;
    private int max;

    PriceCriteria(String label, int min, int max) {
        this.label = label;
        this.min = min;
        this.max = max;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public static PriceCriteria getCriteria(String label){
        for(PriceCriteria priceCriteria: PriceCriteria.values()){
            if(priceCriteria.getLabel().equals(label)){
                return priceCriteria;
            }
        }
        return null;
    }
}
