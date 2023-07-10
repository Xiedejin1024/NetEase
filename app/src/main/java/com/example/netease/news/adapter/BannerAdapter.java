package com.example.netease.news.adapter;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.example.netease.R;
import com.example.netease.news.bean.Ads;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class BannerAdapter extends PagerAdapter {
    List<Ads> mAds;
    List<View> mView;
    private DisplayImageOptions options;

    public BannerAdapter(List<Ads> ads, List<View> mView) {
        this.mAds = ads;
        this.mView = mView;
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        position = position % mView.size();

        View view = mView.get(position);
        ImageView mIcon = view.findViewById(R.id.image_icon);
        String imgsrc = mAds.get(position).getImgsrc();
        ImageLoader.getInstance().displayImage(imgsrc, mIcon, options);
        container.addView(view);
        return view;
    }
}
