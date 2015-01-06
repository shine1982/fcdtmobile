package com.facanditu.fcdtandroid.screen.restaurant;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facanditu.fcdtandroid.R;
import com.facanditu.fcdtandroid.model.RestaurantBO;
import com.facanditu.fcdtandroid.screen.DishLangMode;
import com.facanditu.fcdtandroid.screen.OrderDishesActivity;
import com.facanditu.fcdtandroid.screen.ShowMenuActivity;
import com.facanditu.fcdtandroid.screen.searchresto.RestaurantBoHelper;
import com.facanditu.fcdtandroid.util.FavRestoManager;

import org.w3c.dom.Text;

import java.util.List;


/**
 * A simple {@link android.app.Fragment} subclass.
 */
public class ShowRestoFragment extends Fragment {

    private RestaurantBO restaurant;

    private ImageButton favoriteRestoButton;
    private ImageButton callButton;
    private ImageButton openMapButton;
    private ImageButton orderDishesButton;

    private TextView restoNameTV;
    private TextView restoAddressTV;
    private LinearLayout opentime;
    private LinearLayout price;
    private LinearLayout recReasons;

    private TextView cnMenu;
    private TextView frMenu;

    private RestaurantBoHelper restaurantBoHelper;

    private View view;
    public ShowRestoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view = inflater.inflate(R.layout.fragment_show_resto, container, false);
       return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        restaurant = restaurantBoHelper.getResto();
        updateUI();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            restaurantBoHelper = (RestaurantBoHelper) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        restaurantBoHelper = null;
    }


    public void updateUI(){
        restoNameTV = (TextView) view.findViewById(R.id.restoNameLabel);
        restoAddressTV = (TextView) view.findViewById(R.id.restoAddress);
        restoNameTV.setText(restaurant.getName());
        restoAddressTV.setText(restaurant.getAddress() +", "+restaurant.getPostalCode()+", "+restaurant.getCity());

        setFavoriteButton();
        setCallButton();
        setOpenMapButton();
        setOrderDishesButton();
        setOpenTime();
        setPrice();
        setRecReasons();
        setChAndFrMenuClickAction();
    }
    private void setFavoriteButton(){
        final FavRestoManager favRestoManager =  FavRestoManager.getInstance(getActivity());
        favoriteRestoButton = (ImageButton) view.findViewById(R.id.favoriteRestoButton);
        boolean ifFav = favRestoManager.isRestoFav(restaurant.getId());
        favoriteRestoButton.setImageResource(ifFav?R.drawable.ic_like_50:R.drawable.ic_like_outline_50);

        favoriteRestoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                final String idResto = restaurant.getId();
                boolean isFavNow = false;
                if(favRestoManager.isRestoFav(idResto)){
                    favRestoManager.removeFavResto(idResto);
                }else{
                    favRestoManager.addFavResto(idResto);
                    isFavNow = true;
                }
                favoriteRestoButton.setImageResource(isFavNow?R.drawable.ic_like_50:R.drawable.ic_like_outline_50);

            }

        });
    }

    private void setCallButton(){
        callButton = (ImageButton) view.findViewById(R.id.callResto);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + restaurant.getTelephone()));
                startActivity(callIntent);
            }
        });
    }

    private void setOpenMapButton(){
        openMapButton =(ImageButton) view.findViewById(R.id.mapToResto);
        openMapButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String encodedString = Uri.encode(restaurant.getCompleteAddress());
                Uri geoLocation = Uri.parse("geo:0,0?q=" + encodedString);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(geoLocation);
                startActivity(intent);
            }
        });
    }

    private void setOrderDishesButton(){
        orderDishesButton = (ImageButton)view.findViewById(R.id.orderDishes);
        orderDishesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OrderDishesActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setOpenTime(){
        opentime=(LinearLayout)view.findViewById(R.id.opentime);
        addTextViewToLinerLayout( restaurant.getFullOpenTime() , opentime);
    }

    private void setPrice(){
        price=(LinearLayout)view.findViewById(R.id.price);
        addTextViewToLinerLayout( restaurant.getPrice(), price);
    }

    private void setRecReasons(){
        recReasons = (LinearLayout)view.findViewById(R.id.recReason);
        addTextViewToLinerLayout( restaurant.getRecReasons() , recReasons);
    }

    private void addTextViewToLinerLayout(final List<String> strings, final LinearLayout linearLayout){
        for(final String opentimestr : strings ){
            ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            final TextView tv=new TextView(this.getActivity());
            tv.setLayoutParams(lparams);
            tv.setText(opentimestr);
            linearLayout.addView(tv);
        }
    }

    private void setChAndFrMenuClickAction(){
        cnMenu = (TextView) view.findViewById(R.id.menuCnBtn);
        frMenu = (TextView) view.findViewById(R.id.menuFrBtn);

        cnMenu.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startMenuActivity(DishLangMode.CN);
                }
            }
        );
        frMenu.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMenuActivity(DishLangMode.FR);
            }
        });
    }

    private void startMenuActivity(final DishLangMode dishLangMode){
        Intent intent = new Intent(getActivity(), ShowMenuActivity.class);
        intent.putExtra(ShowMenuActivity.ID_RESTO, restaurant.getId());
        intent.putExtra(ShowMenuActivity.LANG, dishLangMode.name());
        startActivity(intent);
    }


}
