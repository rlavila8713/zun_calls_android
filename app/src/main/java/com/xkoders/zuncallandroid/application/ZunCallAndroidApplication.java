package com.xkoders.zuncallandroid.application;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.xkoders.zuncallandroid.models.ZunCallUser;


public class ZunCallAndroidApplication extends Application {
    public static int keyDel = 0;
    public static final String TAG = "DEFAULT";
    public static ZunCallAndroidApplication instance;
    public static RequestQueue mRequestQueue;
    public static ZunCallUser userLogged;

    public static synchronized ZunCallAndroidApplication getInstance() {
        return instance;
    }

    public static ZunCallAndroidApplication getInstance(Context context) {
        return (ZunCallAndroidApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

    }

    public ZunCallUser getUserLogged() {
        return ZunCallAndroidApplication.userLogged;
    }

    public void setUserLogged(ZunCallUser value) {
        ZunCallAndroidApplication.userLogged = value;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(this.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request, String tag) {
        request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(request);
    }

    public <T> void addToRequestQueue(Request<T> request) {
        request.setTag(TAG);
        getRequestQueue().add(request);
    }

    public void cancelPendingRequest(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }

    }
}
