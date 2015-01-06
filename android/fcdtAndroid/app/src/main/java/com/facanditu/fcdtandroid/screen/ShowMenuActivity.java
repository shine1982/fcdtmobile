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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facanditu.fcdtandroid.R;
import com.facanditu.fcdtandroid.model.Dish;
import com.facanditu.fcdtandroid.model.DishItem;
import com.facanditu.fcdtandroid.model.DishType;
import com.facanditu.fcdtandroid.model.Restaurant;
import com.facanditu.fcdtandroid.util.StringUtils;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.Predicate;

import java.util.ArrayList;
import java.util.List;


public class ShowMenuActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private ListView menuListView;

    public static final String ID_RESTO="idResto";
    public static final String LANG="Lang";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_menu);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        menuListView = (ListView) findViewById(R.id.dishList);
        Bundle bundle = getIntent().getExtras();
        final String idResto = bundle.getString(ID_RESTO);
        final String lang = bundle.getString(LANG);
        final Context thisActivity = this;

        final ParseQuery<Dish> dishParseQuery = Dish.getQuery();
        dishParseQuery.whereEqualTo(Restaurant.FOREIGNE_KEY, Restaurant.newRestaurantWithoutData(idResto));

        dishParseQuery.findInBackground(new FindCallback<Dish>() {
            @Override
            public void done(List<Dish> dishs, ParseException e) {
                final List<DishItem> dishItems = getDishItemList(dishs);
                final DishLangMode dishLangMode = DishLangMode.valueOf(lang);
                DishItemAdapter dishItemAdapter = new DishItemAdapter(thisActivity, dishItems, dishLangMode);
                menuListView.setAdapter(dishItemAdapter);
                menuListView.setClickable(true);
                menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final DishItem dishItem = dishItems.get(position);
                        Toast.makeText(thisActivity, dishItem.getInverseName(dishLangMode)
                                +(dishItem.isTitle()?"":" "+
                                dishItem.getInversePriceLabel(dishLangMode)),Toast.LENGTH_LONG).show();
                    }
                });
                hideSearchingBloc();
            }
        });


    }

    private List<DishItem> getDishItemList(List<Dish> dishs){

        List<DishItem> dishItems=new ArrayList<>();

        DishType[] dishTypes = DishType.values();
        for (final DishType dishType: dishTypes){

            dishItems.add(new DishItem(dishType.getCnName(),dishType.getFrName(),"", true));


            final List<Dish> dishsTemp =
                    ListUtils.select(dishs, new Predicate<Dish>() {
                @Override
                public boolean evaluate(Dish dish) {
                    return dishType.getType().equals(dish.getDishType());
                }
            });

            for (final Dish dish : dishsTemp){
                dishItems.add(new DishItem(dish.getCnName(),dish.getFrName(),getPriceToShow(dish),false));
            }
        }
        return dishItems;
    }

    private String getPriceToShow(Dish dish){
        return dish.getPriceEuro()+(StringUtils.isEmpty(dish.getPriceCentimes())?"":"."+dish.getPriceCentimes());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
    private void hideSearchingBloc(){
        View searchingBloc = findViewById(R.id.loadingPanel);
        searchingBloc.setVisibility(View.GONE);
    }
}

class DishItemAdapter extends ArrayAdapter<DishItem> {

    private Context context;

    private List<DishItem> dishs;

    private DishLangMode dishLangMode;

    public DishItemAdapter(Context context, List<DishItem> dishs, DishLangMode dishLangMode) {

        super(context, R.layout.oneline_dish_item, dishs);
        this.context = context;
        this.dishs = dishs;
        this.dishLangMode = dishLangMode;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View v = convertView;
        if(v==null){
            v = inflater.inflate(R.layout.oneline_dish_item, parent, false);
        }

        DishItem dishItem = this.dishs.get(position);
        TextView t1 = (TextView) v.findViewById(R.id.dishName);

        if(dishItem.isTitle()){
            SpannableString dishesBlocTitle= new SpannableString(dishItem.getName(dishLangMode));
            dishesBlocTitle.setSpan(new UnderlineSpan(), 0, dishItem.getName(dishLangMode).length(), 0);
            t1.setText(dishesBlocTitle);
        }else{
            t1.setText(dishItem.getName(dishLangMode));
        }
        TextView t2 = (TextView) v.findViewById(R.id.dishPrice);
        if(!StringUtils.isEmpty(dishItem.getPrice())){
            t2.setText(dishItem.getPriceLabel(dishLangMode));
        }else {
            t2.setText("");
        }

        return v;
    }



}