package com.facanditu.fcdtandroid.screen;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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
import com.facanditu.fcdtandroid.model.DishItem;
import com.facanditu.fcdtandroid.model.DishItemHelper;
import com.facanditu.fcdtandroid.model.DishSelectableItem;
import com.facanditu.fcdtandroid.model.DishType;
import com.facanditu.fcdtandroid.util.StringUtils;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.Predicate;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ShowChosenDishesActivity extends GenericFcdtActivity{

    public static final String PARAM_DISHES_CHOSEN="dishesChosen";

    TextToSpeech ttobj;

    private ListView dishesChosenCn;
    private ListView dishesChosenFr;
    ArrayList<DishSelectableItem> completeDishItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_chosen_dishes);
        initToolbar();

        Bundle bundle = getIntent().getExtras();
        ArrayList<DishSelectableItem> dishItems= (ArrayList<DishSelectableItem>)
        bundle.getSerializable(PARAM_DISHES_CHOSEN);

        completeDishItems = DishItemHelper.getCompleteDishItems(dishItems);

        setFrAndCnChosenDishListView();

        setTextToSpeech();
    }

    private void setFrAndCnChosenDishListView(){
        dishesChosenCn = (ListView) findViewById(R.id.orderedDishListCn);
        dishesChosenFr = (ListView) findViewById(R.id.orderedDishListFr);

        dishesChosenCn.setAdapter(new ChosenDishItemAdapter(this,completeDishItems, DishLangMode.CN));
        dishesChosenCn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showTranslation(parent,position,DishLangMode.CN);
            }
        });
        dishesChosenFr.setAdapter(new ChosenDishItemAdapter(this,completeDishItems, DishLangMode.FR));
        dishesChosenFr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showTranslation(parent,position,DishLangMode.FR);
            }
        });
    }

    private void setTextToSpeech(){
        ttobj=new TextToSpeech(getApplicationContext(),
                new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if(status != TextToSpeech.ERROR){
                            ttobj.setLanguage(Locale.FRENCH);
                        }
                    }
                });
    }

    private void showTranslation(AdapterView<?> parent, int position, DishLangMode dishLangMode){

        final DishSelectableItem dishItem = (DishSelectableItem) parent.getItemAtPosition(position);
        Toast.makeText(this, dishItem.getInverseName(dishLangMode)
                + (dishItem.isTitle() ? "" : " " +
                dishItem.getInversePriceLabel(dishLangMode)), Toast.LENGTH_LONG).show();
    }

    @Override
    protected int getMenu() {
        return R.menu.menu_show_chosen_dishes;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
            case R.id.orderInFrench:
                orderInFrench();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void orderInFrench(){

        StringBuilder words = new StringBuilder();
        words.append("Bonjour! Je souhaite avoir s'il vous plait, ");

        for(final DishSelectableItem dishSelectableItem: completeDishItems){
            if(dishSelectableItem.isTitle()){
                words.append("comme "+dishSelectableItem.getFrName()+":");
            }else{
                words.append(dishSelectableItem.getSelectedCount() + " " + dishSelectableItem.getFrName()+", ");
            }
        }
        words.append("Merci!");
        ttobj.speak(words.toString(), TextToSpeech.QUEUE_FLUSH, null);
    }


    @Override
    public void onPause(){
        if(ttobj !=null){
            ttobj.stop();
            ttobj.shutdown();
        }
        super.onPause();
    }
}
class ChosenDishItemAdapter extends ArrayAdapter<DishSelectableItem> {

    private Context context;

    private List<DishSelectableItem> dishs;

    private DishLangMode dishLangMode;

    public ChosenDishItemAdapter(Context context, List<DishSelectableItem> dishs, DishLangMode dishLangMode) {

        super(context, R.layout.oneline_visu_dishchosen, dishs);
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
            v = inflater.inflate(R.layout.oneline_visu_dishchosen, parent, false);
        }

        final DishSelectableItem dishItem = this.dishs.get(position);

        final TextView textView =(TextView) v.findViewById(R.id.textOfItem);
        if (dishItem.isTitle()) {
            SpannableString dishesBlocTitle = new SpannableString(dishItem.getName(dishLangMode));
            dishesBlocTitle.setSpan(new UnderlineSpan(), 0, dishItem.getName(dishLangMode).length(), 0);
            textView.setText(dishesBlocTitle);
        } else {
            textView.setText("(X"+dishItem.getSelectedCount()+")"+dishItem.getName(dishLangMode));
        }

        return v;
    }


}
