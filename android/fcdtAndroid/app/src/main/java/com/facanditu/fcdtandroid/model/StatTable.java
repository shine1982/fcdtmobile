package com.facanditu.fcdtandroid.model;

import com.parse.ParseObject;

/**
 * Created by fengqin on 15/1/11.
 */

public class StatTable  extends ParseObject {

    public String getValue(){
        return getString("value");
    }

    public int getCount(){
        return getInt("count");
    }
}
