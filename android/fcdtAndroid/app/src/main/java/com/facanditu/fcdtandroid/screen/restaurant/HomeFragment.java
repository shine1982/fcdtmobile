package com.facanditu.fcdtandroid.screen.restaurant;

/**
 * Created by fengqin on 15/1/3.
 */


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;

import com.facanditu.fcdtandroid.R;
import com.facanditu.fcdtandroid.model.Photo;
import com.facanditu.fcdtandroid.model.Restaurant;
import com.facanditu.fcdtandroid.model.RestaurantBO;
import com.facanditu.fcdtandroid.util.CirclePageIndicator;
import com.facanditu.fcdtandroid.util.NetworkUtils;
import com.facanditu.fcdtandroid.util.PageIndicator;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

public class HomeFragment extends Fragment {

    public static final String ARG_ITEM_ID = "home_fragment";

    private static final long ANIM_VIEWPAGER_DELAY = 5000;
    private static final long ANIM_VIEWPAGER_DELAY_USER_VIEW = 10000;

    // UI References
    private ViewPager mViewPager;
    PageIndicator mIndicator;

    AlertDialog alertDialog;

    List<Photo> photos;
    RequestImgTask task;
    boolean stopSliding = false;
    String message;

    private Runnable animateViewPager;
    private Handler handler;

    FragmentActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    private void sendRequest() {
        if (NetworkUtils.isConnectionAvailable(activity)) {
            task = new RequestImgTask(activity);
            // a refactoriser
            task.execute(((RestaurantBO)activity.getIntent().getSerializableExtra("resto")).getId());
        } else {
            message = getResources().getString(R.string.no_internet_connection);
            showAlertDialog(message, true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_viewer, container, false);
        findViewById(view);

        mIndicator.setOnPageChangeListener(new PageChangeListener());
        mViewPager.setOnPageChangeListener(new PageChangeListener());
        mViewPager.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction()) {

                    case MotionEvent.ACTION_CANCEL:
                        break;

                    case MotionEvent.ACTION_UP:
                        // calls when touch release on ViewPager
                        if (photos != null && photos.size() != 0) {
                            stopSliding = false;
                            runnable(photos.size());
                            handler.postDelayed(animateViewPager,
                                    ANIM_VIEWPAGER_DELAY_USER_VIEW);
                        }
                        break;

                    case MotionEvent.ACTION_MOVE:
                        // calls when ViewPager touch
                        if (handler != null && stopSliding == false) {
                            stopSliding = true;
                            handler.removeCallbacks(animateViewPager);
                        }
                        break;
                }
                return false;
            }
        });

        return view;
    }

    private void findViewById(View view) {
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        mIndicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
    }

    public void runnable(final int size) {
        handler = new Handler();
        animateViewPager = new Runnable() {
            public void run() {
                if (!stopSliding) {
                    if (mViewPager.getCurrentItem() == size - 1) {
                        mViewPager.setCurrentItem(0);
                    } else {
                        mViewPager.setCurrentItem(
                                mViewPager.getCurrentItem() + 1, true);
                    }
                    handler.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
                }
            }
        };
    }


    @Override
    public void onResume() {

        if (photos == null) {
            sendRequest();
        } else {
            mViewPager.setAdapter(new ImageSlideAdapter(activity, photos));

            mIndicator.setViewPager(mViewPager);
           // imgNameTxt.setText(""
             //       + ((Product) photos.get(mViewPager.getCurrentItem()))
              //      .getName());
            runnable(photos.size());
            //Re-run callback
            handler.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
        }
        super.onResume();
    }


    @Override
    public void onPause() {
        if (task != null)
            task.cancel(true);
        if (handler != null) {
            //Remove callback
            handler.removeCallbacks(animateViewPager);
        }
        super.onPause();
    }



    public void showAlertDialog(String message, final boolean finish) {
        alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (finish)
                            activity.finish();
                    }
                });
        alertDialog.show();
    }

    private class PageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {

                /*
                if (photos != null) {
                    imgNameTxt.setText(""
                            + ((Product) photos.get(mViewPager
                            .getCurrentItem())).getName());
                }*/
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private class RequestImgTask extends AsyncTask<String, Void, List<Photo>> {
        private final WeakReference<Activity> activityWeakRef;
        Throwable error;

        public RequestImgTask(Activity context) {
            this.activityWeakRef = new WeakReference<Activity>(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Photo> doInBackground(String... idRestos) {
            try {
                ParseQuery<Photo> query = Photo.getQuery();
                query.whereEqualTo("resto", Restaurant.newRestaurantWithoutData(idRestos[0]));
                return query.find();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }



        @Override
        protected void onPostExecute(List<Photo> result) {
            super.onPostExecute(result);

            if (activityWeakRef != null && !activityWeakRef.get().isFinishing()) {
                if (error != null && error instanceof IOException) {
                    message = getResources().getString(R.string.time_out);
                    showAlertDialog(message, true);
                } else if (error != null) {
                    message = getResources().getString(R.string.error_occured);
                    showAlertDialog(message, true);
                } else {
                    photos = result;
                    if (result != null) {
                        if (photos != null && photos.size() != 0) {

                            mViewPager.setAdapter(new ImageSlideAdapter(
                                    activity, photos));

                            mIndicator.setViewPager(mViewPager);

                            runnable(photos.size());
                            handler.postDelayed(animateViewPager,
                                    ANIM_VIEWPAGER_DELAY);
                        } else {

                        }
                    } else {
                    }
                }
            }
        }
    }
}