package com.facanditu.fcdtandroid.util;

import android.location.Location;

import com.parse.ParseGeoPoint;

/**
 * Created by fengqin on 15/1/2.
 */
public class GeoUtils {

    public static ParseGeoPoint geoPointFromLocation(Location loc) {
        return new ParseGeoPoint(loc.getLatitude(), loc.getLongitude());
    }
}
