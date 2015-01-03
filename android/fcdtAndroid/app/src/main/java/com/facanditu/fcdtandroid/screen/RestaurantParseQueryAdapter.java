package com.facanditu.fcdtandroid.screen;

import android.content.Context;
import android.location.Location;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facanditu.fcdtandroid.R;
import com.facanditu.fcdtandroid.model.Restaurant;
import com.facanditu.fcdtandroid.util.GeoUtils;
import com.parse.ParseQueryAdapter;

/**
 * Created by fengqin on 15/1/2.
 */
class RestaurantParseQueryAdapter extends ParseQueryAdapter<Restaurant>{

        private Location location;

        public RestaurantParseQueryAdapter(Context context, ParseQueryAdapter.QueryFactory<Restaurant> queryFactory, Location location) {
            super(context, queryFactory);
            this.location = location;
        }

        @Override
        public View getItemView(Restaurant restaurant, View view, ViewGroup parent) {
            if (view == null) {
                view = View.inflate(getContext(), R.layout.one_resto_line, null);
            }

            TextView restoName = (TextView) view.findViewById(R.id.restoNameTextView);

            restoName.setText(restaurant.getName());
            TextView distanceResto = (TextView) view.findViewById(R.id.distanceResto);

            if(location!=null){
                distanceResto.setText(restaurant.getDistanceLabel(GeoUtils.geoPointFromLocation(location)));
            }
            return view;
        }
}
