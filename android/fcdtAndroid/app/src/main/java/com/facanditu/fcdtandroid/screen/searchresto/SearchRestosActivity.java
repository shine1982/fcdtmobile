package com.facanditu.fcdtandroid.screen.searchresto;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.facanditu.fcdtandroid.R;
import com.facanditu.fcdtandroid.model.Restaurant;
import com.facanditu.fcdtandroid.model.RestaurantBO;
import com.facanditu.fcdtandroid.screen.restaurant.RestaurantMainActivity;
import com.facanditu.fcdtandroid.util.GeoUtils;
import com.facanditu.fcdtandroid.util.LocationUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.util.List;

public class SearchRestosActivity extends ActionBarActivity implements

        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static final String SEARCH_TYPE_NAME="searchType";
    public final static int
            CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    private Toolbar toolbar;

    private GoogleApiClient mGoogleApiClient;

    private String searchType;

    private Location mCurrentLocation;

    private View searchingBloc;

    private ListView restoListView;
    private static final String RESTO_LIST_VIEW="restoListView";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_restos);

            searchType=
                    getIntent().getExtras().getString(SEARCH_TYPE_NAME);

            toolbar = (Toolbar) findViewById(R.id.app_bar);
            setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            TextView textView = (TextView) findViewById(R.id.searchTypeTitle);
            textView.setText(searchType);

            buildGoogleApiClient();






    }
    protected synchronized void buildGoogleApiClient() {
        Log.i("GoogleApiClient", "Building GoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_restos, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(SearchRestosType.NearBy.name().equals(searchType)){// geoloc

            mGoogleApiClient.connect();
        }
    }


    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
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

                                ParseQuery<Restaurant> query = Restaurant.getQuery();
                                query.whereNear(Restaurant.LOCATION, GeoUtils.geoPointFromLocation(mCurrentLocation));
                                return query;
                            }
                        };

                final ParseQueryAdapter<Restaurant> adapter = new RestaurantParseQueryAdapter(this,factory,mCurrentLocation);

                adapter.setPaginationEnabled(true);
                adapter.setObjectsPerPage(10);
                adapter.addOnQueryLoadListener(new ParseQueryAdapter.OnQueryLoadListener<Restaurant>() {
                    public void onLoading() {
                        // Trigger any "loading" UI
                    }
                    @Override
                    public void onLoaded(List<Restaurant> restaurants, Exception e) {
                        hideSearchingBloc();
                    }

                });

                restoListView = (ListView) findViewById(R.id.restosList);
                restoListView.setAdapter(adapter);
                final Context ctxParam=this;
                restoListView.setOnItemClickListener(
                        new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                final Restaurant resto = adapter.getItem(i);
                                RestaurantBO restoBo = new RestaurantBO(resto);
                                Intent intent = new Intent(ctxParam, RestaurantMainActivity.class);
                                intent.putExtra(RestaurantMainActivity.RESTO, restoBo);
                                startActivity(intent);
                            }
                        }
                );
            }
        }
    }

    private void hideSearchingBloc(){
        searchingBloc = findViewById(R.id.loadingPanel);
        searchingBloc.setVisibility(View.GONE);
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
                        this,
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
                this,
                LocationUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST);

        // If Google Play services can provide an error dialog
        if (errorDialog != null) {

            // Create a new DialogFragment in which to show the error dialog
            ErrorDialogFragment errorFragment = new ErrorDialogFragment();

            // Set the dialog in the DialogFragment
            errorFragment.setDialog(errorDialog);

            // Show the error dialog in the DialogFragment
            errorFragment.show(this.getSupportFragmentManager(), LocationUtils.APPTAG);
        }
    }

    private boolean servicesConnected() {
        // Check that Google Play services is available
        int resultCode =
                GooglePlayServicesUtil.
                        isGooglePlayServicesAvailable(this);
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
                    this,
                    CONNECTION_FAILURE_RESOLUTION_REQUEST);

            // If Google Play services can provide an error dialog
            if (errorDialog != null) {
                // Create a new DialogFragment for the error dialog
                ErrorDialogFragment errorFragment =
                        new ErrorDialogFragment();
                // Set the dialog in the DialogFragment
                errorFragment.setDialog(errorDialog);
                // Show the error dialog in the DialogFragment
                errorFragment.show(this.getSupportFragmentManager(),
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
