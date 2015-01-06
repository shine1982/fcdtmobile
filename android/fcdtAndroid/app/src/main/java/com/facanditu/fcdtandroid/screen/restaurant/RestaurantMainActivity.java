package com.facanditu.fcdtandroid.screen.restaurant;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.facanditu.fcdtandroid.R;
import com.facanditu.fcdtandroid.model.RestaurantBO;
import com.facanditu.fcdtandroid.screen.GenericFcdtActivity;
import com.facanditu.fcdtandroid.screen.searchresto.RestaurantBoHelper;

public class RestaurantMainActivity extends GenericFcdtActivity implements RestaurantBoHelper {

    public static final String RESTO="resto";
    private RestaurantBO restaurant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_main);

        initToolbar();
        Intent intent = getIntent();
        restaurant = (RestaurantBO) intent.getSerializableExtra(RESTO);
    }

    @Override
    protected int getMenu() {
        return R.menu.menu_restaurant_main;
    }

    @Override
    public RestaurantBO getResto() {
        return restaurant;
    }
}
