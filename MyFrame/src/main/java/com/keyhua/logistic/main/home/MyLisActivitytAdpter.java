package com.keyhua.logistic.main.home;

import java.util.List;

import com.example.importotherlib.R;
import com.keyhua.logistic.main.home.bean.SearchResultBean;
import com.keyhua.logistic.view.MyListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import in.srain.cube.image.ImageLoader;

/**
 * @author 曾金叶
 * @2015-8-6 @上午9:58:49 adapter
 */

public class MyLisActivitytAdpter extends BaseAdapter {
    private Context context = null;
    public JSONArray mDatas = null;
    //    private ImageLoader imageLoader = null;
//    private com.nostra13.universalimageloader.core.ImageLoader mImageLoader = null;
    private DisplayImageOptions options;

    public MyLisActivitytAdpter(Context context, JSONArray list) {
        this.context = context;
        this.mDatas = list;
//        this.imageLoader = imageLoader;
//        this.mImageLoader = mImageLoader;
//        this.options = options;
    }

    @Override
    public int getCount() {
        return mDatas != null ? mDatas.length() : 0;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_home, null);
            holder = new ViewHolder();
            holder.item_tv = (TextView) convertView.findViewById(R.id.item_tv);
            holder.itme_lv = (MyListView) convertView.findViewById(R.id.itme_lv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        try {
            holder.item_tv.setText((String) mDatas.getJSONObject(position).get("date"));
            holder.itme_lv.setAdapter(new ItemAdpter(context, (JSONArray) mDatas.getJSONObject(position).get("data"), position));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return convertView;
    }

    private class ViewHolder {
        private TextView item_tv = null;
        private MyListView itme_lv;
    }

    public class ItemAdpter extends BaseAdapter {
        private Context context = null;
        public JSONArray mDatas = null;
        private int position_f = 0;

        public ItemAdpter(Context context, JSONArray list, int position) {
            this.context = context;
            this.mDatas = list;
            this.position_f = position;
        }

        @Override
        public int getCount() {
            return mDatas != null ? mDatas.length() : 0;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_timeline, null);
                holder = new ViewHolder();
                holder.dongtai_des = (TextView) convertView.findViewById(R.id.dongtai_des);
                holder.dongtai_time = (TextView) convertView.findViewById(R.id.dongtai_time);
                holder.view_1 = (View) convertView.findViewById(R.id.view_1);
                holder.image = (ImageView) convertView.findViewById(R.id.image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            try {
                holder.dongtai_des.setText((String) mDatas.getJSONObject(position).get("des"));
                holder.dongtai_time.setText((String) mDatas.getJSONObject(position).get("time"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //根据上一层的位子来显示颜色
            if (position_f == 0 && position == 0) {
                holder.view_1.setVisibility(View.INVISIBLE);
                holder.image.setBackgroundResource((R.drawable.solid_timeline_one));
                holder.dongtai_des.setTextColor(context.getResources().getColor(R.color.appblue));
                holder.dongtai_time.setTextColor(context.getResources().getColor(R.color.appblue));
            } else {
                holder.view_1.setVisibility(View.VISIBLE);
                holder.image.setBackgroundResource(R.drawable.solid_timeline);
                holder.dongtai_des.setTextColor(context.getResources().getColor(R.color.content));
                holder.dongtai_time.setTextColor(context.getResources().getColor(R.color.content));
            }
            return convertView;
        }

        private class ViewHolder {
            private TextView dongtai_des = null;
            private View view_1 = null;
            private ImageView image = null;
            private TextView dongtai_time = null;
        }
    }
}
