package com.keyhua.logistic.main.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.importotherlib.R;
import com.keyhua.logistic.base.BaseActivity;

public class AdvertisementActivity extends BaseActivity {
    private WebView webView = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement);
        initHeaderOther();
        init();
    }

    @Override
    protected void onInitData() {
        webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl("http://baidu.com");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                return false;

            }
        });
    }

    @Override
    protected void onResload() {
        top_tv_title.setText("XXX广告");
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
