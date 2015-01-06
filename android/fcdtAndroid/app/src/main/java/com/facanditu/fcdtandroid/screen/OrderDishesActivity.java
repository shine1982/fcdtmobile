package com.facanditu.fcdtandroid.screen;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.facanditu.fcdtandroid.R;
import com.facanditu.fcdtandroid.model.Dish;
import com.facanditu.fcdtandroid.util.StringUtils;

import java.util.List;

public class OrderDishesActivity extends GenericFcdtActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_dishes);
        initToolbar();
    }

    @Override
    protected int getMenu(){
        return R.menu.menu_order_dishes;
    }




}


