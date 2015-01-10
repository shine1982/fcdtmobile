package com.facanditu.fcdtandroid.screen.searchresto;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.facanditu.fcdtandroid.R;
import com.facanditu.fcdtandroid.model.GenericItemObject;
import com.facanditu.fcdtandroid.screen.GenericFcdtActivity;
import com.facanditu.fcdtandroid.screen.GenericListAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchRestoByCatActivity extends GenericFcdtActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_resto_by_cat);
        initToolbar();

        listView = (ListView)findViewById(R.id.listView);
        GenericListAdapter genericListAdapter = new GenericListAdapter(this, R.layout.oneline_generic, getList());
        listView.setAdapter(genericListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(SearchRestoByCatActivity.this,"clicked "+ view.getTag(), Toast.LENGTH_SHORT).show();
            }
        });



    }

    private List<GenericItemObject> getList(){
        List<GenericItemObject> list = new ArrayList<>();
        list.add(new GenericItemObject(SearchRestosType.Address.name(),"按地址"));
        list.add(new GenericItemObject(SearchRestosType.PostCode.name(),"按邮编"));
        list.add(new GenericItemObject(SearchRestosType.City.name(),"按城市"));
        list.add(new GenericItemObject(SearchRestosType.Tags.name(),"按特色标签"));
        list.add(new GenericItemObject(SearchRestosType.Price.name(),"按价格"));
        list.add(new GenericItemObject(SearchRestosType.RecReason.name(),"按推荐理由"));
        list.add(new GenericItemObject(SearchRestosType.DishName.name(),"按菜名(中、法)"));
        list.add(new GenericItemObject(SearchRestosType.Random.name(),"随机浏览"));
        list.add(new GenericItemObject(SearchRestosType.Newest.name(),"最新加入"));
        return list;
    }

    @Override
    protected int getMenu() {
        return R.menu.menu_search_resto_by_cat;
    }


}
