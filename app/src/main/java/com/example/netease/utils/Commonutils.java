package com.example.netease.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.netease.contance.Contance;

import java.io.File;

public class Commonutils {


    public static boolean checkDownload(Context context, String imagename) {
        File image = getimageByname(context, imagename);
        if (image == null) return false;
        Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath());
        if (bitmap == null) {
            return false;
        }
        bitmap.recycle();
        return true;
    }

    public static File getimageByname(Context context, String imagename) {
        File path = new File(context.getCacheDir(), Contance.CHILDFILE);
        if (!path.exists()) {
            return null;
        }
        File image = new File(path, imagename + ".jpg");
        if (!image.exists()) {
            return null;
        }
        return image;
    }
}
