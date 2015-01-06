package com.facanditu.fcdtandroid.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by fengqin on 15/1/5.
 */
@ParseClassName("Dish")
public class Dish extends ParseObject {

    public static final String FR_NAME="name";
    public static final String CN_NAME="namecn";
    public static final String PRICE_EURO="priceEuro";
    public static final String PRICE_CENTIMES="priceCentimes";
    public static final String DISH_TYPE="dishType";

    public String getFrName(){
        return getString(FR_NAME);
    }

    public String getCnName(){
        return getString(CN_NAME);
    }

    public String getPriceEuro(){
        return getString(PRICE_EURO);
    }

    public String getPriceCentimes(){
        return getString(PRICE_CENTIMES);
    }

    public String getDishType(){
        return getString(DISH_TYPE);
    }

    public static ParseQuery<Dish> getQuery() {
        return ParseQuery.getQuery(Dish.class).orderByAscending("order");
    }

    public static ParseQuery<Dish> getQuery(String idResto) {
        ParseQuery<Dish> parseQuery = Dish.getQuery();
        parseQuery.whereEqualTo(Restaurant.FOREIGNE_KEY, Restaurant.newRestaurantWithoutData(idResto));
        return parseQuery;
    }
}
