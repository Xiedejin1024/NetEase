package com.example.netease.utils;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.netease.contance.Contance;

public class SPutils {


    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(Contance.SPLASHNAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context context, String key, boolean defvalue) {
        SharedPreferences sp = context.getSharedPreferences(Contance.SPLASHNAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defvalue);
    }

    public static void putStringValue(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(Contance.SPLASHNAME, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    public static String getStringValue(Context context, String key, String defvalue) {
        SharedPreferences sp = context.getSharedPreferences(Contance.SPLASHNAME, Context.MODE_PRIVATE);
        return sp.getString(key, defvalue);
    }


    public static void putInt(Context context, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences(Contance.SPLASHNAME, Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).commit();
    }

    public static int getInt(Context context, String key, int defvalue) {
        SharedPreferences sp = context.getSharedPreferences(Contance.SPLASHNAME, Context.MODE_PRIVATE);
        return sp.getInt(key, defvalue);
    }


    public static void putlong(Context context, String key, long value) {
        SharedPreferences sp = context.getSharedPreferences(Contance.SPLASHNAME, Context.MODE_PRIVATE);
        sp.edit().putLong(key, value).commit();
    }

    public static long getlong(Context context, String key, long defvalue) {
        SharedPreferences sp = context.getSharedPreferences(Contance.SPLASHNAME, Context.MODE_PRIVATE);
        return sp.getLong(key, defvalue);
    }

}
