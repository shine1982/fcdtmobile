package com.facanditu.fcdtandroid.screen.searchresto;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by fengqin on 15/1/6.
 */
public class SearchNavigator {

    public static void goTo(Activity activity, SearchRestosType searchRestosType, String... args){
        Intent intent = new Intent(activity, SearchRestosActivity.class);
        intent.putExtra(SearchRestosActivity.SEARCH_TYPE_NAME, searchRestosType.name());
        if(args!=null){
            int i=0;
            for (final String arg: args){
                intent.putExtra("arg"+i, arg);
                i++;
            }
        }
        activity.startActivity(intent);
    }


}
