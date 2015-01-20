package com.facanditu.fcdtandroid.screen.searchresto;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by fengqin on 15/1/6.
 */
public class SearchNavigator {

    public static final String ARG="arg";
    public static final String ARG0=ARG+"0";

    public static void goTo(Activity activity, SearchRestosType searchRestosType, String... args){
        goTo(activity,SearchRestosActivity.class, searchRestosType, args);
    }

    public static  void goTo(Activity activity, Class desinationActivity, SearchRestosType searchRestosType, String... args){

        Intent intent = new Intent(activity, desinationActivity);
        intent.putExtra(SearchRestosActivity.SEARCH_TYPE_NAME, searchRestosType.name());
        if(args!=null){
            int i=0;
            for (final String arg: args){
                intent.putExtra(ARG+i, arg);
                i++;
            }
        }
        activity.startActivity(intent);
    }


}
