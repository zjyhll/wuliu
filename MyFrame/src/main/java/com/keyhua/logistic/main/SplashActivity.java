package com.keyhua.logistic.main;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.importotherlib.R;
import com.keyhua.logistic.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
    }

    @Override
    protected void onInitData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                openActivity(WelcomeActivity.class);
                finish();
            }
        }, 1000);

    }

    @Override
    protected void onResload() {

    }

    @Override
    protected void setMyViewClick() {

    }

    @Override
    public void onClick(View v) {

    }
}
