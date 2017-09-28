package com.xkoders.zuncallandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.xkoders.zuncallandroid.constants.SHARED_PREF_IDS;
import com.xkoders.zuncallandroid.constants.URLS;

import java.util.Locale;


public class LocalPreferences {

    public static final String tag = "LocalPreferences";
    public static final String LASTLECTURE = "LASTLECTURE";

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("LocalPreferences", Context.MODE_PRIVATE);
    }

    public static void saveLanguageCode(Context ctx, String languageCode) {
        Editor edit = PreferenceManager.getDefaultSharedPreferences(ctx).edit();
        edit.putString("languageCode", languageCode);
        edit.apply();
    }

    public static void saveLanguageName(Context ctx, String languageName) {
        Editor edit = PreferenceManager.getDefaultSharedPreferences(ctx).edit();
        edit.putString("languageName", languageName);
        edit.apply();
    }

    public static String getLanguageCode(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getString("languageCode", Locale.getDefault().getLanguage());
    }

    public static String getLanguageName(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getString("languageName", Locale.getDefault().getDisplayLanguage());
    }

    public static void saveAppDescription(Context ctx, String app, String description) {
        Editor edit = PreferenceManager.getDefaultSharedPreferences(ctx).edit();
        edit.putString(app + "_desc", description);
        edit.apply();
    }

    public static void saveAppName(Context ctx, String app, String name) {
        Editor edit = PreferenceManager.getDefaultSharedPreferences(ctx).edit();
        edit.putString(app + "_name", name);
        edit.apply();
    }


    public static String getAppDescription(Context ctx, String app) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getString(app + "_desc", "");
    }


    public static String getAppName(Context ctx, String app) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getString(app + "_name", "");
    }

    public static void saveApp(Context ctx, String app) {
        Editor edit = PreferenceManager.getDefaultSharedPreferences(ctx).edit();
        edit.putBoolean(app + getLanguageCode(ctx), true);
        edit.apply();
    }

    public static boolean getApp(Context ctx, String app) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean(app + getLanguageCode(ctx), false);
    }

    public static boolean getFirstTimeLaunched(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean(SHARED_PREF_IDS.FIRST_TIME_LAUNCHED, true);
    }

    public static boolean getShareInfo(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean("shareInfo", false);
    }

    public static int getCategorySelected(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getInt("pos", 0);
    }

    public static void saveLastLecture(Context ctx, String key, String value) {
        final SharedPreferences prefs = getSharedPreferences(ctx);
        Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static Integer getLastLecture(Context ctx, String key) {
        final SharedPreferences prefs = getSharedPreferences(ctx);
        String value = prefs.getString(key, String.valueOf(0));
        return Integer.valueOf(value);
    }

    public static String getApiUrl(Context ctx, String key) {
        final SharedPreferences prefs = getSharedPreferences(ctx);
        return prefs.getString(key, URLS.API_URL2);
    }

    public static String getAllCallsUrl(Context ctx, String key) {
        final SharedPreferences prefs = getSharedPreferences(ctx);
        return prefs.getString(key, URLS.GET_ALL_CALLS2);
    }

    public static String getLoginUrl(Context ctx, String key) {
        final SharedPreferences prefs = getSharedPreferences(ctx);
        return prefs.getString(key, URLS.LOGIN2);
    }

    public static String getDateToQuery(Context ctx, String key) {
        final SharedPreferences prefs = getSharedPreferences(ctx);
        return prefs.getString(key, Utils.getCurrentDate());
    }

    public static boolean isCachePreferred(Context ctx, String key) {
        final SharedPreferences prefs = getSharedPreferences(ctx);
        return prefs.getBoolean(key, true);
    }

    public static boolean isDataRemebered(Context ctx, String key) {
        final SharedPreferences prefs = getSharedPreferences(ctx);
        return prefs.getBoolean(key, false);
    }

    public static void saveApiUrl(Context ctx, String key, String value) {
        final SharedPreferences prefs = getSharedPreferences(ctx);
        Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void saveGetAllCalls(Context ctx, String key, String value) {
        final SharedPreferences prefs = getSharedPreferences(ctx);
        Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void saveLoginUrl(Context ctx, String key, String value) {
        final SharedPreferences prefs = getSharedPreferences(ctx);
        Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void setCachePreferred(Context ctx, String key, Boolean value) {
        final SharedPreferences prefs = getSharedPreferences(ctx);
        Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void setRememberData(Context ctx, String key, Boolean value, String userName) {
        final SharedPreferences prefs = getSharedPreferences(ctx);
        Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        saveUserNameData(ctx, SHARED_PREF_IDS.USER_NAME_DATA, userName);
        editor.apply();
    }


    private static void saveUserNameData(Context ctx, String key, String value) {
        final SharedPreferences prefs = getSharedPreferences(ctx);
        Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getUserNameData(Context ctx, String key) {
        final SharedPreferences prefs = getSharedPreferences(ctx);
        return prefs.getString(key,"");
    }


    public static void setDateToQuery(Context ctx, String key, String value) {
        final SharedPreferences prefs = getSharedPreferences(ctx);
        Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }
}
