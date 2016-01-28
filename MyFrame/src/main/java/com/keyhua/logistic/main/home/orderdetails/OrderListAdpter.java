package com.keyhua.logistic.main.home.orderdetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.importotherlib.R;
import com.keyhua.logistic.view.MyListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * @author 曾金叶
 * @2015-8-6 @上午9:58:49 adapter
 */

public class OrderListAdpter extends BaseAdapter {
    private Context context = null;
    public JSONArray mDatas = null;
    //    private ImageLoader imageLoader = null;
//    private com.nostra13.universalimageloader.core.ImageLoader mImageLoader = null;
    private DisplayImageOptions options;

    public OrderListAdpter(Context context, JSONArray list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_orderlist, null);
            holder = new ViewHolder();
            holder.tv_odnum = (TextView) convertView.findViewById(R.id.tv_odnum);
            holder.tv_from = (TextView) convertView.findViewById(R.id.tv_from);
            holder.tv_to = (TextView) convertView.findViewById(R.id.tv_to);
            holder.tv_dis = (TextView) convertView.findViewById(R.id.tv_dis);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.tv_status = (TextView) convertView.findViewById(R.id.tv_status);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        try {
            holder.tv_odnum.setText((String) mDatas.getJSONObject(position).get("odnum"));
            holder.tv_from.setText((String) mDatas.getJSONObject(position).get("from"));
            holder.tv_to.setText((String) mDatas.getJSONObject(position).get("to"));
            holder.tv_dis.setText((String) mDatas.getJSONObject(position).get("dis"));
            holder.tv_time.setText((String) mDatas.getJSONObject(position).get("time"));
            holder.tv_status.setText((String) mDatas.getJSONObject(position).get("status"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return convertView;
    }

    private class ViewHolder {
        private TextView tv_odnum = null;
        private TextView tv_from = null;
        private TextView tv_to = null;
        private TextView tv_dis = null;
        private TextView tv_time = null;
        private TextView tv_status = null;
    }
}
