package com.keyhua.logistic.main.home.orderdetails;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.example.importotherlib.R;
import com.keyhua.logistic.base.BaseActivity;
import com.keyhua.logistic.main.home.MyLisActivitytAdpter;
import com.keyhua.logistic.view.CleareditTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OrderDetails extends BaseActivity {
    //运单编号
    private TextView tv_num = null;
    //运单状态
    private TextView tv_status = null;
    //查询结果
    private ListView lv_details = null;
    private LinearLayout ll = null;
    //适配器
    private MyLisActivitytAdpter myLisActivitytAdpter = null;
    private JSONArray searchResultBean_Array = null;
    private JSONArray searchResultBean_Array1 = null;
    private JSONObject searchResultBean_Object = null;
    private JSONObject searchResultBean_Object1 = null;
    // 头部
    private View headerLayout = null;
    private LinearLayout headerParent = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        initHeaderOther();
        init();
    }

    @Override
    protected void onInitData() {

        lv_details = (ListView) findViewById(R.id.lv_details);
        initControl();
        //
        searchResultBean_Array1 = new JSONArray();
        searchResultBean_Object = new JSONObject();
        searchResultBean_Array = new JSONArray();
        searchResultBean_Object1 = new JSONObject();
        try {
            //第一天
            searchResultBean_Object.put("time", "10:30");
            searchResultBean_Object.put("des", "成都");
            searchResultBean_Array.put(searchResultBean_Object);
            searchResultBean_Object = new JSONObject();
            searchResultBean_Object.put("time", "10:40");
            searchResultBean_Object.put("des", "成都北门");
            searchResultBean_Array.put(searchResultBean_Object);
            searchResultBean_Object = new JSONObject();
            searchResultBean_Object.put("time", "11:40");
            searchResultBean_Object.put("des", "五块石客运站");
            searchResultBean_Array.put(searchResultBean_Object);
            searchResultBean_Object1 = new JSONObject();
            searchResultBean_Object1.put("data", searchResultBean_Array);
            searchResultBean_Object1.put("date", "2016-1-24");
            searchResultBean_Array1.put(searchResultBean_Object1);
            //第二天
            searchResultBean_Array = new JSONArray();
            searchResultBean_Object = new JSONObject();
            searchResultBean_Object.put("time", "11:30");
            searchResultBean_Object.put("des", "大丰");
            searchResultBean_Array.put(searchResultBean_Object);
            searchResultBean_Object = new JSONObject();
            searchResultBean_Object.put("time", "14:30");
            searchResultBean_Object.put("des", "邮件已签收");
            searchResultBean_Array.put(searchResultBean_Object);
            searchResultBean_Object1 = new JSONObject();
            searchResultBean_Object1.put("data", searchResultBean_Array);
            searchResultBean_Object1.put("date", "2016-1-25");
            searchResultBean_Array1.put(searchResultBean_Object1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        myLisActivitytAdpter = new MyLisActivitytAdpter(this, searchResultBean_Array1);
        lv_details.setAdapter(myLisActivitytAdpter);
    }

    private void initControl() {
        // 头部
        headerLayout = LayoutInflater.from(this).inflate(R.layout.lvheader_orderdetails, null, true);
        tv_num = (TextView) headerLayout.findViewById(R.id.tv_num);
        tv_status = (TextView) headerLayout.findViewById(R.id.tv_status);
        ll = (LinearLayout) headerLayout.findViewById(R.id.ll);
        ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        // 获得父类的视图布局
        headerParent = new LinearLayout(this);
        headerParent.addView(headerLayout);
        lv_details.addHeaderView(headerParent);
    }

    @Override
    protected void onResload() {
        top_tv_title.setText("订单详情");
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
