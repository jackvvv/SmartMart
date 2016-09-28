package sinia.com.smartmart.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.smartmart.R;
import sinia.com.smartmart.base.BaseActivity;

/**
 * Created by 忧郁的眼神 on 2016/9/28.
 */

public class AccountDetailActivity extends BaseActivity {

    @Bind(R.id.img_fee)
    ImageView imgFee;
    @Bind(R.id.tv_company)
    TextView tvCompany;
    @Bind(R.id.tv_money)
    TextView tvMoney;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_payType)
    TextView tvPayType;
    @Bind(R.id.tv_feeType)
    TextView tvFeeType;
    @Bind(R.id.tv_code)
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail, "账单详情");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
    }

    @OnClick({R.id.tv_call, R.id.tv_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_call:
                break;
            case R.id.tv_pay:
                break;
        }
    }
}
