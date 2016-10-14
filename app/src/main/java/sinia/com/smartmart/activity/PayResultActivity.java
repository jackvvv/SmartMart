package sinia.com.smartmart.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.smartmart.R;
import sinia.com.smartmart.base.BaseActivity;
import sinia.com.smartmart.utils.ActivityManager;
import sinia.com.smartmart.utils.StringUtil;

/**
 * Created by 忧郁的眼神 on 2016/9/7.
 */
public class PayResultActivity extends BaseActivity {

    @Bind(R.id.img_result)
    ImageView imgResult;
    @Bind(R.id.tv_result)
    TextView tvResult;
    @Bind(R.id.tv_orderno)
    TextView tvOrderno;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_ok)
    TextView tvOk;

    private String orderno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_result, "支付缴费");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
        initData();
    }

    private void initData() {
        orderno = getIntent().getStringExtra("orderno");
        tvOrderno.setText("订单号：" + orderno);
        tvTime.setText("支付时间：" + StringUtil.getCurrentDate());
    }

    @OnClick(R.id.tv_ok)
    public void onClick() {
        ActivityManager.getInstance().finishCurrentActivity();
        ActivityManager.getInstance().finishActivity(PayFeeActivity.class);
    }
}
