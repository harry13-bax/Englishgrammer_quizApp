package com.ultimate.englishgrammarquiz;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by Legandan on 27/02/2021.
 */

public class MyApplications extends Application {
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
