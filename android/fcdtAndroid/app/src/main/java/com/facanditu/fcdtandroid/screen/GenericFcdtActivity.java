package com.facanditu.fcdtandroid.screen;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facanditu.fcdtandroid.R;

/**
 * Created by fengqin on 15/1/6.
 */
public abstract class GenericFcdtActivity  extends ActionBarActivity {

    protected abstract int getMenu();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(getMenu(), menu);
        return true;
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

    protected Toolbar initToolbar(){
        final Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return toolbar;
    }

    protected void hideSearchingBloc(){
        View searchingBloc = findViewById(R.id.loadingPanel);
        searchingBloc.setVisibility(View.GONE);
    }
}
