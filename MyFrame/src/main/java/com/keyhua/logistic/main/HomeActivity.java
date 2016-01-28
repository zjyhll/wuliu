package com.keyhua.logistic.main;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.example.importotherlib.R;
import com.keyhua.logistic.app.App;
import com.keyhua.logistic.base.BaseActivity;
import com.keyhua.logistic.main.home.AdvertisementActivity;
import com.keyhua.logistic.main.home.MyLisActivitytAdpter;
import com.keyhua.logistic.main.home.NetworkImageHolderView;
import com.keyhua.logistic.main.home.mydriver.MyDriverActivity;
import com.keyhua.logistic.main.home.mymerchants.MyMerchantsActivity;
import com.keyhua.logistic.main.home.orderdetails.OrderingActivity;
import com.keyhua.logistic.main.login.LoginActivity;
import com.keyhua.logistic.main.set.SetActivity;
import com.keyhua.logistic.util.CommonUtility;
import com.keyhua.logistic.view.CleareditTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class HomeActivity extends BaseActivity implements ViewPager.OnPageChangeListener, OnItemClickListener {
    private ConvenientBanner convenientBanner;//顶部广告栏控件
    private List<String> networkImages;
    private String[] images = {"http://baike.cfnet.org.cn/uploads/201310/1382021773qqnCy8W1.jpg",
            "http://img.dhtv.cn/portal/201407/14/102339fm6n80b688mo7mn7.jpg",
            "http://image6.huangye88.com/2014/03/18/cae05a287144f3a0.jpg",
            "http://docs.ebdoor.com/Image/ProductImage/0/1039/10397827_1.jpg"
    };

    //查询按钮
    private TextView tv_search = null;
    //搜索框
    private CleareditTextView ctv_search = null;
    //根据登录状态显示隐藏
    //商家登录-----------------
    private LinearLayout ll_whenlogin_sj = null;
    //当前订单
    private TextView tv_dingdan = null;
    //发货记录
    private TextView tv_jilu = null;
    //联系客服
    private TextView tv_kefu = null;
    //经纪人登录
    private LinearLayout ll_whenlogin_jjr = null;
    private TextView tv_mysj = null;
    private TextView tv_jjr_ywtongj = null;

    //第三方机构登录
    private LinearLayout ll_whenlogin_jg = null;
    private TextView tv_jg_mysj = null;
    private TextView tv_jg_ywtongj = null;

    //查询结果
    private ListView lv_search = null;
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
        setContentView(R.layout.activity_home);
        initHeaderOther();
        init();
    }

    @Override
    protected void onInitData() {

        lv_search = (ListView) findViewById(R.id.lv_search);
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
        lv_search.setAdapter(myLisActivitytAdpter);
    }

    private void initControl() {
        // 头部
        headerLayout = LayoutInflater.from(this).inflate(R.layout.lvheader_home, null, true);
        convenientBanner = (ConvenientBanner) headerLayout.findViewById(R.id.convenientBanner);
        tv_search = (TextView) headerLayout.findViewById(R.id.tv_search);
        tv_dingdan = (TextView) headerLayout.findViewById(R.id.tv_dingdan);
        tv_jilu = (TextView) headerLayout.findViewById(R.id.tv_jilu);
        tv_mysj = (TextView) headerLayout.findViewById(R.id.tv_mysj);
        tv_kefu = (TextView) headerLayout.findViewById(R.id.tv_kefu);
        tv_jjr_ywtongj = (TextView) headerLayout.findViewById(R.id.tv_jjr_ywtongj);
        tv_jg_mysj = (TextView) headerLayout.findViewById(R.id.tv_jg_mysj);
        tv_jg_ywtongj = (TextView) headerLayout.findViewById(R.id.tv_jg_ywtongj);
        ctv_search = (CleareditTextView) headerLayout.findViewById(R.id.ctv_search);
        ll_whenlogin_sj = (LinearLayout) headerLayout.findViewById(R.id.ll_whenlogin_sj);
        ll_whenlogin_jjr = (LinearLayout) headerLayout.findViewById(R.id.ll_whenlogin_jjr);
        ll_whenlogin_jg = (LinearLayout) headerLayout.findViewById(R.id.ll_whenlogin_jg);
        // 获得父类的视图布局
        headerParent = new LinearLayout(this);
        headerParent.addView(headerLayout);
        lv_search.addHeaderView(headerParent);
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
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
//                .setOnPageChangeListener(this)//监听翻页事件
                .setOnItemClickListener(this);
        //        convenientBanner.setManualPageable(false);//设置不能手动影响

    }

    @Override
    protected void setMyViewClick() {
        top_itv_back.setOnClickListener(this);
        top_itv_back.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.yaoyaole_username), null, null, null);
        top_tv_right.setOnClickListener(this);
        tv_search.setOnClickListener(this);
        tv_dingdan.setOnClickListener(this);
        tv_jilu.setOnClickListener(this);
        tv_kefu.setOnClickListener(this);
        tv_mysj.setOnClickListener(this);
        tv_jjr_ywtongj.setOnClickListener(this);
        tv_jg_mysj.setOnClickListener(this);
        tv_jg_ywtongj.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_itv_back:
//                openActivity(PersonCenterActivity.class);
                openActivity(LoginActivity.class);
                break;
            case R.id.top_tv_right:
                showToast("跳转到设置界面");
                openActivity(SetActivity.class);
                break;
            case R.id.tv_search:
                String searchStr = ctv_search.getText().toString();
                showToast("查询:" + searchStr);
                break;
            case R.id.tv_dingdan:
                showToast("跳转到当前订单");
                openActivity(OrderingActivity.class);
                break;
            case R.id.tv_jilu:
                showToast("跳转到发货记录");
                openActivity(OrderingActivity.class);
                break;
            case R.id.tv_kefu:
                showToast("跳转到运单统计");
                break;
            case R.id.tv_mysj:
                showToast("跳转到我的商家");
                openActivity(MyMerchantsActivity.class);
                break;
            case R.id.tv_jjr_ywtongj:
                showToast("跳转到业务统计");
                break;
            case R.id.tv_jg_mysj:
                showToast("跳转到我的司机");
                openActivity(MyDriverActivity.class);
                break;
            case R.id.tv_jg_ywtongj:
                showToast("跳转到业务统计");
                break;
        }
    }


    // 开始自动翻页
    @Override
    protected void onResume() {
        super.onResume();
        //开始自动翻页
        convenientBanner.startTurning(5000);
    }

    // 停止自动翻页
    @Override
    protected void onPause() {
        super.onPause();
        //停止翻页
        convenientBanner.stopTurning();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Toast.makeText(this, "监听到翻到第" + position + "了", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, "点击了第" + position + "个", Toast.LENGTH_SHORT).show();
        openActivity(AdvertisementActivity.class);
    }

    Handler handlerlist = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommonUtility.SERVEROK1:

                    break;
                case CommonUtility.SERVEROK2:
                    break;
                case CommonUtility.SERVERERRORLOGIN:
                    showToastLogin();
                    App.getInstance().setAut("");
//                    openActivity(LoginActivity.class);
                    break;
                case CommonUtility.SERVERERROR:
                    break;
                case CommonUtility.KONG:
                    break;
                default:
                    break;
            }
        }

    };


}
