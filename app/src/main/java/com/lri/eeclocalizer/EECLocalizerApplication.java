package com.lri.eeclocalizer;

import android.app.Application;

import com.lri.eeclocalizer.Utils.UIUtils;

/**
 * Created by borelkoumo on 17/12/15.
 */
public class EECLocalizerApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Init Typeface (fonts of our app)
        UIUtils.initCustomTypefaces(this);
    }
}
