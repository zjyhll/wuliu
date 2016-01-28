package com.keyhua.logistic.main.home;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.example.importotherlib.R;

import in.srain.cube.image.CubeImageView;
import in.srain.cube.image.ImageLoader;

/**
 * Created by Sai on 15/8/4.
 * 网络图片加载例子
 */
public class NetworkImageHolderView implements Holder<String> {
    private CubeImageView imageView;
    private ImageLoader imageLoader;

    public NetworkImageHolderView(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    @Override
    public View createView(Context context) {
        //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
        imageView = new CubeImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        ImageLoader imageLoader = ImageLoaderFatory.create(context);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, String data) {
        imageView.setImageResource(R.mipmap.ic_default_adimage);
//        ImageLoader.getInstance().displayImage(data, imageView);
        imageView.loadImage(imageLoader, data);
    }
}
