package sinia.com.smartmart.activity;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_property_fee, "物业费缴纳");
        getDoingView().setVisibility(View.GONE);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_ok)
    public void onClick() {
        Intent intent = new Intent();
        intent.putExtra("fee_type", "4");
        startActivityForIntent(PayActivity.class, intent);
    }
}
