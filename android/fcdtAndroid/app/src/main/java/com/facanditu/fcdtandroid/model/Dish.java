package com.facanditu.fcdtandroid.model;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

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
    public static final String LOCAL_RESULTS_NAME="dishes";

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

    private static ParseQuery<Dish> getQuery() {
        return ParseQuery.getQuery(Dish.class).orderByAscending("order");
    }

    public static ParseQuery<Dish> getQuery(String idResto) {
        ParseQuery<Dish> parseQuery = getQuery();
        parseQuery.whereEqualTo(Restaurant.FOREIGNE_KEY, Restaurant.newRestaurantWithoutData(idResto));
        return parseQuery;
    }

    public static ParseQuery<Dish> getQuery(String idResto, boolean withLocalDb){
        if(withLocalDb){
            return getLocalQuery(idResto);
        }else {
            return getQuery(idResto);
        }
    }

    private static ParseQuery<Dish> getLocalQuery(String idResto) {
        ParseQuery<Dish> query = getQuery(idResto);
        query.fromPin(LOCAL_RESULTS_NAME);
        return query;
    }

    public static boolean synchroLocalDb(){
        ParseQuery<Dish> query = Dish.getQuery();
        List<Dish> dishs = null;
        try {
            dishs = query.find();
        } catch (ParseException e) {}
        if(dishs != null){
            try {
                Dish.unpinAll(LOCAL_RESULTS_NAME);
                Dish.pinAll(LOCAL_RESULTS_NAME, dishs);
                Log.d(LOCAL_RESULTS_NAME, "synchroLocalDb succeed!");
            } catch (ParseException e) {
                return false;
            }
            return true;
        }else {
            return false;
        }
    }
}
