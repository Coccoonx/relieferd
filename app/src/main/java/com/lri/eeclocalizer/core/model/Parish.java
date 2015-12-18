package com.lri.eeclocalizer.core.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Lyonnel T. DZOTANG on 14/12/2015.
 */
public enum Parish {
    TAMDJA(1, "Paroisse de Tamdja", 5.4668672, 10.4164029),
    BAFOUSSAMPLATEAU(2, "Paroisse de Bafoussam Plateau", 5.4819165, 10.4193172);

    private int id;
    private String displayName;
    private LatLng location;

    Parish(int id, String displayName, double latitude, double longitude) {
        this.id = id;
        this.displayName = displayName;
        this.location = new LatLng(latitude, longitude);
    }

    Parish[] getAllParish() {
        return Parish.values();
    }

    public LatLng getLatLng() {
        return this.location;
    }

    public int getId() {
        return this.id;
    }

    public String getDisplayName() {
        return this.displayName;
    }

}
