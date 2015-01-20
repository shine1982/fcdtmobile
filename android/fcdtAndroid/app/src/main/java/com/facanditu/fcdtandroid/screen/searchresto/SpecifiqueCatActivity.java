package com.facanditu.fcdtandroid.screen.searchresto;

import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.facanditu.fcdtandroid.R;
import com.facanditu.fcdtandroid.model.GenericItemObject;
import com.facanditu.fcdtandroid.model.Restaurant;
import com.facanditu.fcdtandroid.model.StatCity;
import com.facanditu.fcdtandroid.model.StatPostalCode;
import com.facanditu.fcdtandroid.model.StatRecReason;
import com.facanditu.fcdtandroid.model.StatTag;
import com.facanditu.fcdtandroid.screen.GenericFcdtActivity;
import com.facanditu.fcdtandroid.screen.GenericListAdapter;
import com.facanditu.fcdtandroid.util.LocalDbIndicator;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

public class SpecifiqueCatActivity extends GenericFcdtActivity {

    private ListView listView;
    private SearchRestosType searchRestosType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specifique_cat);
        initToolbar();
        searchRestosType = SearchRestosType.valueOf(getIntent().getExtras().getString(SearchRestosActivity.SEARCH_TYPE_NAME));

        listView = (ListView)findViewById(R.id.listView);

        GenericListAdapter genericListAdapter = new GenericListAdapter(this, R.layout.oneline_generic, getList());
        listView.setAdapter(genericListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final String argu1 = (String)view.getTag();
                if(searchRestosType.equals(SearchRestosType.City)
                        ||searchRestosType.equals(SearchRestosType.PostCode)
                        ||searchRestosType.equals(SearchRestosType.Tags)
                        ||searchRestosType.equals(SearchRestosType.RecReason)){
                    SearchNavigator.goTo(SpecifiqueCatActivity.this, searchRestosType, argu1);
                }

            }
        });
    }


    @Override
    protected int getMenu() {
        return R.menu.menu_specifique_cat;
    }

    private List<GenericItemObject> getList(){
        List<GenericItemObject> list = new ArrayList<>();

        if(SearchRestosType.City.equals(searchRestosType)){//search by city
            getCityList(list);
        }else if(SearchRestosType.PostCode.equals(searchRestosType)){
            getPostalCodeList(list);
        }else if(SearchRestosType.Tags.equals(searchRestosType)){
            getTagList(list);
        }else if(SearchRestosType.RecReason.equals(searchRestosType)){
            getRecReasonList(list);
        }

        return list;
    }

    private void getCityList(List<GenericItemObject> list) {
        List<StatCity> statCities = null;
        try {
            statCities = StatCity.getQuery(LocalDbIndicator.getIns().isSyncSuccess()).find();
        } catch (ParseException e) {}
        if(statCities!=null){
            for(StatCity statCity:statCities){
                list.add(new GenericItemObject(statCity.getValue(), statCity.getValue()+" ("+statCity.getCount()+")"));
            }
        }
    }

    private void getPostalCodeList(List<GenericItemObject> list) {
        List<StatPostalCode> stats = null;
        try {
            stats = StatPostalCode.getQuery(LocalDbIndicator.getIns().isSyncSuccess()).find();
        } catch (ParseException e) {}
        if(stats!=null){
            for(StatPostalCode stat:stats){
                list.add(new GenericItemObject(stat.getValue(), stat.getValue()+" ("+stat.getCount()+")"));
            }
        }
    }

    private void getRecReasonList(List<GenericItemObject> list) {
        List<StatRecReason> stats = null;
        try {
            stats = StatRecReason.getQuery(LocalDbIndicator.getIns().isSyncSuccess()).find();
        } catch (ParseException e) {}
        if(stats!=null){
            for(StatRecReason stat:stats){
                list.add(new GenericItemObject(stat.getValue(), stat.getValue()+" ("+stat.getCount()+")"));
            }
        }
    }

    private void getTagList(List<GenericItemObject> list) {
        List<StatTag> stats = null;
        try {
            stats = StatTag.getQuery(LocalDbIndicator.getIns().isSyncSuccess()).find();
        } catch (ParseException e) {}
        if(stats!=null){
            for(StatTag stat:stats){
                list.add(new GenericItemObject(stat.getValue(), stat.getValue()+" ("+stat.getCount()+")"));
            }
        }
    }


}
