package com.facanditu.fcdtandroid.screen.searchresto;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.facanditu.fcdtandroid.R;
import com.facanditu.fcdtandroid.screen.GenericFcdtActivity;
import com.facanditu.fcdtandroid.screen.GenericListAdapter;

/**
 * Created by fengqin on 15/1/7.
 */
public class OneCategoryActivity extends GenericFcdtActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_resto_by_cat);
        initToolbar();
/*
        listView = (ListView)findViewById(R.id.listView);
        GenericListAdapter genericListAdapter = new GenericListAdapter(this, R.layout.oneline_generic, getList());
        listView.setAdapter(genericListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }
        });
*/


    }

    @Override
    protected int getMenu() {
        return R.menu.menu_search_resto_by_cat;
    }
}
