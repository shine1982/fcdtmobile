package com.facanditu.fcdtandroid.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.List;

/**
 * Created by fengqin on 15/1/10.
 */
@ParseClassName("RatpStation")
public class Transportation extends ParseObject {

    public static final String LINES="line";
    public static final String NAME="name";

    public List<String> getLines(){
        return getList(LINES);
    }

    public String getName(){
        return getString(NAME);
    }
}
