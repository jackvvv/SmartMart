package sinia.com.smartmart.activity;

import android.content.Intent;
import android.os.Bundle;
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
import sinia.com.smartmart.R;
import sinia.com.smartmart.base.BaseActivity;
import sinia.com.smartmart.bean.FeeDetailBean;
import sinia.com.smartmart.bean.JsonBean;
import sinia.com.smartmart.bean.PropertyFeeBean;
import sinia.com.smartmart.utils.Constants;
import sinia.com.smartmart.utils.JsonUtil;
import sinia.com.smartmart.utils.MyApplication;
import sinia.com.smartmart.utils.Utils;

/**
 * Created by 忧郁的眼神 on 2016/9/7.
 */
public class PayPropertyFeeActivity extends BaseActivity {


    @Bind(R.id.img_fee)
    ImageView imgFee;
    @Bind(R.id.tv_fee)
    TextView tvFee;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.tv_area)
    TextView tvArea;
    @Bind(R.id.tv_company)
    TextView tvCompany;
    @Bind(R.id.tv_current_fee)
    TextView tvCurrentFee;
    @Bind(R.id.tv_money)
    TextView tvMoney;
    @Bind(R.id.tv_time_s)
    TextView tvTimeS;
    @Bind(R.id.tv_time_e)
    TextView tvTimeE;
    @Bind(R.id.ll_fee)
    LinearLayout llFee;
    @Bind(R.id.ll_nofee)
    LinearLayout llNofee;
    @Bind(R.id.tv_ok)
    TextView tvOk;

    private AsyncHttpClient client = new AsyncHttpClient();
    private Double money;
    private String rateid, isFromProperty;//2.从缴费提醒跳转

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_property_fee, "物业费缴纳");
        getDoingView().setVisibility(View.GONE);
        ButterKnife.bind(this);
        isFromProperty = getIntent().getStringExtra("isFromProperty");
        if ("2".equals(isFromProperty)) {
            rateid = getIntent().getStringExtra("rateid");
            getFeeDetail();
        } else {
            initData();
        }
    }

    private void getFeeDetail() {
        showLoad("加载中...");
        RequestParams params = new RequestParams();
        params.put("memberid", MyApplication.getInstance().getUserInfo().getMemberid());
        params.put("rateid", rateid);
        Log.i("tag", Constants.BASE_URL + "readratemessage" + params);
        client.post(Constants.BASE_URL + "readratemessage", params,
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
                            PropertyFeeBean feeBean = gson.fromJson(resultStr, PropertyFeeBean.class);
                            setData(feeBean);
                        } else {
                            showToast((String) json.getRescnt());
                        }
                    }
                });
    }

    private void initData() {
        RequestParams params = new RequestParams();
        params.put("memberid", MyApplication.getInstance().getUserInfo().getMemberid());
        Log.i("tag", Constants.BASE_URL + "propertyrate" + params);
        client.post(Constants.BASE_URL + "propertyrate", params,
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
                            PropertyFeeBean detailBean = gson.fromJson(resultStr, PropertyFeeBean.class);
                            setData(detailBean);
                        } else {
                            showToast((String) json.getRescnt());
                        }
                    }
                });
    }

    private void setData(PropertyFeeBean detailBean) {
        tvName.setText(detailBean.getUsername());
        tvAddress.setText(detailBean.getAddress());
        tvArea.setText(detailBean.getHousearea() + "㎡");
        tvCompany.setText(detailBean.getCompanyname());
        if (0 == detailBean.getRatecost()) {
            llNofee.setVisibility(View.VISIBLE);
            llFee.setVisibility(View.GONE);
            tvOk.setBackgroundResource(R.drawable.btn_ok_gray);
            tvOk.setClickable(false);
        } else {
            llNofee.setVisibility(View.GONE);
            llFee.setVisibility(View.VISIBLE);
            tvMoney.setText(detailBean.getRatecost() + "元");
        }
        money = detailBean.getRatecost();
        tvTimeS.setText(detailBean.getProfeetime());
    }

    @OnClick(R.id.tv_ok)
    public void onClick() {
        Intent intent = new Intent();
        intent.putExtra("fee_type", "4");
        intent.putExtra("fee", money + "");
        startActivityForIntent(PayActivity.class, intent);
    }
}
