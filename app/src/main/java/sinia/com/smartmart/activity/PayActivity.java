package sinia.com.smartmart.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.smartmart.R;
import sinia.com.smartmart.base.BaseActivity;
import sinia.com.smartmart.utils.ActivityManager;

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
    private String fee_type;//1.水费，2.电费，3.煤气费，4.物业费
    private String pay_type = "1";//1.支付宝，2.微信

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay, "支付缴费");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
        fee_type = getIntent().getStringExtra("fee_type");
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
                startActivityForNoIntent(PayResultActivity.class);
                ActivityManager.getInstance().finishCurrentActivity();
                break;
        }
    }
}
