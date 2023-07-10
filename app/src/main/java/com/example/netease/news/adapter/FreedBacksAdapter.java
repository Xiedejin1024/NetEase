package com.example.netease.news.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.netease.R;
import com.example.netease.news.bean.FreedBack;
import com.example.netease.news.bean.FreedBacks;
import com.example.netease.news.bean.HotsDetail;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class FreedBacksAdapter extends BaseAdapter {


    private static final int TITLE = 1;
    private static final int CONTENT = 2;
    private ArrayList<FreedBacks> mDatas;
    private Context mContext;

    public FreedBacksAdapter(Context context, List<FreedBacks> datas) {
        this.mContext = context;
        mDatas = new ArrayList<>();
        mDatas.addAll(datas);

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
        FreedBacks freedBacks = mDatas.get(position);
       /* int viewType = getItemViewType(position);
        if (viewType == CONTENT) {
            ViewHolder holder = null;
            if (converView == null) {
                holder = new ViewHolder();
                converView = View.inflate(mContext, R.layout.item_freedback_view, null);
                holder.image_praise_icon = converView.findViewById(R.id.image_freedback_icon);
                holder.tv_praise_name = converView.findViewById(R.id.tv_freedback_name);
                holder.tv_praise_time_address = converView.findViewById(R.id.tv_freedback_time_adress);
                holder.tv_praise_content = converView.findViewById(R.id.tv_freedback_content);
                holder.ll_praise = converView.findViewById(R.id.ll_praise);
                holder.tv_praise = converView.findViewById(R.id.tv_praise);
                converView.setTag(holder);
            } else {
                holder = (ViewHolder) converView.getTag();
            }

            List<FreedBack> freedBack = freedBacks.getmFreedback();
            if (freedBack.size() > 0) {
                FreedBack back = freedBack.get(freedBack.size() - 1);
                holder.tv_praise_name.setText(TextUtils.isEmpty(back.getN()) ? "手机网友" : back.getN());
                String f = back.getF();
                f.replace(":", "");
                if (f.lastIndexOf("&nbsp") != -1) {
                    int index = f.lastIndexOf("&nbsp");
                    String real = f.substring(2, index);
                    holder.tv_praise_time_address.setText(real + "     " + back.getT());
                } else {
                    holder.tv_praise_time_address.setText(back.getF() + "     " + back.getT());
                }

                holder.tv_praise_content.setText(back.getB());
                holder.tv_praise.setText(back.getV());
            }


        } else {
            TitleHolder titleHolder = null;
            if (converView == null) {
                titleHolder = new TitleHolder();
                converView = View.inflate(mContext, R.layout.item_freedback_title, null);
                titleHolder.tv_title = converView.findViewById(R.id.tv_freed_title);
                converView.setTag(titleHolder);
            } else {
                titleHolder = (TitleHolder) converView.getTag();
            }
            titleHolder.tv_title.setText(freedBacks.getTitle());
        }
        return converView;*/
        ViewHolder holder = null;
        if (converView == null) {
            holder = new ViewHolder();
            converView = View.inflate(mContext, R.layout.item_freedback_view, null);
            holder.image_praise_icon = converView.findViewById(R.id.image_freedback_icon);
            holder.tv_praise_name = converView.findViewById(R.id.tv_freedback_name);
            holder.tv_praise_time_address = converView.findViewById(R.id.tv_freedback_time_adress);
            holder.tv_praise_content = converView.findViewById(R.id.tv_freedback_content);
            holder.ll_praise = converView.findViewById(R.id.ll_praise);
            holder.tv_praise = converView.findViewById(R.id.tv_praise);
            converView.setTag(holder);
        } else {
            holder = (ViewHolder) converView.getTag();
        }

        List<FreedBack> freedBack = freedBacks.getmFreedback();
        if (freedBack.size() > 0) {
            FreedBack back = freedBack.get(freedBack.size() - 1);
            holder.tv_praise_name.setText(TextUtils.isEmpty(back.getN()) ? "手机网友" : back.getN());
            String f = back.getF();
            f.replace(":", "");
            if (f.lastIndexOf("&nbsp") != -1) {
                int index = f.lastIndexOf("&nbsp");
                String real = f.substring(2, index);
                holder.tv_praise_time_address.setText(real + "     " + back.getT());
            } else {
                holder.tv_praise_time_address.setText(back.getF() + "     " + back.getT());
            }

            holder.tv_praise_content.setText(back.getB());
            holder.tv_praise.setText(back.getV());
        }
        return converView;
    }

   /* public void updatas(List<HotsDetail> details) {
        mDatas.addAll(details);
        notifyDataSetChanged();

    }*/

    class ViewHolder {
        de.hdodenhof.circleimageview.CircleImageView image_praise_icon;
        TextView tv_praise_name;
        TextView tv_praise_time_address;
        TextView tv_praise_content;
        LinearLayout ll_praise;
        TextView tv_praise;

    }

    class TitleHolder {

        TextView tv_title;

    }

   /* @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        return mDatas.get(position).isTitle() ? TITLE : CONTENT;
    }*/
}
