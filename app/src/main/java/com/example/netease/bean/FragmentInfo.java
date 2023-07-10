package com.example.netease.bean;

import androidx.fragment.app.Fragment;

import com.example.netease.fragment.HeadlineFragment;

public class FragmentInfo {

    Fragment fragment;
    String name;

    public FragmentInfo(Fragment mFragment, String mName) {
        this.fragment = mFragment;
        this.name = mName;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "FragmentInfo{" +
                "fragment=" + fragment +
                ", name='" + name + '\'' +
                '}';
    }
}
