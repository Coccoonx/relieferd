package com.lri.eeclocalizer.Utils;

import com.google.android.gms.maps.model.LatLng;
import com.lri.eeclocalizer.core.model.Parish;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class CoreUtils {

    public static double getDistance(LatLng location1, LatLng location2) {
        double theta = location1.longitude - location2.longitude;
        double dist = Math.sin(deg2rad(location1.latitude)) * Math.sin(deg2rad(location2.latitude)) + Math.cos(deg2rad(location1.latitude)) * Math.cos(deg2rad(location2.latitude)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public static Parish getMinLatLng(LatLng currentLatLong) {
        double distance = Double.MAX_VALUE;
        Parish correspondingParish = null;
        for (Parish p : Parish.values()) {

            double dist = getDistance(currentLatLong, p.getLatLng());

            if (distance > dist) {

                distance = dist;
                correspondingParish = p;
            }
        }
        return correspondingParish;
    }
}
