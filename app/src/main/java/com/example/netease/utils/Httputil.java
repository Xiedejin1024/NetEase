package com.example.netease.utils;

import android.content.Intent;
import android.util.Log;

import com.example.netease.activity.SplashActivity;
import com.example.netease.contance.Contance;
import com.example.netease.net.HttpResponse;
import com.example.netease.service.DownloadImageService;
import com.example.netease.splash.bean.Ads;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Httputil {
    private static Httputil mHttputil = null;
    private OkHttpClient mClient;

    private Httputil() {
        mClient = new OkHttpClient();
    }

    public static Httputil getInstance() {
        if (mHttputil == null) {
            synchronized (Httputil.class) {
                if (mHttputil == null) {
                    mHttputil = new Httputil();
                }
            }
        }
        return mHttputil;
    }

    public void doGet(String url, HttpResponse httpResponse) {

        Request request = new Request.Builder()
                .url(url)

                .build();

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                httpResponse.onError();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    httpResponse.onError();
                }
                String jsonstr = response.body().string();
                httpResponse.parseDatas(jsonstr);

            }
        });
    }
}
