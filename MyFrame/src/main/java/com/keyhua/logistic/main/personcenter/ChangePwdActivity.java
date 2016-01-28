package com.keyhua.logistic.main.personcenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.importotherlib.R;
import com.keyhua.logistic.base.BaseActivity;
import com.keyhua.logistic.view.CleareditTextView;

public class ChangePwdActivity extends BaseActivity {
    private CleareditTextView ctv_name = null;
    private CleareditTextView ctv_yzm = null;
    private CleareditTextView ctv_pwd = null;
    private TextView tv_yzm = null;
    private TextView tv_login = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);
        initHeaderOther();
        init();
    }

    @Override
    protected void onInitData() {
        ctv_name = (CleareditTextView) findViewById(R.id.ctv_name);
        ctv_yzm = (CleareditTextView) findViewById(R.id.ctv_yzm);
        ctv_pwd = (CleareditTextView) findViewById(R.id.ctv_pwd);
        tv_yzm = (TextView) findViewById(R.id.tv_yzm);
        tv_login = (TextView) findViewById(R.id.tv_login);
    }

    @Override
    protected void onResload() {
        top_tv_title.setText("修改密码");
        top_tv_right.setVisibility(View.GONE);
    }

    @Override
    protected void setMyViewClick() {
        top_itv_back.setOnClickListener(this);
        ctv_yzm.setOnClickListener(this);
        tv_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_itv_back:
                finish();
                break;
            case R.id.ctv_yzm:
                break;
            case R.id.tv_login:
                break;
        }
    }
}
