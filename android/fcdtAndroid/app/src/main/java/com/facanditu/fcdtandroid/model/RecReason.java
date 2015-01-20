package com.facanditu.fcdtandroid.model;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by fengqin on 15/1/13.
 */
@ParseClassName("RecReason")
public class RecReason extends ParseObject{

    public static final String LABEL="label";
    public static final String TYPE="type";

    public static final String LOCAL_RESULTS_NAME="recReasons";

    public String getLabel(){
        return getString(LABEL);
    }

    public int getType(){
        return getInt(TYPE);
    }

    private static ParseQuery<RecReason> getQuery() {
        ParseQuery<RecReason> query = ParseQuery.getQuery(RecReason.class);
        return query;
    }

    public static ParseQuery<RecReason> getQuery(boolean withLocalDb){
        if(withLocalDb){
            return getLocalQuery();
        }else {
            return getQuery();
        }
    }

    private static ParseQuery<RecReason> getLocalQuery() {
        ParseQuery<RecReason> query = getQuery();
        query.fromPin(LOCAL_RESULTS_NAME);
        return query;
    }


    public static boolean synchroLocalDb(){
        ParseQuery<RecReason> query = RecReason.getQuery();
        List<RecReason> recReasons = null;
        try {
            recReasons = query.find();
        } catch (ParseException e) {}
        if(recReasons != null){
            try {
                RecReason.unpinAll(LOCAL_RESULTS_NAME);
                RecReason.pinAll(LOCAL_RESULTS_NAME, recReasons);
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
