package com.keyhua.logistic.main.home.mydriver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.importotherlib.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import org.json.JSONArray;
import org.json.JSONException;

import in.srain.cube.image.CubeImageView;
import in.srain.cube.image.ImageLoader;

/**
 * @author 曾金叶
 * @2015-8-6 @上午9:58:49 adapter
 */

public class DriverListAdpter extends BaseAdapter {
    private Context context = null;
    public JSONArray mDatas = null;
    private ImageLoader imageLoader = null;
    //    private com.nostra13.universalimageloader.core.ImageLoader mImageLoader = null;
    private DisplayImageOptions options;

    public DriverListAdpter(Context context, JSONArray list, ImageLoader imageLoader) {
        this.context = context;
        this.mDatas = list;
        this.imageLoader = imageLoader;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_driver, null);
            holder = new ViewHolder();
            holder.iv_pic = (CubeImageView) convertView.findViewById(R.id.iv_pic);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_car = (TextView) convertView.findViewById(R.id.tv_car);
            holder.tv_num = (TextView) convertView.findViewById(R.id.tv_num);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        try {
            holder.iv_pic.loadImage(imageLoader, (String) mDatas.getJSONObject(position).get("pic"));
            holder.tv_name.setText((String) mDatas.getJSONObject(position).get("name"));
            holder.tv_car.setText((String) mDatas.getJSONObject(position).get("car"));
            holder.tv_num.setText((String) mDatas.getJSONObject(position).get("num"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return convertView;
    }

    private class ViewHolder {
        private CubeImageView iv_pic = null;
        private TextView tv_name = null;
        private TextView tv_car = null;
        private TextView tv_num = null;
    }
}
