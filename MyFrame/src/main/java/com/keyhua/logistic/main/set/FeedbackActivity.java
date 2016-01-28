package com.keyhua.logistic.main.set;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.importotherlib.R;
import com.keyhua.logistic.base.BaseActivity;
import com.keyhua.logistic.util.KeyBoardUtils;

public class FeedbackActivity extends BaseActivity {
    private EditText et_feedback = null;
    private TextView tv_commit = null;
    private ScrollView scroll_view = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initHeaderOther();
        init();
    }

    @Override
    protected void onInitData() {
        et_feedback = (EditText) findViewById(R.id.et_feedback);
        tv_commit = (TextView) findViewById(R.id.tv_commit);
        scroll_view = (ScrollView) findViewById(R.id.scroll_view);
    }

    @Override
    protected void onResload() {
        top_tv_title.setText("意见反馈");
        top_tv_right.setVisibility(View.GONE);
    }

    @Override
    protected void setMyViewClick() {
        top_itv_back.setOnClickListener(this);
        tv_commit.setOnClickListener(this);
        scroll_view.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_itv_back:
                finish();
                break;
            case R.id.tv_commit:
                showToast("意见反馈");
                break;
            case R.id.scroll_view:
                showToast("点击");
                KeyBoardUtils.openKeybord(et_feedback, FeedbackActivity.this);
                break;
        }
    }
}
