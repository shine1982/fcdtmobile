package com.facanditu.fcdtandroid;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by fengqin on 14/12/19.
 */
public class SharedPreferenceManager {

    private static String USER_PREF = "userpref";
    private static String ALREADY_LEARNED_DRAWER = "alreadyLearnedDrawer";

    public static void setUserAlreadyLearnedDrawer(Context context, boolean alreadyLearned){
        SharedPreferences.Editor editor = getUserPrefEditor(context);
        editor.putBoolean(ALREADY_LEARNED_DRAWER, alreadyLearned);
        editor.apply();
    }

    public static boolean getUserAlreadyLearnedDrawer(Context context){
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return sharedPreferences.getBoolean(ALREADY_LEARNED_DRAWER, false);
    }

    private static SharedPreferences.Editor getUserPrefEditor(Context context){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        return editor;
    }
    private static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences(USER_PREF,Context.MODE_PRIVATE);
    }



}
