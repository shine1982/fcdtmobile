package com.facanditu.fcdtandroid.model;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by fengqin on 15/1/11.
 */
@ParseClassName("RestoStatRecReason")
public class StatRecReason extends StatTable {

    public static final String LOCAL_RESULTS_NAME="statRecReasons";

    private static ParseQuery<StatRecReason> getQuery() {
        ParseQuery<StatRecReason> query = ParseQuery.getQuery(StatRecReason.class);
        return query;
    }

    public static ParseQuery<StatRecReason> getQuery(boolean withLocalDb){
        if(withLocalDb){
            return getLocalQuery();
        }else {
            return getQuery();
        }
    }

    private static ParseQuery<StatRecReason> getLocalQuery() {
        ParseQuery<StatRecReason> query = getQuery();
        query.fromPin(LOCAL_RESULTS_NAME);
        return query;
    }

    public static boolean synchroLocalDb(){
        ParseQuery<StatRecReason> query = StatRecReason.getQuery();
        List<StatRecReason> statRecReasons = null;
        try {
            statRecReasons = query.find();
        } catch (ParseException e) {}
        if(statRecReasons != null){
            try {
                StatRecReason.unpinAll(LOCAL_RESULTS_NAME);
                StatRecReason.pinAll(LOCAL_RESULTS_NAME, statRecReasons);
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
