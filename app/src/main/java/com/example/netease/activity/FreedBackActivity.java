package com.example.netease.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.netease.R;
import com.example.netease.contance.Contance;
import com.example.netease.net.HttpResponse;
import com.example.netease.news.adapter.FreedBacksAdapter;
import com.example.netease.news.bean.FreedBack;
import com.example.netease.news.bean.FreedBacks;
import com.example.netease.utils.Httputil;
import com.example.netease.utils.Jsonutil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;


public class FreedBackActivity extends AppCompatActivity {
    public static final String DOCID = "docid";
    private static final int INIT_LIST = 1;
    private Httputil mHttputil;
    private MyHandler mMyhandler;
    private ArrayList<FreedBacks> mPostList;
    private ListView mLvFreedBack;
    private FreedBacksAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freedback);
        mLvFreedBack = findViewById(R.id.lv_freedback);
        mMyhandler = new MyHandler(this);
        mPostList = new ArrayList<>();
        String docid = getIntent().getStringExtra(DOCID);
        getFreedBack(docid);

    }

    private void getFreedBack(String docid) {
        String freedbackURL = Contance.getFreedbackURL(docid);
        mHttputil = Httputil.getInstance();
        mHttputil.doGet(freedbackURL, new HttpResponse<String>(String.class) {
            @Override
            public void onError() {
            }

            @Override
            public void onSuccess(String string) {
                try {
                    JSONObject json = new JSONObject(string);
                    JSONArray hotPosts = json.optJSONArray("hotPosts");
                    if (hotPosts == null) {
                        return;
                    }
                    /*FreedBacks title = new FreedBacks();
                    title.setTitle(true);
                    title.setTitle("热门跟帖");
                    mPostList.add(title);*/
                    for (int i = 0; i < hotPosts.length(); i++) {
                        FreedBacks freedBacks = new FreedBacks();
                        JSONObject tmp = hotPosts.getJSONObject(i);
                        Iterator<String> key = tmp.keys();
                        while (key.hasNext()) {
                            String next = key.next();
                            JSONObject jsonObject = tmp.getJSONObject(next);
                            if (jsonObject == null) {
                                continue;
                            }
                            FreedBack back = Jsonutil.Jsonparse(jsonObject.toString(), FreedBack.class);
                            if (back == null) {
                                continue;
                            }
                            back.setIndex(Integer.valueOf(next));
                            freedBacks.addFreedback(back);

                        }
                        freedBacks.sort();
                        mPostList.add(freedBacks);

                    }
                    mMyhandler.sendEmptyMessage(INIT_LIST);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    static class MyHandler extends Handler {
        //采用弱引用，避免内存泄露
        private WeakReference<FreedBackActivity> activity;

        private MyHandler(FreedBackActivity activity) {
            this.activity = new WeakReference<>(activity);

        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            FreedBackActivity freedBackActivity = activity.get();
            if (freedBackActivity == null) {
                return;
            }

            switch (msg.what) {
                case INIT_LIST:
                    freedBackActivity.showAdapter();

                    break;
            }
        }
    }

    private void showAdapter() {
        mAdapter = new FreedBacksAdapter(this, mPostList);
        mLvFreedBack.setAdapter(mAdapter);
    }
}
