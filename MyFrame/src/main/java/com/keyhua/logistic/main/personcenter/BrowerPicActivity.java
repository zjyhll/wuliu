package com.keyhua.logistic.main.personcenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.example.importotherlib.R;
import com.keyhua.logistic.base.BaseActivity;
import com.keyhua.logistic.base.BrowerPicBaseActivity;

public class BrowerPicActivity extends BrowerPicBaseActivity {
    private PhotoView img = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brower_pic);
        initHeaderOther();
        init();
    }

    @Override
    protected void onInitData() {
        img = (PhotoView) findViewById(R.id.img);
        img.enable();
        Glide .with(this)
                .load("http://docs.ebdoor.com/Image/ProductImage/0/1039/10397827_1.jpg")
                .into(img);
//        mImageLoader.displayImage("http://docs.ebdoor.com/Image/ProductImage/0/1039/10397827_1.jpg", img);
    }

    @Override
    protected void onResload() {
        top_tv_title.setText("查看图片");
        top_tv_right.setVisibility(View.GONE);
    }

    @Override
    protected void setMyViewClick() {
        top_itv_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_itv_back:
                finish();
                break;
        }
    }
}
