package com.example.netease.utils;

import android.text.TextUtils;

import com.google.gson.Gson;

public class Jsonutil {
    static Gson mGson;

    public static <T> T Jsonparse(String string, Class<T> tClass) {
        if (TextUtils.isEmpty(string) || tClass == null) return null;
        if (mGson == null) {
            mGson = new Gson();
        }
        T result = mGson.fromJson(string, tClass);
        return result;
    }
}
