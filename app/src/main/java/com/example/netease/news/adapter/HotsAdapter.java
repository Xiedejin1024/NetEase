package com.example.netease.news.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.netease.R;
import com.example.netease.news.bean.HotsDetail;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class HotsAdapter extends BaseAdapter {

    private DisplayImageOptions options;
    private ArrayList<HotsDetail> mDatas;
    private Context mContext;

    public HotsAdapter(Context context, List<HotsDetail> datas) {
        this.mContext = context;
        mDatas = new ArrayList<>();
        mDatas.addAll(datas);
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
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
    public View getView(int position, View converView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (converView == null) {
            holder = new ViewHolder();
            converView = View.inflate(mContext, R.layout.item_fragment_headline, null);
            holder.imageView = converView.findViewById(R.id.image_headline_icon);
            holder.tv_title = converView.findViewById(R.id.tv_headline_title);
            holder.tv_source = converView.findViewById(R.id.tv_headline_source);
            holder.tv_replyCount = converView.findViewById(R.id.tv_headline_replyCount);
            converView.setTag(holder);
        } else {
            holder = (ViewHolder) converView.getTag();
        }
        HotsDetail hotsDetail = mDatas.get(position);
        ImageLoader.getInstance().displayImage(hotsDetail.getImgsrc(), holder.imageView, options);
        //holder.imageView.setImageBitmap(hotsDetail.getImgsrc());
        holder.tv_title.setText(hotsDetail.getTitle());
        holder.tv_source.setText(hotsDetail.getSource());
        holder.tv_replyCount.setText(hotsDetail.getReplyCount() + "跟贴");
        return converView;
    }

    public void updatas(List<HotsDetail> details) {
        mDatas.addAll(details);
        notifyDataSetChanged();

    }

    class ViewHolder {
        ImageView imageView;
        TextView tv_title;
        TextView tv_source;
        TextView tv_replyCount;

    }

}
