package com.keyhua.logistic.main;

import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.example.importotherlib.R;
import com.keyhua.logistic.base.BaseActivity;
import com.keyhua.logistic.main.home.NetworkImageHolderView;

import java.util.Arrays;
import java.util.List;

public class WelcomeActivity extends BaseActivity implements ViewPager.OnPageChangeListener, OnItemClickListener {
    private ConvenientBanner convenientBanner;//顶部广告栏控件
    private List<String> networkImages;
    private String[] images = {"http://baike.cfnet.org.cn/uploads/201310/1382021773qqnCy8W1.jpg",
            "http://img.dhtv.cn/portal/201407/14/102339fm6n80b688mo7mn7.jpg",
            "http://image6.huangye88.com/2014/03/18/cae05a287144f3a0.jpg",
            "http://docs.ebdoor.com/Image/ProductImage/0/1039/10397827_1.jpg"
    };
    private TextView tv_jump = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();
    }

    @Override
    protected void onInitData() {
        convenientBanner = (ConvenientBanner) findViewById(R.id.convenientBanner);
        tv_jump = (TextView) findViewById(R.id.tv_jump);
    }

    @Override
    protected void onResload() {
        //网络加载例子
        networkImages = Arrays.asList(images);
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView(imageLoader);
            }
        }, networkImages).setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnPageChangeListener(this)//监听翻页事件
                .setOnItemClickListener(this);
        //        convenientBanner.setManualPageable(false);//设置不能手动影响
    }

    @Override
    protected void setMyViewClick() {
        tv_jump.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_jump:
                openActivity(HomeActivity.class);
                finish();
                break;
        }
    }

    // 开始自动翻页
    @Override
    protected void onResume() {
        super.onResume();
        //开始自动翻页
//        convenientBanner.startTurning(5000);
    }

    // 停止自动翻页
    @Override
    protected void onPause() {
        super.onPause();
        //停止翻页
//        convenientBanner.stopTurning();
    }

    @Override
    public void onPageScrolled(final int position, final float positionOffset, int positionOffsetPixels) {
//        showToast("position:" + position + "positionOffset:" + positionOffset + "positionOffsetPixels:" + positionOffsetPixels);
        if (position >= 7 && positionOffset > 0) {
            finish();
            openActivity(HomeActivity.class);
        }

    }

    @Override
    public void onPageSelected(int position) {
//        Toast.makeText(this, "监听到翻到第" + position + "了", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onItemClick(int position) {
//        Toast.makeText(this, "点击了第" + position + "个", Toast.LENGTH_SHORT).show();
    }
}
