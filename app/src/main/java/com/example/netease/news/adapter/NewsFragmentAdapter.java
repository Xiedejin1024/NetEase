package com.example.netease.news.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.netease.bean.FragmentInfo;

import java.util.ArrayList;
import java.util.List;

//FragmentPagerAdapter  一次性加载所有的Fragment
//FragmentStateAdapter  每次只加载正在显示的Fragment
public class NewsFragmentAdapter extends FragmentStatePagerAdapter {
    ArrayList<FragmentInfo> mInfos;

    public NewsFragmentAdapter(FragmentManager fm, List<FragmentInfo> fginfo) {
        super(fm);
        mInfos = new ArrayList<>();
        mInfos.addAll(fginfo);
    }

    @Override
    public Fragment getItem(int position) {
        return mInfos.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return mInfos.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mInfos.get(position).getName();
    }


    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void updatas(ArrayList<FragmentInfo> mNewInfo) {
        mInfos = mNewInfo;
        notifyDataSetChanged();
    }
}
