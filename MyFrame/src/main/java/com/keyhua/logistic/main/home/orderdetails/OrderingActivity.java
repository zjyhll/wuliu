package com.keyhua.logistic.main.home.orderdetails;

import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.importotherlib.R;
import com.keyhua.logistic.base.BaseActivity;
import com.keyhua.logistic.util.CommonUtility;
import com.keyhua.logistic.util.NetUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import in.srain.cube.loadmore.LoadMoreContainer;
import in.srain.cube.loadmore.LoadMoreHandler;
import in.srain.cube.loadmore.LoadMoreListViewContainer;
import in.srain.cube.ptr.PtrDefaultHandler;
import in.srain.cube.ptr.PtrFrameLayout;
import in.srain.cube.ptr.PtrHandler;

public class OrderingActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    // 上拉下拉刷新
    LoadMoreListViewContainer loadMoreListViewContainer = null;
    private ListView lv_home = null;
    private OrderListAdpter listadapter = null;
    private PtrFrameLayout mPtrFrameLayout;
    public int index = 0;
    public int count = 10;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering);
        initHeaderOther();
        init();
    }

    @Override
    protected void onInitData() {
        JSONArray arr = new JSONArray();
        JSONObject obj = new JSONObject();
        try {
            obj.put("odnum", "1231321321321321");
            obj.put("from", "成都");
            obj.put("to", "武汉");
            obj.put("dis", "货物已到达武汉");
            obj.put("time", "2016-01-25 22:33");
            obj.put("status", "运送中");
            arr.put(obj);
            obj = new JSONObject();
            obj.put("odnum", "1231321321321321");
            obj.put("from", "广东");
            obj.put("to", "四川");
            obj.put("dis", "货物已到达成都");
            obj.put("time", "2016-01-26 22:33");
            obj.put("status", "待取货");
            arr.put(obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        lv_home = (ListView) findViewById(R.id.lv_home);
        lv_home.setOnItemClickListener(this);
        listadapter = new OrderListAdpter(this, arr);
        lv_home.setAdapter(listadapter);
        refreshAndLoadMore();
    }

    @Override
    protected void onResload() {
        top_tv_title.setText("我的订单");
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


    private void refreshAndLoadMore() {
        // 上下刷新START--------------------------------------------------------------------
        // 获取装载VIew的容器
        mPtrFrameLayout = (PtrFrameLayout) findViewById(R.id.load_more_list_view_ptr_frame);
        // 获取view的引用
        loadMoreListViewContainer = (LoadMoreListViewContainer) findViewById(R.id.load_more_list_view_container);
        // 使用默认样式
        loadMoreListViewContainer.useDefaultHeader();
        // 加载更多数据，当列表滑动到最底部的时候，触发加载更多操作，
        // 这是需要从网络加载数据，或者是从数据库去读取数据
        // 给View 设置加载更多的Handler 去异步加载View需要显示的数据和VIew
        loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            // loadMoreListViewContainer调用onLoadMore传入loadMoreListViewContainer自身对象
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                if (NetUtil.isNetworkAvailable(OrderingActivity.this)) {// 有网
                    mHandler.sendEmptyMessage(CommonUtility.ISLOADMORE);
                } else {// 无网
                    mHandler.sendEmptyMessage(CommonUtility.ISNETCONNECTEDInt);
                }
            }
        });
        // load至少刷新多少1秒
        mPtrFrameLayout.setLoadingMinTime(1000);
        // 容器设置异步线程检查是否可以下拉刷新，并且开始下拉刷新用户头
        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                // here check list view, not content.
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, lv_home, header);
            }

            // 开始刷新容器开头
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if (NetUtil.isNetworkAvailable(OrderingActivity.this)) {// 有网
                    mHandler.sendEmptyMessage(CommonUtility.ISREFRESH);
                } else {// 无网
                    if (isshowdialog()) {
                        closedialog();
                    }
                    mHandler.sendEmptyMessage(CommonUtility.ISNETCONNECTEDInt);
                }
            }
        });
        // auto load data
        mPtrFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    mPtrFrameLayout.autoRefresh(true);
                    // mHandler.sendEmptyMessage(1);
                } catch (Exception e) {
                }
            }
        }, 150);
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 进行判断，是否存在数据
            // loadMoreListViewContainer.loadMoreFinish(mDataModel.getListPageInfo().isEmpty(),
            // mDataModel.getListPageInfo().hasMore());
            loadMoreListViewContainer.loadMoreFinish(false, true);
            switch (msg.what) {
                case CommonUtility.ISREFRESH:// 刷新
                    mPtrFrameLayout.refreshComplete();
                    break;
                case CommonUtility.ISLOADMORE:// 加载更多
                    mPtrFrameLayout.refreshComplete();
                    break;
                case CommonUtility.ISNETCONNECTEDInt:// 下拉刷新无网络时
                    mPtrFrameLayout.refreshComplete();
                    showToastNet();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        openActivity(OrderDetails.class);
    }

    // 刷新end------------------------------------------------------------------
}
