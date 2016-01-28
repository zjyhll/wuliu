package com.keyhua.logistic.main.personcenter;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.importotherlib.R;
import com.keyhua.logistic.base.BaseActivity;

public class ProfilesActivity extends BaseActivity {
    private TextView btn_yyzzh = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiles);
        initHeaderOther();
        init();
    }

    @Override
    protected void onInitData() {
        btn_yyzzh = (TextView) findViewById(R.id.btn_yyzzh);
    }

    @Override
    protected void onResload() {
        top_tv_title.setText("我的资料");
        top_tv_right.setVisibility(View.GONE);
    }

    @Override
    protected void setMyViewClick() {
        btn_yyzzh.setOnClickListener(this);
        top_itv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yyzzh:
                openActivity(BrowerPicActivity.class);
                break;
            case R.id.top_itv_back:
                finish();
                break;
        }
    }
}
