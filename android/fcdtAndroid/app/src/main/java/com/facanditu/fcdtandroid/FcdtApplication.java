package com.facanditu.fcdtandroid;

import android.app.Application;

import com.facanditu.fcdtandroid.model.Restaurant;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;



/**
 * Created by fengqin on 14/11/13.
 */
public class FcdtApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Restaurant.class);
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(this, "iwGsOVQf75GczDN5rbPWBnpVjRnihfldbFx7wpYq", "wKBBV1YRfnwRp1Uv8nnuSYmTj8R174SEbi2vOjVz");

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        // If you would like all objects to be private by default, remove this line.
        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);


    }
}
