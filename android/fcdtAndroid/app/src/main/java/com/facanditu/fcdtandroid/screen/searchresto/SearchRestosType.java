package com.facanditu.fcdtandroid.screen.searchresto;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fengqin on 15/1/2.
 */
public enum SearchRestosType {
    NearBy,
    Fav,
    Address,
    PostCode,
    City,
    Tags,
    Price,
    RecReason,
    DishName,
    Random,
    Newest,
    Metro;

    private static Map<String, String> tMap; //title map
    static {
        tMap = new HashMap<>();
        tMap.put(SearchRestosType.NearBy.name(), "搜索到在您附近有以下餐厅");
        tMap.put(SearchRestosType.Fav.name(), "您的收藏");
        tMap.put(SearchRestosType.City.name(), "所选城市搜到的餐厅");
        tMap.put(SearchRestosType.PostCode.name(), "所选邮编搜到的餐厅");
    }
    public static String getSearchResultTitle(String searchRestosType){
        return tMap.get(searchRestosType);
    }

}
