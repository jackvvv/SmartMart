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
import sinia.com.smartmart.bean.FeeMessageListBean;
import sinia.com.smartmart.bean.JsonBean;
import sinia.com.smartmart.utils.Constants;
import sinia.com.smartmart.utils.JsonUtil;
import sinia.com.smartmart.utils.MyApplication;
import sinia.com.smartmart.utils.Utils;

/**
 * Created by 忧郁的眼神 on 2016/9/6.
 */
public class PayFeeActivity extends BaseActivity {

    @Bind(R.id.img_fee)
    ImageView imgFee;
    @Bind(R.id.tv_fee)
    TextView tvFee;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.tv_current_fee)
    TextView tvCurrentFee;
    @Bind(R.id.tv_money)
    TextView tvMoney;
    @Bind(R.id.tv_company)
    TextView tvCompany;
    @Bind(R.id.tv_card)
    TextView tvCard;
    @Bind(R.id.tv_ok)
    TextView tvOk;
    @Bind(R.id.ll_fee)
    LinearLayout llFee;
    @Bind(R.id.ll_nofee)
    LinearLayout llNofee;

    private String rateid, fee_type;//1.水费，2.电费，3.煤气费，4.物业费
    private String companyname, rateno;
    private Double money;
    private String isFromProperty;//1从物业首页跳转过来的,2.从欠费提醒，3.从添加水费账号后跳转
    private FeeDetailBean feeBean;
    private AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fee_type = getIntent().getStringExtra("fee_type");
        isFromProperty = getIntent().getStringExtra("isFromProperty");
        setTitle();
        getDoingView().setVisibility(View.GONE);
        if (isFromProperty.equals("1")) {
            //从物业首页跳转
            feeBean = (FeeDetailBean) getIntent().getSerializableExtra("feeBean");
            setData(feeBean);
        } else if (isFromProperty.equals("2")) {
            //从消息提醒跳转，请求欠费详情
            rateid = getIntent().getStringExtra("rateid");
            getFeeDetail();
        } else {
            companyname = getIntent().getStringExtra("companyname");
            rateno = getIntent().getStringExtra("rateno");
            getFeeFirstIn();
        }
    }

    private void getFeeFirstIn() {
        showLoad("加载中...");
        RequestParams params = new RequestParams();
        params.put("memberid", MyApplication.getInstance().getUserInfo().getMemberid());
        params.put("companyname", companyname);
        params.put("rateno", rateno);
        params.put("ratetype", fee_type);
        Log.i("tag", Constants.BASE_URL + "perfectmemberrate" + params);
        client.post(Constants.BASE_URL + "perfectmemberrate", params,
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
                            feeBean = gson.fromJson(resultStr, FeeDetailBean.class);
                            setData(feeBean);
                        } else {
                            showToast((String) json.getRescnt());
                        }
                    }
                });
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
                            feeBean = gson.fromJson(resultStr, FeeDetailBean.class);
                            setData(feeBean);
                        } else {
                            showToast((String) json.getRescnt());
                        }
                    }
                });
    }

    private void setData(FeeDetailBean detailBean) {
        tvName.setText(detailBean.getUsername());
        tvAddress.setText(detailBean.getAddress());
        tvCompany.setText(detailBean.getCompanyname());
        tvCard.setText(detailBean.getRateno());
        money = detailBean.getRatecost();
        if (0 == detailBean.getRatecost()) {
            llNofee.setVisibility(View.VISIBLE);
            llFee.setVisibility(View.GONE);
            tvOk.setBackgroundResource(R.drawable.btn_ok_gray);
            tvOk.setClickable(false);
        } else {
            llFee.setVisibility(View.VISIBLE);
            llNofee.setVisibility(View.GONE);
            tvMoney.setText(detailBean.getRatecost() + "元");
        }
    }

    private void setTitle() {
        if ("1".equals(fee_type)) {
            setContentView(R.layout.activity_pay_fee, "水费缴纳");
            ButterKnife.bind(this);
            imgFee.setImageResource(R.drawable.ic_water_samll);
            tvFee.setText("水费");
            tvCurrentFee.setText("当前水费");
        } else if ("2".equals(fee_type)) {
            setContentView(R.layout.activity_pay_fee, "电费缴纳");
            ButterKnife.bind(this);
            imgFee.setImageResource(R.drawable.ic_elec_small);
            tvFee.setText("电费");
            tvCurrentFee.setText("当前电费");
        } else if ("3".equals(fee_type)) {
            setContentView(R.layout.activity_pay_fee, "煤气费缴纳");
            ButterKnife.bind(this);
            imgFee.setImageResource(R.drawable.ic_gas_small);
            tvFee.setText("煤气费");
            tvCurrentFee.setText("当前煤气费");
        }
    }

    @OnClick(R.id.tv_ok)
    public void onClick() {
        Intent intent = new Intent();
        intent.putExtra("fee_type", fee_type);
        intent.putExtra("isFromProperty", isFromProperty);
        intent.putExtra("fee", money + "");
        if (isFromProperty.equals("2")) {
            intent.putExtra("rateid", rateid);
        }
        startActivityForIntent(PayActivity.class, intent);
    }
}
