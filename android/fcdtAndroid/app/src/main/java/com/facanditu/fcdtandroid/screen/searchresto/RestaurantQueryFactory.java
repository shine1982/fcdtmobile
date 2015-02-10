package com.facanditu.fcdtandroid.screen.searchresto;

import android.app.Activity;

import com.facanditu.fcdtandroid.model.PriceCriteria;
import com.facanditu.fcdtandroid.model.RecReason;
import com.facanditu.fcdtandroid.model.Restaurant;
import com.facanditu.fcdtandroid.util.FavRestoManager;
import com.facanditu.fcdtandroid.util.LocalDbIndicator;
import com.parse.ParseException;
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
        ParseQuery<Restaurant> query = Restaurant.getQuery(LocalDbIndicator.getIns().isSyncSuccess());
        if(SearchRestosType.Fav.equals(searchType)){//favorite
            query.whereContainedIn("objectId", FavRestoManager.getInstance(context).getFavoriteRestoIds());
        }else if(SearchRestosType.Newest.equals(searchType)){
            query.orderByDescending("createdAt");
        }else if(SearchRestosType.Random.equals(searchType)){

        }else if(SearchRestosType.City.equals(searchType)){
            query.whereEqualTo(Restaurant.CITY, context.getIntent().getStringExtra(SearchNavigator.ARG0));

        }else if(SearchRestosType.PostCode.equals(searchType)){
            query.whereEqualTo(Restaurant.POSTAL_CODE, context.getIntent().getStringExtra(SearchNavigator.ARG0));

        }else if(SearchRestosType.Tags.equals(searchType)){
            query.whereEqualTo(Restaurant.TAG_LIST, context.getIntent().getStringExtra(SearchNavigator.ARG0));

        }else if(SearchRestosType.RecReason.equals(searchType)){
            final ParseQuery<RecReason> recReasonQuery = RecReason.getQuery(LocalDbIndicator.getIns().isSyncSuccess());
            recReasonQuery.whereEqualTo(RecReason.LABEL, context.getIntent().getStringExtra(SearchNavigator.ARG0));
            RecReason recReason = null;
            try {
                recReason = recReasonQuery.getFirst();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            query.whereEqualTo(Restaurant.REC_REASONS, recReason);

        }else if(SearchRestosType.Price.equals(searchType)){
            PriceCriteria priceCriteria = PriceCriteria.getCriteria(context.getIntent().getStringExtra(SearchNavigator.ARG0));
            query.whereLessThanOrEqualTo(Restaurant.PRICE_NOON,priceCriteria.getMax());
            query.whereGreaterThanOrEqualTo(Restaurant.PRICE_NOON, priceCriteria.getMin());
        }


        return query;
    }
}
