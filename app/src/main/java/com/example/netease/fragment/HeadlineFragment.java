package com.example.netease.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.netease.R;
import com.example.netease.activity.DetailActivity;
import com.example.netease.contance.Contance;
import com.example.netease.net.HttpResponse;
import com.example.netease.news.adapter.BannerAdapter;
import com.example.netease.news.adapter.HotsAdapter;
import com.example.netease.news.bean.Ads;
import com.example.netease.news.bean.Hots;
import com.example.netease.news.bean.HotsDetail;
import com.example.netease.utils.Httputil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class HeadlineFragment extends Fragment implements ViewPager.OnPageChangeListener, AbsListView.OnScrollListener {
    private Httputil mHttputil;
    private ListView mLvheadline;
    private List<Ads> mBannerlist;//装载轮播图
    private List<HotsDetail> mHotsDetail;

    private static final int INIT_SUCCESS = 0;
    private static final int LOAD_MORE_SUCCESS = 1;
    private MyHandler mMyHandler;
    private HotsAdapter adapter;
    private ViewPager mVpheadviewpager;
    private BannerAdapter mBannerAdapter;
    private TextView mBannerTitle;
    private LinearLayout mLlDots;
    private List<ImageView> mDots;
    private boolean isScrollToBottom;
    private int loadMoreIndex = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBannerlist = new ArrayList<>();
        mHotsDetail = new ArrayList<>();
        mDots = new ArrayList<>();
        mMyHandler = new MyHandler(this);
        initData(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_headline, container, false);
        mLvheadline = view.findViewById(R.id.headline_listview);
        View headView = inflater.inflate(R.layout.include_headline_viewpage, null);
        mLvheadline.addHeaderView(headView);
        mVpheadviewpager = headView.findViewById(R.id.head_viewpager);
        mLlDots = headView.findViewById(R.id.dots);
        mBannerTitle = headView.findViewById(R.id.tv_viewpager_title);
        mLvheadline.setOnScrollListener(this);
        mLvheadline.setOnItemClickListener((AdapterView<?> adapterView, View converview, int position, long l) -> {
            int head = mLvheadline.getHeaderViewsCount();
            HotsDetail hotsDetail = mHotsDetail.get(position - head);
            if (!TextUtils.isEmpty(hotsDetail.getPostid())) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), DetailActivity.class);
                intent.putExtra(DetailActivity.DOCID, hotsDetail.getPostid());
                startActivity(intent);
            } else {
                //跳转到专题界面
            }
        });
        mLvheadline.setEmptyView(view.findViewById(R.id.loading));
        return view;
    }


    private void initData(boolean isFirstload) {

        mHttputil = Httputil.getInstance();
        mHttputil.doGet(getLoadURL(isFirstload), new HttpResponse<Hots>(Hots.class) {
            @Override
            public void onError() {
            }

            @Override
            public void onSuccess(Hots hots) {
                List<HotsDetail> hotsDetails = hots.getT1348647909107();
                if (isFirstload) {
                    HotsDetail detail = hotsDetails.get(0);//取出轮播图
                    List<Ads> ads = detail.getAds();
                    mBannerlist.addAll(ads);
                    hotsDetails.remove(0);//删除轮播图位置
                    mHotsDetail.addAll(hotsDetails);
                    mMyHandler.sendEmptyMessage(INIT_SUCCESS);
                } else {
                    if (mHotsDetail != null) {
                        mHotsDetail.addAll(hotsDetails);
                    }
                    Message msg = mMyHandler.obtainMessage(LOAD_MORE_SUCCESS);
                    msg.obj = hotsDetails;
                    mMyHandler.sendMessage(msg);
                }

                loadMoreIndex++;//刷新页面成功的次数，用以分页查询的
            }
        });
    }

    private String getLoadURL(boolean isFirstload) {
        if (isFirstload) {
            loadMoreIndex = 0;
        }
        int start = loadMoreIndex * 20;
        int end = start + 20;
        String url = Contance.getRefreshURL(Contance.NEWSHEADLINEURL, start, end);
        return url;
    }

    private void initList() {
        adapter = new HotsAdapter(getContext(), mHotsDetail);
        mLvheadline.setAdapter(adapter);
    }


    private void initBanner() {
        ArrayList<View> mViewlist = new ArrayList<>();
        for (int i = 0; i < mBannerlist.size(); i++) {
            View view = View.inflate(getContext(), R.layout.item_banner_view, null);
            mViewlist.add(view);

            ImageView dots = new ImageView(getContext());
            dots.setImageResource(R.drawable.gray_banner_headline);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 0, 0, 0);
            dots.setLayoutParams(params);
            mLlDots.addView(dots);
            mDots.add(dots);
        }

        mBannerAdapter = new BannerAdapter(mBannerlist, mViewlist);
        mVpheadviewpager.setAdapter(mBannerAdapter);
        mVpheadviewpager.setCurrentItem(Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % mBannerlist.size()));
        changedHotsState(0);
        //mMyHandler.postDelayed(mRun, 3000);
        mVpheadviewpager.setOnPageChangeListener(this);
        /*mVpheadviewpager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mMyHandler.removeCallbacks(mRun);
                        break;
                    case MotionEvent.ACTION_UP:
                        mMyHandler.postDelayed(mRun, 3000);
                        break;
                }
                return false;
            }
        });*/

    }

    Runnable mRun = new Runnable() {
        @Override
        public void run() {
            int index = mVpheadviewpager.getCurrentItem();
            mVpheadviewpager.setCurrentItem(index + 1);
            mMyHandler.postDelayed(this, 3000);
        }
    };

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        changedHotsState(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    public void changedHotsState(int position) {
        position = position % mDots.size();

        for (int i = 0; i < mDots.size(); i++) {
            ImageView imageView = mDots.get(i);
            if (position == i) {
                imageView.setImageResource(R.drawable.white_banner_headline);
            } else {
                imageView.setImageResource(R.drawable.gray_banner_headline);
            }
        }
        mBannerTitle.setText(mBannerlist.get(position).getTitle());
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE && isScrollToBottom) {
            //获取更多数据
            initData(false);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (view.getLastVisiblePosition() == (totalItemCount - 1)) {
            isScrollToBottom = true;
        } else {
            isScrollToBottom = false;
        }
    }

    public void listLoadMore(List<HotsDetail> details) {
        adapter.updatas(details);
    }


    static class MyHandler extends Handler {
        WeakReference<HeadlineFragment> mHeadLineFG;

        public MyHandler(HeadlineFragment mHeadFG) {
            mHeadLineFG = new WeakReference<>(mHeadFG);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            //使用之前进行判断是否被回收
            HeadlineFragment fragment = mHeadLineFG.get();
            if (fragment == null) {
                return;
            }
            switch (msg.what) {
                case INIT_SUCCESS:
                    fragment.initList();
                    fragment.initBanner();
                    break;
                case LOAD_MORE_SUCCESS:
                    List<HotsDetail> hotsDetails = (List<HotsDetail>) msg.obj;
                    fragment.listLoadMore(hotsDetails);
                    break;
            }
        }
    }
}