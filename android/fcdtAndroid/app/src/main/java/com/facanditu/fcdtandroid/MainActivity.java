package com.facanditu.fcdtandroid;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.facanditu.fcdtandroid.screen.searchresto.SearchNavigator;
import com.facanditu.fcdtandroid.screen.searchresto.SearchRestoByCatActivity;
import com.facanditu.fcdtandroid.screen.searchresto.SearchRestosActivity;
import com.facanditu.fcdtandroid.screen.searchresto.SearchRestosType;


public class MainActivity extends ActionBarActivity {

    private Toolbar toolbar;

    private ImageButton mGeoLoc, mFav, mSearchByCat, mWikiFrCook;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        NavigationDrawerFragment drawerFragment
                = (NavigationDrawerFragment)getSupportFragmentManager().findFragmentById(R.id.drawer_navigation);

        drawerFragment.setUp(R.id.drawer_navigation, (DrawerLayout) findViewById( R.id.drawer_layout), toolbar);

        initGeoLocButton(this);
        initFavButton(this);
        initSearchRestosByCat(this);
        initWikiFrCook(this);
    }

    private void initWikiFrCook(MainActivity mainActivity) {
        mWikiFrCook=(ImageButton)findViewById(R.id.wikiFrCook);
    }

    private void initSearchRestosByCat(MainActivity mainActivity) {
        mSearchByCat=(ImageButton)findViewById(R.id.searchRestosByCat);
        mSearchByCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchRestoByCatActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initFavButton(final MainActivity mainActivity) {
        mFav=(ImageButton)findViewById(R.id.favRestos);
        mFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchNavigator.goTo(mainActivity,SearchRestosType.Fav);
            }
        });
    }


    private void initGeoLocButton(final Activity activity){
        mGeoLoc = (ImageButton)findViewById(R.id.geolocRestos);
        mGeoLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchNavigator.goTo(activity,SearchRestosType.NearBy);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}
