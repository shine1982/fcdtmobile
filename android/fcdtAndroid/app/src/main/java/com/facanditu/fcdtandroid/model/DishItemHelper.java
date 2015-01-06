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

            dishItems.add(new DishItem(dishType.getCnName(),dishType.getFrName(),"","", true));


            final List<Dish> dishsTemp =
                    ListUtils.select(dishs, new Predicate<Dish>() {
                        @Override
                        public boolean evaluate(Dish dish) {
                            return dishType.getType().equals(dish.getDishType());
                        }
                    });

            for (final Dish dish : dishsTemp){
                dishItems.add(new DishItem(dish.getCnName(),dish.getFrName(),getPriceToShow(dish),dish.getDishType(),false));
            }
        }
        return dishItems;
    }

    public static String getPriceToShow(Dish dish){
        return dish.getPriceEuro()+(StringUtils.isEmpty(dish.getPriceCentimes())?"":"."+dish.getPriceCentimes());
    }

    public static List<DishSelectableItem> convertDishListToDishSelectabelItemList(List<Dish> dishs){
        List<DishSelectableItem> dishItems=new ArrayList<>();

        DishType[] dishTypes = DishType.values();
        for (final DishType dishType: dishTypes){

            dishItems.add(new DishSelectableItem(dishType.getCnName(),dishType.getFrName(),"","", true,0));


            final List<Dish> dishsTemp =
                    ListUtils.select(dishs, new Predicate<Dish>() {
                        @Override
                        public boolean evaluate(Dish dish) {
                            return dishType.getType().equals(dish.getDishType());
                        }
                    });

            for (final Dish dish : dishsTemp){
                dishItems.add(new DishSelectableItem(dish.getCnName(),dish.getFrName(),getPriceToShow(dish),dish.getDishType(),false,0));
            }
        }
        return dishItems;
    }


    public static ArrayList<DishSelectableItem> getCompleteDishItems(ArrayList<DishSelectableItem> dishItems){
        ArrayList<DishSelectableItem> cplDishItems = new ArrayList<>();
        DishType[] dishTypes = DishType.values();

        for(final DishType dishType : dishTypes){
            List<DishSelectableItem> listTemp=
                    ListUtils.select(dishItems, new Predicate<DishSelectableItem>() {
                        @Override
                        public boolean evaluate(DishSelectableItem dishSelectableItem) {
                            return dishType.getType().equals(dishSelectableItem.getType());
                        }
                    });
            if(listTemp.size()>0){
                    cplDishItems.add(new DishSelectableItem(dishType.getCnName(),dishType.getFrName(),"","", true,0));
                    cplDishItems.addAll(listTemp);
            }
        }

        return cplDishItems;
    }
}
