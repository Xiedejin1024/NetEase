package com.example.netease.mine.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.netease.R;

public class MineItemCustom extends RelativeLayout {
    private TextView mTv_MineDesc;
    private TextView mTv_MineText;
    private ImageView mImageMineView;
    private RelativeLayout mRlMineView;


    public MineItemCustom(Context context) {
        super(context);
    }

    public MineItemCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        initData(attrs);
    }

    private void initData(AttributeSet attrs) {
        String text = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "text");
        String desc = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "desc");
        String iconLeft = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "iconLeft");
        if (!TextUtils.isEmpty(iconLeft)) {
            Drawable drawableLeft = getResources().getDrawable(Integer.parseInt(iconLeft.substring(1)));
            mTv_MineText.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null);
            mTv_MineText.setCompoundDrawablePadding(10);
        }
        mTv_MineText.setText(text);
        mTv_MineDesc.setText(desc);
        String bgselector = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "bgselector");
        switch (bgselector) {
            case "0":
                mRlMineView.setBackgroundResource(R.drawable.selector_item_mine_frist);
                break;
            case "1":
                mRlMineView.setBackgroundResource(R.drawable.selector_item_mine_middle);
                break;
            case "2":
                mRlMineView.setBackgroundResource(R.drawable.selector_item_mine_last);
                break;
        }
    }

    private void initView(Context context) {
        View view = View.inflate(context, R.layout.item_mine_view, this);
        mRlMineView = findViewById(R.id.rl_item_main_view);
        mTv_MineDesc = findViewById(R.id.tv_item_main_view_desc);
        mTv_MineText = findViewById(R.id.tv_item_main_view_text);
        mImageMineView = findViewById(R.id.image_item_main_view);
        mImageMineView = findViewById(R.id.image_item_main_view);
    }
}
