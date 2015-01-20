package com.facanditu.fcdtandroid.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

/**
 * Created by fengqin on 14/12/19.
 */
public class SharedPreferenceManager {

    private static String USER_PREF = "userpref";
    private static String ALREADY_LEARNED_DRAWER = "alreadyLearnedDrawer";
    private static String DATABASE_LAST_UPDATE_DATE_IN_MILLE_SECONDE="dblastUpdateDate";
    private static long oneDayInMilleSecondes = 24*60*60*1000;

    public static void setUserAlreadyLearnedDrawer(Context context, boolean alreadyLearned){
        SharedPreferences.Editor editor = getUserPrefEditor(context);
        editor.putBoolean(ALREADY_LEARNED_DRAWER, alreadyLearned);
        editor.apply();
    }

    public static boolean getUserAlreadyLearnedDrawer(Context context){
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return sharedPreferences.getBoolean(ALREADY_LEARNED_DRAWER, false);
    }

    public static void setLastUpdateDatabaseDateWithToday(Context context){
        SharedPreferences.Editor editor = getUserPrefEditor(context);
        editor.putLong(DATABASE_LAST_UPDATE_DATE_IN_MILLE_SECONDE, new Date().getTime());
        editor.apply();
    }

    public static boolean isDbMoreThan1DayNotUpdated(Context context){
        final SharedPreferences sharedPreferences = getSharedPreferences(context);
        final long lasttimeInMilleSecondes = sharedPreferences.getLong(DATABASE_LAST_UPDATE_DATE_IN_MILLE_SECONDE,0);
        Date today = new Date();
        return (today.getTime()-lasttimeInMilleSecondes) >= oneDayInMilleSecondes;
    }

    private static SharedPreferences.Editor getUserPrefEditor(Context context){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        return editor;
    }
    private static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences(USER_PREF,Context.MODE_PRIVATE);
    }

}
