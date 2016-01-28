package com.keyhua.logistic.main.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.importotherlib.R;
import com.keyhua.logistic.base.BaseActivity;
import com.keyhua.logistic.main.personcenter.PersonCenterActivity;
import com.keyhua.logistic.view.CleareditTextView;

public class LoginActivity extends BaseActivity {
    //username
    private CleareditTextView ctv_login = null;
    //password
    private CleareditTextView ctv_pwd = null;
    //登录按钮
    private TextView tv_login = null;
    //忘记密码按钮
    private TextView tv_forgetpwd = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initHeaderOther();
        init();
    }

    @Override
    protected void onInitData() {
        ctv_login = (CleareditTextView) findViewById(R.id.ctv_login);
        ctv_pwd = (CleareditTextView) findViewById(R.id.ctv_pwd);
        tv_login = (TextView) findViewById(R.id.tv_login);
        tv_forgetpwd = (TextView) findViewById(R.id.tv_forgetpwd);
    }

    @Override
    protected void onResload() {
        top_tv_title.setText("登录");
        top_tv_right.setVisibility(View.GONE);
    }

    @Override
    protected void setMyViewClick() {
        tv_login.setOnClickListener(this);
        tv_forgetpwd.setOnClickListener(this);
        top_itv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                openActivity(PersonCenterActivity.class);
                finish();
                break;
            case R.id.tv_forgetpwd:
                openActivity(ForgetPwdActivity.class);
                finish();
                break;
            case R.id.top_itv_back:
                finish();
                break;
        }
    }
}
