package com.facanditu.fcdtandroid.screen.searchresto;

import android.app.Activity;

import com.facanditu.fcdtandroid.model.Restaurant;
import com.facanditu.fcdtandroid.util.FavRestoManager;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

/**
 * Created by fengqin on 15/1/6.
 */
public class RestaurantQueryFactory implements ParseQueryAdapter.QueryFactory<Restaurant> {

    private SearchRestosType searchType;
    private Activity context;

    public RestaurantQueryFactory(Activity context,String searchType){
        this.context=context;
        this.searchType =SearchRestosType.valueOf(searchType);
    }

    @Override
    public ParseQuery<Restaurant> create() {
        ParseQuery<Restaurant> query = Restaurant.getQuery();
        if(SearchRestosType.Fav.equals(searchType)){//favorite
            query.whereContainedIn("objectId", FavRestoManager.getInstance(context).getFavoriteRestoIds());
        }

        return query;
    }
}
