package com.facanditu.fcdtandroid.screen.searchresto;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.facanditu.fcdtandroid.R;
import com.facanditu.fcdtandroid.model.Restaurant;
import com.facanditu.fcdtandroid.model.RestaurantBO;
import com.facanditu.fcdtandroid.screen.restaurant.RestaurantMainActivity;
import com.facanditu.fcdtandroid.util.GeoUtils;
import com.facanditu.fcdtandroid.util.LocalDbIndicator;
import com.facanditu.fcdtandroid.util.LocationUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.util.List;

/**
 * Created by fengqin on 15/1/6.
 */
public class GeoLocHelper implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private GoogleApiClient mGoogleApiClient;
    private SearchRestosActivity context;
    private Location mCurrentLocation;
    private ListView restoListView;
    public final static int
            CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    public synchronized void buildGoogleApiClient() {
        Log.i("GoogleApiClient", "Building GoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    public GeoLocHelper(SearchRestosActivity context){
        this.context = context;
        buildGoogleApiClient();
    }

    public void connect(){
        mGoogleApiClient.connect();
    }

    public void disconnect(){
        mGoogleApiClient.disconnect();
    }



    @Override
    public void onConnected(Bundle bundle) {
        Log.i("GoogleApiClient", "Connected to GoogleApiClient");
        if(mCurrentLocation==null){
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if(servicesConnected()){

                ParseQueryAdapter.QueryFactory<Restaurant> factory =
                        new ParseQueryAdapter.QueryFactory<Restaurant>() {
                            public ParseQuery<Restaurant> create() {

                                ParseQuery<Restaurant> query = Restaurant.getQuery(LocalDbIndicator.getIns().isSyncSuccess());
                                query.whereNear(Restaurant.LOCATION, GeoUtils.geoPointFromLocation(mCurrentLocation));
                                return query;
                            }
                        };

                final ParseQueryAdapter<Restaurant> adapter = new RestaurantParseQueryAdapter(context,factory,mCurrentLocation);

                adapter.setPaginationEnabled(true);
                adapter.setObjectsPerPage(10);
                adapter.addOnQueryLoadListener(new ParseQueryAdapter.OnQueryLoadListener<Restaurant>() {
                    public void onLoading() {
                        // Trigger any "loading" UI
                    }
                    @Override
                    public void onLoaded(List<Restaurant> restaurants, Exception e) {
                        context.hideSearchingBloc();
                    }

                });

                restoListView = (ListView) context.findViewById(R.id.restosList);
                restoListView.setAdapter(adapter);

                restoListView.setOnItemClickListener(
                        new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                final Restaurant resto = adapter.getItem(i);
                                RestaurantBO restoBo = new RestaurantBO(resto);
                                Intent intent = new Intent(context, RestaurantMainActivity.class);
                                intent.putExtra(RestaurantMainActivity.RESTO, restoBo);
                                context.startActivity(intent);
                            }
                        }
                );
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }



    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
/*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {
            try {

                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(
                        context,
                        LocationUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST);

                /*
                * Thrown if Google Play services canceled the original
                * PendingIntent
                */

            } catch (IntentSender.SendIntentException e) {

                // Log the error
                e.printStackTrace();
            }
        } else {

            // If no resolution is available, display a dialog to the user with the error.
            showErrorDialog(connectionResult.getErrorCode());
        }
    }

    private void showErrorDialog(int errorCode) {

        // Get the error dialog from Google Play services
        Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
                errorCode,
                context,
                LocationUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST);

        // If Google Play services can provide an error dialog
        if (errorDialog != null) {

            // Create a new DialogFragment in which to show the error dialog
            ErrorDialogFragment errorFragment = new ErrorDialogFragment();

            // Set the dialog in the DialogFragment
            errorFragment.setDialog(errorDialog);

            // Show the error dialog in the DialogFragment
            errorFragment.show(context.getSupportFragmentManager(), LocationUtils.APPTAG);
        }
    }

    private boolean servicesConnected() {
        // Check that Google Play services is available
        int resultCode =
                GooglePlayServicesUtil.
                        isGooglePlayServicesAvailable(context);
        // If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode) {
            // In debug mode, log the status
            Log.d("Location Updates",
                    "Google Play services is available.");
            // Continue
            return true;
            // Google Play services was not available for some reason.
            // resultCode holds the error code.
        } else {
            // Get the error dialog from Google Play services
            Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
                    resultCode,
                    context,
                    CONNECTION_FAILURE_RESOLUTION_REQUEST);

            // If Google Play services can provide an error dialog
            if (errorDialog != null) {
                // Create a new DialogFragment for the error dialog
                ErrorDialogFragment errorFragment =
                        new ErrorDialogFragment();
                // Set the dialog in the DialogFragment
                errorFragment.setDialog(errorDialog);
                // Show the error dialog in the DialogFragment
                errorFragment.show(context.getSupportFragmentManager(),
                        "Location Updates");

            }
        }
        return false;
    }

    public static class ErrorDialogFragment extends DialogFragment {
        // Global field to contain the error dialog
        private Dialog mDialog;
        // Default constructor. Sets the dialog field to null
        public ErrorDialogFragment() {
            super();
            mDialog = null;
        }
        // Set the dialog to display
        public void setDialog(Dialog dialog) {
            mDialog = dialog;
        }
        // Return a Dialog to the DialogFragment.
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return mDialog;
        }
    }
}
