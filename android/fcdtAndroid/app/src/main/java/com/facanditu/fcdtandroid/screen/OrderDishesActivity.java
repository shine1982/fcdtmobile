package com.facanditu.fcdtandroid.screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facanditu.fcdtandroid.R;
import com.facanditu.fcdtandroid.model.Dish;
import com.facanditu.fcdtandroid.model.DishItemHelper;
import com.facanditu.fcdtandroid.model.DishSelectableItem;
import com.facanditu.fcdtandroid.util.LocalDbIndicator;
import com.facanditu.fcdtandroid.util.StringUtils;
import com.parse.FindCallback;
import com.parse.ParseException;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.Predicate;

import java.util.ArrayList;
import java.util.List;

public class OrderDishesActivity extends GenericFcdtActivity {

    public static final String ID_RESTO="idResto";

    private ListView menuListView;
    private List<DishSelectableItem> dishItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_dishes);
        initToolbar();

        menuListView = (ListView) findViewById(R.id.orderDishList);
        Bundle bundle = getIntent().getExtras();
        final String idResto = bundle.getString(ID_RESTO);

        final Context thisActivity = this;

        Dish.getQuery(idResto, LocalDbIndicator.getIns().isSyncSuccess()).findInBackground(new FindCallback<Dish>() {
            @Override
            public void done(List<Dish> dishs, ParseException e) {
                dishItems = DishItemHelper.convertDishListToDishSelectabelItemList(dishs);
                final DishLangMode dishLangMode = DishLangMode.CN;
                DishSelectableItemAdapter dishItemAdapter = new DishSelectableItemAdapter(thisActivity, dishItems, dishLangMode);
                menuListView.setAdapter(dishItemAdapter);
                hideSearchingBloc();
            }
        });
    }

    @Override
    protected int getMenu(){
        return R.menu.menu_order_dishes;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;

            case R.id.action_ok_for_chosen_dishes:
                okForOrderdish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void okForOrderdish(){

        List<DishSelectableItem> dishesChosen =
        ListUtils.select(dishItems, new Predicate<DishSelectableItem>() {
            @Override
            public boolean evaluate(DishSelectableItem dishSelectableItem) {
                return dishSelectableItem.getSelectedCount()>0;
            }
        });

        if(dishesChosen.size()==0){
            Toast.makeText(this,"请选择您要点的菜，点击菜名加1，点击价格减1",Toast.LENGTH_LONG).show();
        }else{
            Intent intent = new Intent(this, ShowChosenDishesActivity.class);
            Bundle bundle=new Bundle();
            bundle.putSerializable(ShowChosenDishesActivity.PARAM_DISHES_CHOSEN,
                    new ArrayList<>(dishesChosen));
            intent.putExtras(bundle);
            startActivity(intent);
        }

    }

}
class DishSelectableItemAdapter extends ArrayAdapter<DishSelectableItem> {

    private Context context;

    private List<DishSelectableItem> dishs;

    private DishLangMode dishLangMode;

    public DishSelectableItemAdapter(Context context, List<DishSelectableItem> dishs, DishLangMode dishLangMode) {

        super(context, R.layout.oneline_order_dish_choosing, dishs);
        this.context = context;
        this.dishs = dishs;
        this.dishLangMode = dishLangMode;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View v = convertView;
        if (v == null) {
            v = inflater.inflate(R.layout.oneline_order_dish_choosing, parent, false);
        }

        final DishSelectableItem dishItem = this.dishs.get(position);

        final TextView dishNumberChosen =(TextView) v.findViewById(R.id.dishChosenNumber);


        TextView t1 = (TextView) v.findViewById(R.id.dishName);

        if (dishItem.isTitle()) {
            SpannableString dishesBlocTitle = new SpannableString(dishItem.getName(dishLangMode));
            dishesBlocTitle.setSpan(new UnderlineSpan(), 0, dishItem.getName(dishLangMode).length(), 0);
            dishNumberChosen.setText(dishesBlocTitle);
            dishNumberChosen.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            t1.setText("");
        } else {
            t1.setText(dishItem.getName(dishLangMode));
            dishNumberChosen.setText(""+dishItem.getSelectedCount());
            dishNumberChosen.setTextColor(context.getResources().getColor(R.color.colorAccent));
            t1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dishItem.setSelectedCount(dishItem.getSelectedCount() + 1);
                    dishNumberChosen.setText("" + dishItem.getSelectedCount());
                }
            });

        }


        TextView t2 = (TextView) v.findViewById(R.id.dishPrice);
        if (!StringUtils.isEmpty(dishItem.getPrice())) {
            t2.setText(dishItem.getPriceLabel(dishLangMode));
        } else {
            t2.setText("");
        }
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dishItem.getSelectedCount()>0){
                    dishItem.setSelectedCount(dishItem.getSelectedCount()-1);
                    dishNumberChosen.setText(""+dishItem.getSelectedCount());
                }
            }
        });

        return v;
    }


}


