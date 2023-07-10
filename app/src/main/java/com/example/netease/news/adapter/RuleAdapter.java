package com.example.netease.news.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.netease.R;

import java.util.ArrayList;
import java.util.Arrays;

public class RuleAdapter extends BaseAdapter {


    private ArrayList<String> mDatas;
    private Context mContext;
    private boolean isShowDel;

    public boolean isShowDelect() {
        return isShowDel;
    }

    public RuleAdapter(Context context, String[] datas) {
        this.mContext = context;
        this.mDatas = new ArrayList<>();
        mDatas.addAll(Arrays.asList(datas));
    }

    public RuleAdapter(Context context) {
        this.mContext = context;
        this.mDatas = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View converview, ViewGroup viewGroup) {
        if (converview == null) {
            converview = View.inflate(mContext, R.layout.item_rule, null);
        }
        TextView mTvRule = converview.findViewById(R.id.tv_item_rule);
        ImageView mImageDel = converview.findViewById(R.id.image_delect);
        mTvRule.setText(mDatas.get(position));
        if (mDatas.get(position).equals("头条")) {
            mImageDel.setVisibility(View.GONE);
        } else {
            mImageDel.setVisibility(isShowDel ? View.VISIBLE : View.GONE);
        }
        return converview;
    }

    public void setDelect() {

        isShowDel = !isShowDel;
        notifyDataSetChanged();
    }

    public String removeItem(int position) {
        String name = "";
        if (position >= 0 && position <= mDatas.size() - 1) {
            name = mDatas.get(position);
            mDatas.remove(name);
            notifyDataSetChanged();
        }
        return name;
    }

    public String getContent() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mDatas.size(); i++) {
            String string = mDatas.get(i);
            sb.append(string);
            if (i != mDatas.size() - 1) {
                sb.append("-");
            }
        }


        return sb.toString();
    }


    public void addItem(String removeItem) {
        mDatas.add(removeItem);
        notifyDataSetChanged();
    }
}
