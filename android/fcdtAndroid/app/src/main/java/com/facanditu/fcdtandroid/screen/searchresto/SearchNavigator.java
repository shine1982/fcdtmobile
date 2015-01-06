package com.facanditu.fcdtandroid.screen.searchresto;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by fengqin on 15/1/6.
 */
public class SearchNavigator {

    public static void goTo(Activity activity, SearchRestosType searchRestosType){
        Intent intent = new Intent(activity, SearchRestosActivity.class);
        intent.putExtra(SearchRestosActivity.SEARCH_TYPE_NAME, searchRestosType.name());
        activity.startActivity(intent);
    }


}
