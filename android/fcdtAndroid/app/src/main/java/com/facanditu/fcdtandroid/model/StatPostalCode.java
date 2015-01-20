package com.facanditu.fcdtandroid.model;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by fengqin on 15/1/11.
 */
@ParseClassName("RestoStatPostalCode")
public class StatPostalCode extends StatTable {

    public static final String LOCAL_RESULTS_NAME="statPostalCodes";
    private static ParseQuery<StatPostalCode> getQuery() {
        ParseQuery<StatPostalCode> query = ParseQuery.getQuery(StatPostalCode.class);
        query.orderByAscending("value");
        return query;
    }

    public static ParseQuery<StatPostalCode> getQuery(boolean withLocalDb){
        if(withLocalDb){
            return getLocalQuery();
        }else {
            return getQuery();
        }
    }

    private static ParseQuery<StatPostalCode> getLocalQuery() {
        ParseQuery<StatPostalCode> query = getQuery();
        query.fromPin(LOCAL_RESULTS_NAME);
        return query;
    }

    public static boolean synchroLocalDb(){
        ParseQuery<StatPostalCode> query = StatPostalCode.getQuery();
        List<StatPostalCode> statPostalCodes = null;
        try {
            statPostalCodes = query.find();
        } catch (ParseException e) {}
        if(statPostalCodes != null){
            try {
                StatPostalCode.unpinAll(LOCAL_RESULTS_NAME);
                StatPostalCode.pinAll(LOCAL_RESULTS_NAME, statPostalCodes);
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
