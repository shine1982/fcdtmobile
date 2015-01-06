package com.facanditu.fcdtandroid.model;

import com.facanditu.fcdtandroid.util.StringUtils;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.Predicate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengqin on 15/1/6.
 */
public class DishItemHelper {

    public static List<DishItem> convertDishListToDishItemList(List<Dish> dishs){
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

    public static String getPriceToShow(Dish dish){
        return dish.getPriceEuro()+(StringUtils.isEmpty(dish.getPriceCentimes())?"":"."+dish.getPriceCentimes());
    }
}
