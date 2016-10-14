package sinia.com.smartmart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.smartmart.R;
import sinia.com.smartmart.base.BaseActivity;
import sinia.com.smartmart.bean.FeeDetailBean;
import sinia.com.smartmart.bean.FeeMessageListBean;
import sinia.com.smartmart.bean.JsonBean;
import sinia.com.smartmart.bean.PayResultBean;
import sinia.com.smartmart.utils.ActivityManager;
import sinia.com.smartmart.utils.Constants;
import sinia.com.smartmart.utils.JsonUtil;
import sinia.com.smartmart.utils.MyApplication;
import sinia.com.smartmart.utils.Utils;

/**
 * Created by 忧郁的眼神 on 2016/9/6.
 */
public class PayActivity extends BaseActivity {


    @Bind(R.id.tv_fee_type)
    TextView tv_fee_type;
    @Bind(R.id.tv_money)
    TextView tvMoney;
    @Bind(R.id.img_alipay)
    ImageView imgAlipay;
    @Bind(R.id.rl_alipay)
    RelativeLayout rlAlipay;
    @Bind(R.id.img_wx)
    ImageView imgWx;
    @Bind(R.id.rl_wx)
    RelativeLayout rlWx;
    @Bind(R.id.tv_ok)
    TextView tvOk;
    private String fee, rateid, fee_type;//1.水费，2.电费，3.煤气费，4.物业费
    private String pay_type = "1";//1.支付宝，2.微信
    private String method, isFromProperty;//是否从物业首页跳转过来的
    private AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay, "支付缴费");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
        isFromProperty = getIntent().getStringExtra("isFromProperty");
        fee_type = getIntent().getStringExtra("fee_type");
        rateid = getIntent().getStringExtra("rateid");
        fee = getIntent().getStringExtra("fee");
        initData();
    }

    private void initData() {
        if ("1".equals(fee_type)) {
            tv_fee_type.setText("水费");
        }
        if ("2".equals(fee_type)) {
            tv_fee_type.setText("电费");
        }
        if ("3".equals(fee_type)) {
            tv_fee_type.setText("煤气费");
        }
        if ("4".equals(fee_type)) {
            tv_fee_type.setText("物业费");
        }
        tvMoney.setText("¥" + fee);
        rlAlipay.setSelected(true);
        imgAlipay.setSelected(true);
        rlWx.setSelected(false);
        imgWx.setSelected(false);
    }

    @OnClick({R.id.rl_alipay, R.id.rl_wx, R.id.tv_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_alipay:
                rlAlipay.setSelected(true);
                imgAlipay.setSelected(true);
                rlWx.setSelected(false);
                imgWx.setSelected(false);
                pay_type = "1";
                break;
            case R.id.rl_wx:
                rlAlipay.setSelected(false);
                imgAlipay.setSelected(false);
                rlWx.setSelected(true);
                imgWx.setSelected(true);
                pay_type = "2";
                break;
            case R.id.tv_ok:
                pay();
                break;
        }
    }

    private void pay() {
        showLoad("加载中...");
        RequestParams params = new RequestParams();
        params.put("memberid", MyApplication.getInstance().getUserInfo().getMemberid());
        if ("2".equals(isFromProperty)) {
            //从缴费信息进入 无rateid传递
            params.put("rateid", rateid);
            method = "addbillbymessage";
        } else {
            method = "addbillbymember";
        }
        params.put("paytype", pay_type);
        Log.i("tag", Constants.BASE_URL + method + params);
        client.post(Constants.BASE_URL + method, params,
                new AsyncHttpResponseHandler() {

                    @Override
                    public void onFailure(Throwable arg0, String arg1) {
                        super.onFailure(arg0, arg1);
                    }

                    @Override
                    public void onSuccess(int arg0, String s) {
                        dismiss();
                        String resultStr = Utils
                                .getStrVal(new String(s));
                        JsonBean json = JsonUtil.getJsonBean(resultStr);
                        Gson gson = new Gson();
                        int rescode = json.getRescode();
                        if (0 == rescode) {
                            PayResultBean detailBean = gson.fromJson(resultStr, PayResultBean.class);
                            paySuccess(detailBean.getOrderno());
                        } else {
                            showToast((String) json.getRescnt());
                        }
                    }
                });
    }

    private void paySuccess(final String orderno) {
        showLoad("加载中...");
        RequestParams params = new RequestParams();
        params.put("memberid", MyApplication.getInstance().getUserInfo().getMemberid());
        params.put("orderno", orderno);
        Log.i("tag", Constants.BASE_URL + "paysuccess" + params);
        client.post(Constants.BASE_URL + "paysuccess", params,
                new AsyncHttpResponseHandler() {

                    @Override
                    public void onFailure(Throwable arg0, String arg1) {
                        super.onFailure(arg0, arg1);
                    }

                    @Override
                    public void onSuccess(int arg0, String s) {
                        dismiss();
                        String resultStr = Utils
                                .getStrVal(new String(s));
                        JsonBean json = JsonUtil.getJsonBean(resultStr);
                        Gson gson = new Gson();
                        int rescode = json.getRescode();
                        if (0 == rescode) {
                            Intent intent = new Intent();
                            intent.putExtra("orderno", orderno);
                            startActivityForIntent(PayResultActivity.class, intent);
                            ActivityManager.getInstance().finishActivity(AddWaterAccountActivity.class);
                            ActivityManager.getInstance().finishActivity(PayFeeActivity.class);
                            ActivityManager.getInstance().finishActivity(PayPropertyFeeActivity.class);
                            ActivityManager.getInstance().finishCurrentActivity();
                        } else {
                            showToast((String) json.getRescnt());
                        }
                    }
                });
    }
}
