package com.lri.eeclocalizer;

import android.support.multidex.MultiDexApplication;

import com.lri.eeclocalizer.utils.UIUtils;

/**
 * Created by borelkoumo on 17/12/15.
 */
public class EECLocalizerApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        // Init Typeface (fonts of our app)
        UIUtils.initCustomTypefaces(this);
    }
}
