package com.facanditu.fcdtandroid.util;

import java.util.List;

/**
 * Created by fengqin on 14/11/27.
 */
public class StringUtils {

    public static boolean isEmpty(String s){
        return s==null || "".equals(s);
    }

    public static String toString(List<String> list, String delimiter){
        if(list==null)return "";
        StringBuilder sb=new StringBuilder();
        for(int i=0; i<list.size(); i++){
            sb.append(list.get(i));
            if(i!=list.size()-1){
                sb.append(delimiter);
            }
        }
        return sb.toString();
    }
}
