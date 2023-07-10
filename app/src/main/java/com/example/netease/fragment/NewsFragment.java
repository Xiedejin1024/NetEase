package com.example.netease.fragment;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.netease.R;
import com.example.netease.news.NoScrollView;
import com.example.netease.news.adapter.NewsFragmentAdapter;
import com.example.netease.bean.FragmentInfo;
import com.example.netease.contance.Contance;
import com.example.netease.net.HttpResponse;
import com.example.netease.news.adapter.RuleAdapter;
import com.example.netease.news.bean.Hots;
import com.example.netease.news.bean.ShowHideEvent;
import com.example.netease.splash.bean.Ads;
import com.example.netease.utils.Httputil;
import com.example.netease.utils.SPutils;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class NewsFragment extends Fragment implements AdapterView.OnItemClickListener {


    private static String SHOU_CONTENT = "showcontent";
    private static String NO_SHOU_CONTENT = "noshowcontent";
    private ViewPager mViewpager;
    private NewsFragmentAdapter mAdapter;
    private ArrayList<FragmentInfo> mInfo;
    private boolean isShow = false;
    private RuleAdapter mRuleAdapter, mRuleNoshowAdapter;
    private SmartTabLayout viewPagerTab;
    private String lastContent;
    private String[] arrayNews;
    private TextView mTvsortBtn;
    // private String[] arrayTitles;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        initView(view);
        initData();

        return view;
    }

    private void initData() {

    }

    private void initView(View view) {
        mViewpager = view.findViewById(R.id.viewpager);
        mInfo = new ArrayList<>();

        String showcontent = SPutils.getStringValue(getActivity().getApplicationContext(), SHOU_CONTENT, "");
        lastContent = showcontent;
        String noshowcontent = SPutils.getStringValue(getActivity().getApplicationContext(), NO_SHOU_CONTENT, "");

        if (TextUtils.isEmpty(showcontent)) {
            arrayNews = getResources().getStringArray(R.array.news_title);
        } else {
            arrayNews = showcontent.split("-");
        }

        for (int i = 0; i < arrayNews.length; i++) {
            FragmentInfo fragmentInfo;
            String newstitle = arrayNews[i];
            if (i == 0) {
                fragmentInfo = new FragmentInfo(new HeadlineFragment(), newstitle);

            } else {
                fragmentInfo = new FragmentInfo(new EmptyFragment(), newstitle);
            }
            mInfo.add(fragmentInfo);
        }

        ImageView add_view = view.findViewById(R.id.add_fragment);

        LinearLayout tag_ll = view.findViewById(R.id.tag_ll);
        RelativeLayout ll_sort = view.findViewById(R.id.sort);
        add_view.setOnClickListener((View v) -> {
            if (isShow) {//隐藏
                Animation sort_hide = AnimationUtils.loadAnimation(getContext(), R.anim.ll_sort_hide);
                ll_sort.startAnimation(sort_hide);
                sort_hide.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        ll_sort.setVisibility(View.GONE);

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                Animation add_hide = AnimationUtils.loadAnimation(getContext(), R.anim.add_hide);
                add_hide.setFillAfter(true);
                add_view.startAnimation(add_hide);

                Animation tag_hide = AnimationUtils.loadAnimation(getContext(), R.anim.tag_hide);
                tag_ll.startAnimation(tag_hide);
                tag_hide.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        tag_ll.setVisibility(View.GONE);
                        mTvsortBtn.setText("排序删除");
                        mRuleAdapter.setDelect();
                        String showcontent = mRuleAdapter.getContent();

                        String Noshowcontent = mRuleNoshowAdapter.getContent();

                        SPutils.putStringValue(getActivity().getApplicationContext(), SHOU_CONTENT, showcontent);
                        SPutils.putStringValue(getActivity().getApplicationContext(), NO_SHOU_CONTENT, Noshowcontent);

                        if (!lastContent.equals(showcontent)) {
                            String[] split = showcontent.split("-");
                            ArrayList<FragmentInfo> mNewInfo = new ArrayList<>();
                            for (int i = 0; i < split.length; i++) {
                                FragmentInfo fragmentInfo;
                                String newstitle = split[i];
                                if (i == 0) {
                                    fragmentInfo = new FragmentInfo(new HeadlineFragment(), newstitle);

                                } else {
                                    fragmentInfo = new FragmentInfo(new EmptyFragment(), newstitle);
                                }
                                mNewInfo.add(fragmentInfo);
                            }
                            mAdapter.updatas(mNewInfo);
                            viewPagerTab.setViewPager(mViewpager);
                        }
                        lastContent = showcontent;
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                EventBus.getDefault().post(new ShowHideEvent(true));

            } else {
                Animation add_show = AnimationUtils.loadAnimation(getContext(), R.anim.add_show);
                add_show.setFillAfter(true);
                add_view.startAnimation(add_show);
                ll_sort.setVisibility(View.VISIBLE);
                Animation sort_show = AnimationUtils.loadAnimation(getContext(), R.anim.ll_sort_show);
                ll_sort.startAnimation(sort_show);

                Animation tag_show = AnimationUtils.loadAnimation(getContext(), R.anim.tag_show);
                tag_ll.setVisibility(View.VISIBLE);
                tag_show.setFillAfter(true);
                tag_ll.startAnimation(tag_show);
                EventBus.getDefault().post(new ShowHideEvent(false));
            }
            isShow = !isShow;
        });


        mTvsortBtn = view.findViewById(R.id.tv_sort_btn);
        mTvsortBtn.setOnClickListener((View v) -> {
            mRuleAdapter.setDelect();
            if (mRuleAdapter.isShowDelect()) {
                mTvsortBtn.setText("完成");
            } else {
                mTvsortBtn.setText("排序删除");
            }

        });

        mAdapter = new NewsFragmentAdapter(getFragmentManager(), mInfo);
        mViewpager.setAdapter(mAdapter);

        viewPagerTab = view.findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(mViewpager);

        NoScrollView show_gridview = view.findViewById(R.id.gridview_show);
        mRuleAdapter = new RuleAdapter(getContext(), arrayNews);
        show_gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        show_gridview.setAdapter(mRuleAdapter);
        show_gridview.setOnItemClickListener(this);

        NoScrollView noshow_gridview = view.findViewById(R.id.gridview_no_show);
        if (TextUtils.isEmpty(noshowcontent)) {
            mRuleNoshowAdapter = new RuleAdapter(getContext());
        } else {
            String[] noshow = noshowcontent.split("-");
            mRuleNoshowAdapter = new RuleAdapter(getContext(), noshow);
        }

        noshow_gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        noshow_gridview.setAdapter(mRuleNoshowAdapter);
        noshow_gridview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        switch (adapterView.getId()) {
            case R.id.gridview_show:

                if (mRuleAdapter.isShowDelect() && position != 0) {
                    String removeItem = mRuleAdapter.removeItem(position);
                    mRuleNoshowAdapter.addItem(removeItem);
                }
                if (mRuleAdapter.isShowDelect() && position == 0) {
                    Toast.makeText(getContext(), "头条不能删除", Toast.LENGTH_LONG).show();
                    return;
                }
                break;
            case R.id.gridview_no_show:
                String removeItem = mRuleNoshowAdapter.removeItem(position);
                mRuleAdapter.addItem(removeItem);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(getActivity());
    }
}
