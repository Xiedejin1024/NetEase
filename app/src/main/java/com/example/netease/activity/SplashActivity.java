package com.example.netease.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.netease.R;
import com.example.netease.contance.Contance;
import com.example.netease.net.HttpResponse;
import com.example.netease.service.DownloadImageService;
import com.example.netease.splash.bean.Action;
import com.example.netease.splash.bean.Ads;
import com.example.netease.splash.bean.AdsDetail;
import com.example.netease.splash.bean.Material;
import com.example.netease.splash.view.RingTextView;
import com.example.netease.utils.Commonutils;
import com.example.netease.utils.Httputil;
import com.example.netease.utils.Jsonutil;
import com.example.netease.utils.MD5util;
import com.example.netease.utils.SPutils;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SplashActivity extends AppCompatActivity {

    private static final int RESH_RING = 0;
    private ImageView mImageSplashUi;
    private RingTextView mRingview;
    private MyHandler mhandler = null;
    private int resh_space = 50;
    private int index = 0;
    private int total = (4 * 1000) / resh_space;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splah);
        initView();
        initData();
        getAds();
    }

    private void initData() {
        int index = SPutils.getInt(getApplicationContext(), Contance.INDEX, 0);
        String jsonstr = SPutils.getStringValue(getApplicationContext(), Contance.JSON_STR, "");
        if (TextUtils.isEmpty(jsonstr)) {
            mImageSplashUi.setBackgroundResource(R.mipmap.splash);
            mhandler.postDelayed(() -> gotoMain(), 2000);
        } else {
            mRingview.setmRingListener(() -> gotoMain());
            Ads ads = Jsonutil.Jsonparse(jsonstr, Ads.class);
            if (ads == null) {
                mImageSplashUi.setBackgroundResource(R.mipmap.splash);
                mhandler.postDelayed(() -> gotoMain(), 2000);
            } else {
                List<AdsDetail> adsDetails = ads.getAds();
                index = index % adsDetails.size();//取模后顺序才能增加

                AdsDetail adsDetail = adsDetails.get(index);
                List<Material> materialList = adsDetail.getMaterialList();
                Material material = materialList.get(0);
                String url = material.getUrls().get(0);
                String mImagename = MD5util.MD52Str(url);//生成本地唯一的文件名称
                if (Commonutils.checkDownload(getApplicationContext(), mImagename)) {
                    File file = Commonutils.getimageByname(getApplicationContext(), mImagename);
                    if (file.exists()) {
                        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                        if (bitmap != null) {
                            mImageSplashUi.setImageBitmap(bitmap);
                            //点击图片跳转
                            mImageSplashUi.setOnClickListener(view -> skipUI(adsDetail));
                            index++;
                            SPutils.putInt(getApplicationContext(), Contance.INDEX, index);
                            mhandler.post(mRingRun);
                        }
                    }
                } else {
                    Log.e("it520com", "url=" + url);
                }
            }


        }

    }

    private void gotoMain() {
        mhandler.removeCallbacks(mRingRun);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    Runnable mRingRun = new Runnable() {
        @Override
        public void run() {
            Message message = mhandler.obtainMessage(RESH_RING);
            message.obj = index;
            mhandler.sendMessage(message);
            mhandler.postDelayed(mRingRun, resh_space);
            index++;
        }
    };

    private void skipUI(AdsDetail adsDetail) {
        List<Action> actionList = adsDetail.getActionList();
        Action action = actionList.get(0);
        if (action == null || TextUtils.isEmpty(action.getUrl())) {
            return;
        }
        mhandler.removeCallbacks(mRingRun);
        Intent intent = new Intent();
        intent.putExtra(WebViewActivity.ACTION_URL, action);
        intent.setClass(SplashActivity.this, WebViewActivity.class);
        startActivity(intent);
        finish();
    }

    private void initView() {
        mhandler = new MyHandler(this);
        mImageSplashUi = findViewById(R.id.image_splash_ui);
        mRingview = findViewById(R.id.ring_textview);
    }

    private void getAds() {
        String jsonstr = SPutils.getStringValue(getApplicationContext(), Contance.JSON_STR, "");
        if (!TextUtils.isEmpty(jsonstr)) {
            //获取当前的时间
            long nowTime = System.currentTimeMillis();
            long lastTime = SPutils.getlong(getApplicationContext(), Contance.LAST_TIME, 0);
            long timeout = (long) SPutils.getInt(getApplicationContext(), Contance.TIME_OUT, 0);

            if ((nowTime - lastTime) > (timeout * 60 * 1000)) {
                getNetDatas(Contance.SPLASHURL);
            }

        } else {
            getNetDatas(Contance.SPLASHURL);
        }
    }

    private void getNetDatas(String url) {
        Httputil httputil = Httputil.getInstance();
        httputil.doGet(url, new HttpResponse<String>(String.class) {
            @Override
            public void onError() {

            }

            @Override
            public void onSuccess(String jsonstr) {
                Ads ads = Jsonutil.Jsonparse(jsonstr, Ads.class);
                if (ads == null) {
                    Log.e("it520com", "解析异常");
                } else {
                    Log.e("it520com", "ads=" + ads.toString());
                    SPutils.putStringValue(getApplicationContext(), Contance.JSON_STR, jsonstr);//缓存数据，用于开启app时下载的判断
                    SPutils.putInt(getApplicationContext(), Contance.TIME_OUT, ads.getNext_req());
                    SPutils.putlong(getApplicationContext(), Contance.LAST_TIME, System.currentTimeMillis());
                    Intent intent = new Intent();
                    intent.putExtra(DownloadImageService.SPLASH_DATAS, ads);
                    intent.setClass(SplashActivity.this, DownloadImageService.class);
                    startService(intent);
                }
            }
        });
    }

    static class MyHandler extends Handler {
        //采用弱引用，避免内存泄露
        private WeakReference<SplashActivity> activity;


        private MyHandler(SplashActivity activity) {
            this.activity = new WeakReference<>(activity);

        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            SplashActivity splashActivity = activity.get();
            if (splashActivity == null) {
                return;
            }

            switch (msg.what) {
                case RESH_RING:
                    int index = (int) msg.obj;
                    if (index > splashActivity.total) {
                        splashActivity.mhandler.removeCallbacks(splashActivity.mRingRun);
                        splashActivity.gotoMain();
                    } else {
                        splashActivity.mRingview.setProgress(splashActivity.total, index);
                    }
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        mhandler.removeCallbacks(mRingRun);
        super.onBackPressed();
    }
}
