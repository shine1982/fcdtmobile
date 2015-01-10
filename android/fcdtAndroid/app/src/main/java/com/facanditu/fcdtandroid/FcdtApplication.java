package com.facanditu.fcdtandroid;

import android.app.Application;

import com.facanditu.fcdtandroid.model.Dish;
import com.facanditu.fcdtandroid.model.Photo;
import com.facanditu.fcdtandroid.model.Restaurant;
import com.facanditu.fcdtandroid.model.Transportation;
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
        ParseObject.registerSubclass(Photo.class);
        ParseObject.registerSubclass(Dish.class);
        ParseObject.registerSubclass(Transportation.class);
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
       //DEV
        Parse.initialize(this, "iwGsOVQf75GczDN5rbPWBnpVjRnihfldbFx7wpYq", "wKBBV1YRfnwRp1Uv8nnuSYmTj8R174SEbi2vOjVz");
        //PROD
        //Parse.initialize(this, "5vMNOUenvBCpf5y8biVWDFcdkDks40WdwV8T7JrA", "o0EoYMF7RMkX00MmxTtp78G7xCUbupO3PFrNStyI");
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        // If you would like all objects to be private by default, remove this line.
        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);


    }
}
