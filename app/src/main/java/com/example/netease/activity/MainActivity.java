package com.example.netease.activity;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.netease.R;
import com.example.netease.fragment.NewsFragment;
import com.example.netease.fragment.LiveFragment;
import com.example.netease.fragment.MeFragment;
import com.example.netease.fragment.TopicFragment;
import com.example.netease.news.FragmentTabHost;
import com.example.netease.news.bean.ShowHideEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    private FragmentTabHost mTabhost;

    int[] icons = new int[]{R.drawable.news_select, R.drawable.live_select, R.drawable.topic_select, R.drawable.mine_select,};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ImageView iamge = findViewById(R.id.top);
            int statusHeight = getStatusBarHeight(this);
            iamge.getLayoutParams().height = statusHeight;
            iamge.setBackgroundColor(getResources().getColor(R.color.tabhost_text_color));
        }
        mTabhost = findViewById(R.id.tab_host);
        mTabhost.setup(this, getSupportFragmentManager(), R.id.fl_content);
        String[] arrayTitle = getResources().getStringArray(R.array.title_tabhost);
        for (int i = 0; i < arrayTitle.length; i++) {
            TabHost.TabSpec tabSpec = mTabhost.newTabSpec(String.valueOf(i));
            tabSpec.setIndicator(getTabview(i, arrayTitle, icons));
            if (i == 0) {
                mTabhost.addTab(tabSpec, NewsFragment.class, null);
            } else if (i == 1) {
                mTabhost.addTab(tabSpec, LiveFragment.class, null);
            } else if (i == 2) {
                mTabhost.addTab(tabSpec, TopicFragment.class, null);
            } else if (i == 3) {
                mTabhost.addTab(tabSpec, MeFragment.class, null);
            }
        }
        //mTabhost.setCurrentTabByTag("0");
    }

    private View getTabview(int index, String[] arrayTitle, int[] micon) {
        View view = View.inflate(this, R.layout.item_fragment_tabhost, null);
        ImageView mIcon = view.findViewById(R.id.image_tabhost_icon);
        TextView mTvname = view.findViewById(R.id.tv_tabhost_name);
        mIcon.setImageResource(micon[index]);
        mTvname.setText(arrayTitle[index]);
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void hideTab(ShowHideEvent event) {
        int show = event.mIsShowTab ? View.VISIBLE : View.GONE;
        TabWidget tabWidget = mTabhost.getTabWidget();
        tabWidget.setVisibility(show);


    }

    //获取失败，报异常 java.lang.InstantiationException 是指不能实例化某个对象
    public int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    private int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private long exitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_LONG).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
