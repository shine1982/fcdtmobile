package com.facanditu.fcdtandroid.screen.searchresto;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.facanditu.fcdtandroid.R;
import com.facanditu.fcdtandroid.model.Restaurant;
import com.facanditu.fcdtandroid.model.RestaurantBO;
import com.facanditu.fcdtandroid.screen.GenericFcdtActivity;
import com.facanditu.fcdtandroid.screen.restaurant.RestaurantMainActivity;
import com.facanditu.fcdtandroid.util.FavRestoManager;
import com.facanditu.fcdtandroid.util.GeoUtils;
import com.facanditu.fcdtandroid.util.LocationUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.util.List;

public class SearchRestosActivity extends GenericFcdtActivity  {

    public static final String SEARCH_TYPE_NAME="searchType";




    private String searchType;

    private GeoLocHelper geoLocHelper;

    private boolean isNearBySearch;

    private boolean isFavSearch;


    private RestaurantQueryFactory factory ;
    private FindCallback<Restaurant> callback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_restos);

            searchType=
                    getIntent().getExtras().getString(SEARCH_TYPE_NAME);

            initToolbar();

            TextView textView = (TextView) findViewById(R.id.searchTypeTitle);
            textView.setText(SearchRestosType.getSearchResultTitle(searchType));
            factory = new RestaurantQueryFactory(this,searchType);
            isNearBySearch=SearchRestosType.NearBy.name().equals(searchType);
            if(isNearBySearch){
                geoLocHelper = new GeoLocHelper(this);
            }else{

                final RestoParseQueryAdapter adapter =new RestoParseQueryAdapter(this,factory);
                adapter.setPaginationEnabled(true);
                adapter.setObjectsPerPage(10);
                adapter.addOnQueryLoadListener(new ParseQueryAdapter.OnQueryLoadListener<Restaurant>() {
                    public void onLoading() {}
                    @Override
                    public void onLoaded(List<Restaurant> restaurants, Exception e) {
                        hideSearchingBloc();
                    }

                });
                ListView listView = (ListView)findViewById(R.id.restosList);
                listView.setAdapter(adapter);
                final Activity activity=this;
                listView.setOnItemClickListener(
                        new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                final Restaurant resto = adapter.getItem(i);
                                RestaurantBO restoBo = new RestaurantBO(resto);
                                Intent intent = new Intent(activity, RestaurantMainActivity.class);
                                intent.putExtra(RestaurantMainActivity.RESTO, restoBo);
                                startActivity(intent);
                            }
                        }
                );

            }


    }
    @Override
    protected int getMenu() {
        return R.menu.menu_search_restos;
    }


    @Override
    protected void onStart() {
        super.onStart();
        if(isNearBySearch){// geoloc
            geoLocHelper.connect();
        }
    }


    @Override
    public void onStop() {
        if(isNearBySearch){
            geoLocHelper.disconnect();
        }
        super.onStop();
    }
}
class RestoParseQueryAdapter extends ParseQueryAdapter<Restaurant>{



    public RestoParseQueryAdapter(Context context, ParseQueryAdapter.QueryFactory<Restaurant> queryFactory) {
        super(context, queryFactory);
    }

    @Override
    public View getItemView(Restaurant restaurant, View view, ViewGroup parent) {
        if (view == null) {
            view = View.inflate(getContext(), R.layout.one_resto_line, null);
        }

        TextView restoName = (TextView) view.findViewById(R.id.restoNameTextView);

        restoName.setText(restaurant.getName());

        return view;
    }
}

