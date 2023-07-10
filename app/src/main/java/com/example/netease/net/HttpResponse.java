package com.example.netease.net;

import android.text.TextUtils;

import com.example.netease.utils.Jsonutil;

public abstract class HttpResponse<T> {

    Class<T> mTClass;

    public HttpResponse(Class<T> tClass) {
        this.mTClass = tClass;
    }

    public abstract void onError();

    public abstract void onSuccess(T t);

    public void parseDatas(String json) {
        if (TextUtils.isEmpty(json)) {
            onError();
            return;
        }
        if (mTClass == String.class) {
            onSuccess((T) json);
            return;
        }
        T t = Jsonutil.Jsonparse(json, mTClass);
        if (t == null) {
            onError();
            return;
        }
        onSuccess(t);

    }


}
