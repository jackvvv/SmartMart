package sinia.com.smartmart.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.smartmart.R;
import sinia.com.smartmart.adapter.BuildConfirmOrderGoodsAdapter;
import sinia.com.smartmart.base.BaseActivity;
import sinia.com.smartmart.utils.Utility;

/**
 * Created by 忧郁的眼神 on 2016/11/8 0008.
 */

public class BuildConfirmOrderActivity extends BaseActivity {

    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_tel)
    TextView tvTel;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.tv_no_lift)
    TextView tvNoLift;
    @Bind(R.id.tv_lift)
    TextView tvLift;
    @Bind(R.id.et_floor)
    EditText etFloor;
    @Bind(R.id.et_message)
    EditText etMessage;
    @Bind(R.id.lv_goods)
    ListView lvGoods;
    @Bind(R.id.tv_couponsMoney)
    TextView tvCouponsMoney;
    @Bind(R.id.tv_resultMoney)
    TextView tvResultMoney;
    @Bind(R.id.tv_orderMoney)
    TextView tvOrderMoney;
    @Bind(R.id.tv_coupons)
    TextView tvCoupons;
    @Bind(R.id.tv_buy_price)
    TextView tvBuyPrice;
    private BuildConfirmOrderGoodsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_build_order, "确认订单");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
        initView();
    }

    private void initView() {
        tvNoLift.setSelected(false);
        tvLift.setSelected(true);
        tvNoLift.setTextColor(Color.BLACK);
        tvLift.setTextColor(Color.WHITE);
        adapter = new BuildConfirmOrderGoodsAdapter(this);
        lvGoods.setAdapter(adapter);
        Utility.setListViewHeightBasedOnChildren(lvGoods);
    }

    @OnClick({R.id.rl_address, R.id.tv_no_lift, R.id.tv_lift, R.id.rl_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_address:
                break;
            case R.id.tv_no_lift:
                tvNoLift.setSelected(true);
                tvLift.setSelected(false);
                tvLift.setTextColor(Color.BLACK);
                tvNoLift.setTextColor(Color.WHITE);
                break;
            case R.id.tv_lift:
                tvNoLift.setSelected(false);
                tvLift.setSelected(true);
                tvNoLift.setTextColor(Color.BLACK);
                tvLift.setTextColor(Color.WHITE);
                break;
            case R.id.rl_submit:
                break;
        }
    }
}
