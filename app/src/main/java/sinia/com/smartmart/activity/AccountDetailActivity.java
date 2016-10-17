package sinia.com.smartmart.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.materialdialog.MaterialDialog;
import sinia.com.smartmart.R;
import sinia.com.smartmart.base.BaseActivity;
import sinia.com.smartmart.bean.BillDetailBean;
import sinia.com.smartmart.bean.FeeMessageListBean;
import sinia.com.smartmart.bean.JsonBean;
import sinia.com.smartmart.utils.ActivityManager;
import sinia.com.smartmart.utils.Constants;
import sinia.com.smartmart.utils.JsonUtil;
import sinia.com.smartmart.utils.MyApplication;
import sinia.com.smartmart.utils.Utils;

import static android.R.id.list;
import static sinia.com.smartmart.R.id.img_fee;
import static sinia.com.smartmart.R.id.tv_code;
import static sinia.com.smartmart.R.id.tv_fee_type;
import static sinia.com.smartmart.R.id.tv_status;

/**
 * Created by 忧郁的眼神 on 2016/9/28.
 */

public class AccountDetailActivity extends BaseActivity {

    @Bind(img_fee)
    ImageView imgFee;
    @Bind(R.id.tv_company)
    TextView tvCompany;
    @Bind(R.id.tv_money)
    TextView tvMoney;
    @Bind(R.id.tv_status)
    TextView tvStatus;
    @Bind(R.id.tv_payType)
    TextView tvPayType;
    @Bind(R.id.tv_feeType)
    TextView tvFeeType;
    @Bind(tv_code)
    TextView tvCode;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_orderno)
    TextView tvOrderno;
    @Bind(R.id.tv_call)
    TextView tvCall;
    @Bind(R.id.ll_fee)
    LinearLayout llFee;
    @Bind(R.id.tv_pay)
    TextView tvPay;
    @Bind(R.id.ll_notPay)
    LinearLayout llNotPay;

    private MaterialDialog materialDialog;
    private String feeType, status, billid,orderno;
    private AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail, "账单详情");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
        initDta();
    }

    private void initDta() {
        feeType = getIntent().getStringExtra("feeType");
        status = getIntent().getStringExtra("status");
        billid = getIntent().getStringExtra("billid");
        if ("1".equals(feeType)) {
            tvCompany.setText("水费");
            imgFee.setImageResource(R.drawable.ic_water_samll);
        }
        if ("2".equals(feeType)) {
            tvCompany.setText("电费");
            imgFee.setImageResource(R.drawable.ic_elec_small);
        }
        if ("3".equals(feeType)) {
            tvCompany.setText("煤气费");
            imgFee.setImageResource(R.drawable.ic_gas_small);
        }
        if ("4".equals(feeType)) {
            tvCompany.setText("物业费");
            imgFee.setImageResource(R.drawable.ic_wuye_samll);
        }

        if (status.equals("1")) {
            //未支付
            llNotPay.setVisibility(View.VISIBLE);
            llFee.setVisibility(View.GONE);
        }
        if (status.equals("2")) {
            //已支付
            llFee.setVisibility(View.VISIBLE);
            llNotPay.setVisibility(View.GONE);
        }
        getBillDetail();
    }

    private void getBillDetail() {
        showLoad("加载中...");
        RequestParams params = new RequestParams();
        params.put("memberid", MyApplication.getInstance().getUserInfo().getMemberid());
        params.put("billid", billid);
        Log.i("tag", Constants.BASE_URL + "billdetail" + params);
        client.post(Constants.BASE_URL + "billdetail", params,
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
                            BillDetailBean bean = gson.fromJson(resultStr, BillDetailBean.class);
                            setData(bean);
                        } else {
                            showToast((String) json.getRescnt());
                        }
                    }
                });
    }

    private void setData(BillDetailBean bean) {
        tvMoney.setText("-" + bean.getCost());
        if ("1".equals(bean.getBillstatus())) {
            tvStatus.setText("未支付");
        }
        if ("2".equals(bean.getBillstatus())) {
            tvStatus.setText("已支付");
        }

        if ("1".equals(bean.getPaytype())) {
            tvPayType.setText("支付宝支付");
        }
        if ("2".equals(bean.getPaytype())) {
            tvStatus.setText("微信支付");
        }
        tvFeeType.setText(bean.getPaymentinstruction());
        tvCode.setText(bean.getPaymentno());
        tvOrderno.setText(bean.getOrderno());
        tvTime.setText(bean.getCreatetime());
        orderno = bean.getOrderno();
    }

    @OnClick({R.id.tv_call, R.id.tv_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_call:
                callService();
                break;
            case R.id.tv_pay:
                //直接跳转支付宝或者微信支付，支付成功，paysuccess
                paySuccess(orderno);
                break;
        }
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
                            ActivityManager.getInstance().finishCurrentActivity();
                        } else {
                            showToast((String) json.getRescnt());
                        }
                    }
                });
    }

    private void callService() {
        materialDialog = new MaterialDialog(this);
        materialDialog.setTitle("联系物业");
        materialDialog.setMessage("400-20392888");
        materialDialog.setPositiveButton("呼叫", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "400-20392888"));
                if (ActivityCompat.checkSelfPermission(AccountDetailActivity.this, Manifest.permission.CALL_PHONE)
                        != PackageManager
                        .PERMISSION_GRANTED) {
                    return;
                }
                startActivity(intent);
                materialDialog.dismiss();
            }
        });
        materialDialog.setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDialog.dismiss();
            }
        });
        materialDialog.show();
    }
}
