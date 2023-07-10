package com.example.netease.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.netease.R;
import com.example.netease.mine.view.MineItemCustom;

public class MeFragment extends Fragment implements View.OnClickListener {


    private MineItemCustom mMic_news, mMic_jbsc, mMic_jbrw, mMic_yjfk, mMic_gyrj, mMic_dongtai;
    private RelativeLayout mMic_yjms;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mMic_news = view.findViewById(R.id.mic_mynews);
        mMic_dongtai = view.findViewById(R.id.mic_dongtai);
        mMic_jbsc = view.findViewById(R.id.mic_jbsc);
        mMic_jbrw = view.findViewById(R.id.mic_jbrw);
        mMic_yjms = view.findViewById(R.id.mic_yjms);
        mMic_yjfk = view.findViewById(R.id.mic_yjfk);
        mMic_gyrj = view.findViewById(R.id.mic_gyrj);
        mMic_news.setOnClickListener(this);
        mMic_dongtai.setOnClickListener(this);
        mMic_jbsc.setOnClickListener(this);
        mMic_jbrw.setOnClickListener(this);
        mMic_yjms.setOnClickListener(this);
        mMic_yjfk.setOnClickListener(this);
        mMic_gyrj.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mic_mynews:
                break;
            case R.id.mic_dongtai:
                break;
            case R.id.mic_jbsc:
                break;
            case R.id.mic_jbrw:
                break;
            case R.id.mic_yjms:
                break;
            case R.id.mic_yjfk:
                break;
            case R.id.mic_gyrj:
                break;


        }
    }
}
