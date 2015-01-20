package com.facanditu.fcdtandroid.model;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by fengqin on 15/1/11.
 */
@ParseClassName("RestoStatCity")
public class StatCity extends StatTable {

    public static final String LOCAL_RESULTS_NAME="statCities";

    private static ParseQuery<StatCity> getQuery() {
        ParseQuery<StatCity> query = ParseQuery.getQuery(StatCity.class);
        return query;
    }

    public static ParseQuery<StatCity> getQuery(boolean withLocalDb){
        if(withLocalDb){
            return getLocalQuery();
        }else {
            return getQuery();
        }
    }

    private static ParseQuery<StatCity> getLocalQuery() {
        ParseQuery<StatCity> query = getQuery();
        query.fromPin(LOCAL_RESULTS_NAME);
        return query;
    }

    public static boolean synchroLocalDb(){
        ParseQuery<StatCity> query = StatCity.getQuery();
        List<StatCity> statCities = null;
        try {
            statCities = query.find();
        } catch (ParseException e) {}
        if(statCities != null){
            try {
                StatCity.unpinAll(LOCAL_RESULTS_NAME);
                StatCity.pinAll(LOCAL_RESULTS_NAME, statCities);
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
