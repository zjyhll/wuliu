package com.keyhua.logistic.main.set;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnDismissListener;
import com.bigkoo.alertview.OnItemClickListener;
import com.example.importotherlib.R;
import com.keyhua.logistic.base.BaseActivity;
import com.keyhua.logistic.util.CommonUtility;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class SetActivity extends BaseActivity implements OnItemClickListener, OnDismissListener {
    private RelativeLayout rl_gx = null;
    private RelativeLayout rl_fk = null;
    private RelativeLayout rl_gy = null;
    private TextView tv_tel = null;
    private TextView tv_tishi = null;
    private AlertView mAlertView;
    private AlertView telAlertView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        initHeaderOther();
        init();
    }

    @Override
    protected void onInitData() {
        rl_gx = (RelativeLayout) findViewById(R.id.rl_gx);
        rl_fk = (RelativeLayout) findViewById(R.id.rl_fk);
        rl_gy = (RelativeLayout) findViewById(R.id.rl_gy);
        tv_tel = (TextView) findViewById(R.id.tv_tel);
        tv_tishi = (TextView) findViewById(R.id.tv_tishi);
    }

    @Override
    protected void onResload() {
        mAlertView = new AlertView("温馨提示", "当前为最新版本", "取消", new String[]{"确定"}, null, this, AlertView.Style.Alert, this).setCancelable(true).setOnDismissListener(this);
        telAlertView = new AlertView("温馨提示", "是否拨打客服电话", "取消", new String[]{"确定"}, null, this, AlertView.Style.Alert, this).setCancelable(true).setOnDismissListener(this);
        top_tv_title.setText("设置");
        top_tv_right.setVisibility(View.GONE);
        // 检测更新///////////////////////
        new Thread() {
            @Override
            public void run() {
                super.run();
                isUpdate(2);
            }
        }.start();
    }

    @Override
    protected void setMyViewClick() {
        rl_gx.setOnClickListener(this);
        rl_fk.setOnClickListener(this);
        rl_gy.setOnClickListener(this);
        tv_tel.setOnClickListener(this);
        top_itv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_gx:
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        isUpdate(1);
                    }
                }.start();
                break;
            case R.id.top_itv_back:
                finish();
                break;
            case R.id.rl_fk:
                openActivity(FeedbackActivity.class);
                break;
            case R.id.rl_gy:
                openActivity(AboutActivity.class);
                break;
            case R.id.tv_tel:
                telAlertView.show();

                break;
        }
    }

    public void alertShow(View view) {
        mAlertView.show();
    }

    @Override
    public void onDismiss(Object o) {

    }

    @Override
    public void onItemClick(Object o, int position) {
        if (o == telAlertView) {
            if (position != AlertView.CANCELPOSITION) {
                //用intent启动拨打电话
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "18202811358"));
                startActivity(intent);
            }
        } else {
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (mAlertView != null && mAlertView.isShowing()) {
                mAlertView.dismiss();
                return false;
            }
        }

        return super.onKeyDown(keyCode, event);

    }


    // 检测更新///////////////////////////
    private UpdateManager mUpdateManager;
    private String installUrl;

    /**
     * android:versionCode和android:versionName两个字段分别表示版本代码，版本名称。versionCode是整型数字
     * ，versionName是字符串。versionName是给用户看的，不太容易比较大小，升级检查时，可以以检查versionCode为主，
     * 方便比较出版本的前后大小。
     */
    public void isUpdate(int index) {
        String baseUrl = "%s/manager/app_update.jsp?version=%s&platform=android";
        String checkUpdateUrl = String.format(baseUrl, CommonUtility.URLIMAIGN, getVersioncode(this));// 版本号和市场下载的编号
        HttpClient httpClient = new DefaultHttpClient();
        // 请求超时
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
        // 读取超时
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
        try {
            HttpGet httpGet = new HttpGet(checkUpdateUrl);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                String firResponse = EntityUtils.toString(httpEntity);
                JSONObject versionJsonObj = new JSONObject(firResponse);
                String update = versionJsonObj.getString("update");
                installUrl = versionJsonObj.getString("download");
                if (TextUtils.equals(update, "true")) {// 需要更新
                    if (index == 1) {
                        handler.sendEmptyMessage(0);
                    } else if (index == 2) {
                        handler.sendEmptyMessage(5);
                    }
                } else {// 不需要更新
                    if (index == 1) {
                        handler.sendEmptyMessage(1);
                    } else if (index == 2) {
                        handler.sendEmptyMessage(6);
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            handler.sendEmptyMessage(2);
            e.printStackTrace();
        } catch (ConnectTimeoutException e) {
            handler.sendEmptyMessage(2);
            e.printStackTrace();
        } catch (SocketTimeoutException e) {
            handler.sendEmptyMessage(2);
            e.printStackTrace();
        } catch (UnknownHostException e) {
            handler.sendEmptyMessage(2);
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            handler.sendEmptyMessage(2);
            e.printStackTrace();
        } catch (IOException e) {
            handler.sendEmptyMessage(2);
            e.printStackTrace();
        } catch (JSONException e) {
            handler.sendEmptyMessage(2);
            e.printStackTrace();
        }

    }

    // /** 应用是否更新 */
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:// 有版本更新
                    // 此时将计时器取消了
                    mUpdateManager = new UpdateManager(SetActivity.this, installUrl, handler);
                    mUpdateManager.checkUpdateInfo();
                    break;
                case 1:// 无版本更新
                    showToast("当前最新版本");
                    break;
                case 2:
                    showToast("更新失败，请重试或重新下载");
                    break;
                case 3:// 取消更新按钮(强制更新)
                    break;
                case 4:
                    showToast("更新失败，请重新更新");
                    break;
                case 5:
                    tv_tishi.setText("有版本更新");
                    break;
                case 6:
                    tv_tishi.setText("当前为最新版本");
                    break;
            }
        }

        ;
    };
}
