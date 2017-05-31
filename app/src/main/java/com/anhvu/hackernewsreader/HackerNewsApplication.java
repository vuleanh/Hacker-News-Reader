package com.anhvu.hackernewsreader;

import android.app.Application;
import android.content.Context;

import com.firebase.client.Firebase;

/**
 * Created by leanh on 5/30/17.
 */

public class HackerNewsApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Firebase.setAndroidContext(this);
    }

    public static Context getContext() {
        return context;
    }

}
