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

    private String fee_type;//1.水费，2.电费，3.煤气费，4.物业费

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fee_type = getIntent().getStringExtra("fee_type");
        setTitle();
        getDoingView().setVisibility(View.GONE);
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
        } else if ("4".equals(fee_type)) {
            setContentView(R.layout.activity_pay_fee, "物业费缴纳");
            ButterKnife.bind(this);
            imgFee.setImageResource(R.drawable.ic_wuye_samll);
            tvFee.setText("物业费");
            tvCurrentFee.setText("当前物业费");
        }
    }

    @OnClick(R.id.tv_ok)
    public void onClick() {
        Intent intent = new Intent();
        intent.putExtra("fee_type", fee_type);
        startActivityForIntent(PayActivity.class, intent);
    }
}
