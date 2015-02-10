package com.facanditu.fcdtandroid.model;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by fengqin on 14/11/13.
 */
@ParseClassName("Restaurant")
public class Restaurant extends ParseObject {

    public static final String FOREIGNE_KEY="resto";

    public static final String NAME="name";

    public static final String ADDRESS="address";
    public static final String POSTAL_CODE="postalCode";
    public static final String CITY="city";
    public static final String TELEPHONE="telephone";
    public static final String LOCATION="location";

    public static final String OPENTIME_NOON_START_HOUR="opentimeNoonStartHour";
    public static final String OPENTIME_NOON_START_MIN="opentimeNoonStartMin";
    public static final String OPENTIME_NOON_END_HOUR="opentimeNoonEndHour";
    public static final String OPENTIME_NOON_END_MIN="opentimeNoonEndMin";

    public static final String OPENTIME_EVENING_START_HOUR="opentimeEveningStartHour";
    public static final String OPENTIME_EVENING_START_MIN="opentimeEveningStartMin";
    public static final String OPENTIME_EVENING_END_HOUR="opentimeEveningEndHour";
    public static final String OPENTIME_EVENING_END_MIN="opentimeEveningEndMin";

    public static final String OPENTIME_EXCEPTION="opentimeException";

    public static final String PRICE_NOON="priceNoon";
    public static final String PRICE_EVENING="priceEvening";

    public static final String PRICE_SPECIAL="priceSpecial";

    public static final String TRANSPORTATION="metro";

    public static final String REC_REASONS="recReasons";

    public static final String TAG_LIST="tagList";

    private static final String LOCAL_RESULTS_NAME="localRestaurants";



    public String getName() {
        return getString(NAME);
    }

    public void setName(String name) {
        put(NAME,name);
    }

    public String getAddress() {
        return getString(ADDRESS);
    }

    public void setAddress(String address) {
        put(ADDRESS,address);
    }

    public String getPostalCode() {
        return getString(POSTAL_CODE);
    }

    public void setPostalCode(String postalCode) {
        put(POSTAL_CODE,postalCode);
    }

    public String getCity() {
        return getString(CITY);
    }

    public void setCity(String city) {
        put(CITY,city);
    }

    public String getTelephone() {
        return getString(TELEPHONE);
    }

    public void setTelephone(String telephone) {
        put(TELEPHONE,telephone);
    }

    public double getDistance(ParseGeoPoint geoPoint){
        return getParseGeoPoint(LOCATION).distanceInKilometersTo(geoPoint);
    }

    public String getDistanceLabel(ParseGeoPoint geoPoint){
        double distance = getDistance(geoPoint);
        BigDecimal bd = new BigDecimal(distance);
        int decimalPlaces = 3;  // the scale for the decimal
        bd = bd.setScale(decimalPlaces, BigDecimal.ROUND_HALF_UP);
        DecimalFormat decFormat = new DecimalFormat("#.00", new DecimalFormatSymbols(Locale.ENGLISH));
        double formatDecimal = new Double(decFormat.format(distance)).doubleValue();
        return formatDecimal+" km";
    }

    public static Restaurant newRestaurantWithoutData(final String idResto){
        return (Restaurant) ParseObject.createWithoutData("Restaurant", idResto);
    }

    private static ParseQuery<Restaurant> getQuery() {
        ParseQuery<Restaurant> query = ParseQuery.getQuery(Restaurant.class);
        query.include(TRANSPORTATION);
        query.include(REC_REASONS);
        return query;
    }

    public static ParseQuery<Restaurant> getQuery(boolean withLocalDb){
        if(withLocalDb){
            return getLocalQuery();
        }else {
            return getQuery();
        }
    }

    private static ParseQuery<Restaurant> getLocalQuery() {
        ParseQuery<Restaurant> query = getQuery();
        query.fromPin(LOCAL_RESULTS_NAME);
        return query;
    }

    public static boolean synchroLocalDb(){
        ParseQuery<Restaurant> query = Restaurant.getQuery();
        List<Restaurant> restaurants = null;
        try {
             restaurants = query.find();
        } catch (ParseException e) {}
        if(restaurants != null){
            try {
                Restaurant.unpinAll(LOCAL_RESULTS_NAME);
                Restaurant.pinAll(LOCAL_RESULTS_NAME, restaurants);
                Log.d(LOCAL_RESULTS_NAME,"synchroLocalDb succeed!");
            } catch (ParseException e) {
                return false;
            }
            return true;
        }else {
            return false;
        }
    }

    public static ParseQuery<Restaurant> getQueryFromStr(String str){

            ParseQuery<Restaurant> queryCodePostal = getQuery();
            queryCodePostal.whereMatches(POSTAL_CODE, "("+str+")", "i");

            ParseQuery<Restaurant> queryCity = getQuery();
            queryCity.whereMatches(CITY, "("+str+")", "i");

            ParseQuery<Restaurant> queryName = getQuery();
            queryName.whereMatches(NAME,"("+str+")", "i");

            final List<ParseQuery<Restaurant>> queries = new ArrayList<ParseQuery<Restaurant>>();
            queries.add(queryCodePostal);
            queries.add(queryCity);
            queries.add(queryName);
            return ParseQuery.or(queries);
    }

    public ParseGeoPoint getLocation(){
        return getParseGeoPoint(LOCATION);
    }

    public String getOpentimeNoonStartHour(){
        return getString(OPENTIME_NOON_START_HOUR);
    }

    public String getOpentimeNoonStartMin(){
        return getString(OPENTIME_NOON_START_MIN);
    }

    public String getOpentimeNoonEndHour(){
        return getString(OPENTIME_NOON_END_HOUR);
    }

    public String getOpentimeNoonEndMin(){
        return getString(OPENTIME_NOON_END_MIN);
    }

    public String getOpentimeEveningStartHour(){
        return getString(OPENTIME_EVENING_START_HOUR);
    }

    public String getOpentimeEveningStartMin(){
        return getString(OPENTIME_EVENING_START_MIN);
    }

    public String getOpentimeEveningEndHour(){
        return getString(OPENTIME_EVENING_END_HOUR);
    }

    public String getOpentimeEveningEndMin(){
        return getString(OPENTIME_EVENING_END_MIN);
    }

    public String getOpentimeException(){
        return getString(OPENTIME_EXCEPTION);
    }

    public int getPriceNoon(){
        return getInt(PRICE_NOON);
    }

    public int getPriceEvening(){
        return getInt(PRICE_EVENING);
    }

    public String getPriceSpecial(){
        return getString(PRICE_SPECIAL);
    }

    public List<String> getRecommandations(){
        List<RecReason> recReasons = getList(REC_REASONS);
        List<String> recStrings = new ArrayList<>();
        if(recReasons!=null){
            for(RecReason recReason:recReasons){
                recStrings.add(recReason.getLabel());
            }
        }
        return recStrings;
    }

    public String getSpecialRecommandationReason(){
        return getString("recommandationReason");
    }

    public List<Transportation> getTransportation(){
        return getList(TRANSPORTATION);
    }


}
