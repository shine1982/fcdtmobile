package com.facanditu.fcdtandroid.model;



import com.facanditu.fcdtandroid.util.StringUtils;
import com.parse.ParseObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by fengqin on 14/12/5.
 */
public class RestaurantBO implements Serializable {

    private String id;
    private String name;
    private String address;
    private String postalCode;
    private String city;
    private String telephone;

    private List<String> openTime;

    private List<String> price;

    private List<String> recReasons;

    private List<String> metros;

    public RestaurantBO(final Restaurant restaurant){
        this.id=restaurant.getObjectId();
        this.name=restaurant.getName();
        this.address=restaurant.getAddress();
        this.city=restaurant.getCity();
        this.postalCode = restaurant.getPostalCode();
        this.telephone=restaurant.getTelephone();
        this.openTime=initOpentimeFromResto(restaurant);
        this.price = initPriceFromResto(restaurant);
        this.recReasons = initRecReasons(restaurant);
        this.metros = initMetros(restaurant);

    }
    private static final String T_UNIT=":";
    private static final String TO="-";
    private List<String> initOpentimeFromResto(final Restaurant r){
        openTime = new ArrayList<>();
        openTime.add("中午"+r.getOpentimeNoonStartHour()+T_UNIT+r.getOpentimeNoonStartMin()+TO+r.getOpentimeNoonEndHour()+T_UNIT+r.getOpentimeNoonEndMin()
                +" 晚上"+r.getOpentimeEveningStartHour()+T_UNIT+r.getOpentimeEveningStartMin()+TO+r.getOpentimeEveningEndHour()+T_UNIT+r.getOpentimeEveningEndMin());

        if(!StringUtils.isEmpty(r.getOpentimeException())){
            openTime.add(r.getOpentimeException());
        }
        return openTime;

    }

    private List<String> initPriceFromResto(final Restaurant r){
        price = new ArrayList<>();
        price.add("中午 "+r.getPriceNoon()+"欧  "+"晚上 "+r.getPriceEvening() + "欧");
        if(!StringUtils.isEmpty(r.getPriceSpecial())){
            price.add(r.getPriceSpecial());
        }
        return price;
    }

    private List<String> initRecReasons(final Restaurant r){
        recReasons = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (String recR: r.getRecommandations()){
            sb.append(recR+" ");
        }
        recReasons.add(sb.toString());
        if(!StringUtils.isEmpty(r.getSpecialRecommandationReason())){
            recReasons.add(r.getSpecialRecommandationReason());
        }

        return recReasons;
    }

    private List<String> initMetros(final Restaurant r){

        metros = new ArrayList<>();
        List<Transportation> transportations = r.getTransportation();
        if(transportations!=null){
            for (Transportation transportation: transportations){
                metros.add(StringUtils.toString(transportation.getLines(),",")+" : "+transportation.getName());
            }
        }
        return metros;
    }
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getCompleteAddress(){
        return getAddress()+","+getPostalCode()+","+getCity()+",France";
    }

    public List<String> getFullOpenTime(){
        return openTime;
    }

    public List<String> getPrice(){ return price; }

    public List<String> getRecReasons(){
        return recReasons;
    }

    public List<String> getTransportations(){
        return this.metros;
    }

}
