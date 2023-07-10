package com.example.netease.service;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.netease.contance.Contance;
import com.example.netease.splash.bean.Ads;
import com.example.netease.splash.bean.AdsDetail;
import com.example.netease.splash.bean.Material;
import com.example.netease.utils.Commonutils;
import com.example.netease.utils.MD5util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;


public class DownloadImageService extends IntentService {
    public static String SPLASH_DATAS = "datas";

    /**
     * @deprecated
     */
    public DownloadImageService() {
        super("DownloadImageService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Ads Allads = (Ads) intent.getSerializableExtra(SPLASH_DATAS);
        List<AdsDetail> adsDetails = Allads.getAds();


        for (int i = 0; i < adsDetails.size(); i++) {
            AdsDetail adsDetail = adsDetails.get(i);
            List<Material> materialList = adsDetail.getMaterialList();
            Material material = materialList.get(0);
            String url = material.getUrls().get(0);
            String mImagename = MD5util.MD52Str(url);//生成本地唯一的文件名称
            if (!Commonutils.checkDownload(getApplicationContext(), mImagename)) {
                saveImage(url, mImagename);
            } else {
                Log.e("it520com", "url=" + url);
            }
        }


    }


    private void saveImage(String imagerUrl, String imageName) {
        try {
            URL url = new URL(imagerUrl);
            URLConnection connection = url.openConnection();
            Bitmap bitmap = BitmapFactory.decodeStream(connection.getInputStream());
            if (bitmap != null) {
                File path = new File(getApplicationContext().getCacheDir(), Contance.CHILDFILE);
                if (!path.exists()) {
                    path.mkdirs();
                }
                File image = new File(path, imageName + ".jpg");
                if (image.exists()) {
                    return;
                }
                FileOutputStream fos = new FileOutputStream(image);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 60, fos);
                fos.flush();
                fos.close();

            }
            //bitmap.recycle();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
