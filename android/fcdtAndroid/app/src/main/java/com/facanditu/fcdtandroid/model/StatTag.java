package com.facanditu.fcdtandroid.model;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by fengqin on 15/1/11.
 */
@ParseClassName("RestoStatTag")
public class StatTag extends StatTable {

    public static final String LOCAL_RESULTS_NAME="statTags";

    private static ParseQuery<StatTag> getQuery() {
        ParseQuery<StatTag> query = ParseQuery.getQuery(StatTag.class);
        return query;
    }

    public static ParseQuery<StatTag> getQuery(boolean withLocalDb){
        if(withLocalDb){
            return getLocalQuery();
        }else {
            return getQuery();
        }
    }

    private static ParseQuery<StatTag> getLocalQuery() {
        ParseQuery<StatTag> query = getQuery();
        query.fromPin(LOCAL_RESULTS_NAME);
        return query;
    }

    public static boolean synchroLocalDb(){
        ParseQuery<StatTag> query = StatTag.getQuery();
        List<StatTag> statTags = null;
        try {
            statTags = query.find();
        } catch (ParseException e) {}
        if(statTags != null){
            try {
                StatTag.unpinAll(LOCAL_RESULTS_NAME);
                StatTag.pinAll(LOCAL_RESULTS_NAME, statTags);
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
