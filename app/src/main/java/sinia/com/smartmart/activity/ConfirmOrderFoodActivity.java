package sinia.com.smartmart.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.smartmart.R;
import sinia.com.smartmart.adapter.ConfirmOrderFoodAdapter;
import sinia.com.smartmart.base.BaseActivity;
import sinia.com.smartmart.utils.Utility;

import static android.R.string.ok;

/**
 * Created by 忧郁的眼神 on 2016/11/2 0002.
 */

public class ConfirmOrderFoodActivity extends BaseActivity {

    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_tel)
    TextView tvTel;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.rl_address)
    RelativeLayout rlAddress;
    @Bind(R.id.tv_sendTime)
    TextView tvSendTime;
    @Bind(R.id.et_message)
    EditText etMessage;
    @Bind(R.id.tv_payType)
    TextView tvPayType;
    @Bind(R.id.tv_redMoney)
    TextView tvRedMoney;
    @Bind(R.id.tv_shop_name)
    TextView tvShopName;
    @Bind(R.id.lv_food)
    ListView lvFood;
    @Bind(R.id.tv_boxNum)
    TextView tvBoxNum;
    @Bind(R.id.tv_boxMoney)
    TextView tvBoxMoney;
    @Bind(R.id.tv_sendMoney)
    TextView tvSendMoney;
    @Bind(R.id.tv_couponsMoney)
    TextView tvCouponsMoney;
    @Bind(R.id.tv_orderMoney)
    TextView tvOrderMoney;
    @Bind(R.id.tv_coupons)
    TextView tvCoupons;
    @Bind(R.id.tv_resultMoney)
    TextView tvResultMoney;
    @Bind(R.id.tv_payMoney)
    TextView tvPayMoney;
    @Bind(R.id.tv_coupons2)
    TextView tvCoupons2;
    @Bind(R.id.tv_buy_price)
    TextView tvBuyPrice;
    @Bind(R.id.rl_submit)
    RelativeLayout rlSubmit;

    private ConfirmOrderFoodAdapter adapter;
    private Dialog dialog;
    private String payType = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_food_order, "确认订单");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
        initData();
    }

    private void initData() {
        adapter = new ConfirmOrderFoodAdapter(this);
        lvFood.setAdapter(adapter);
        Utility.setListViewHeightBasedOnChildren(lvFood);
    }

    @OnClick({R.id.rl_address, R.id.rl_submit, R.id.rl_payType})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_address:
                break;
            case R.id.rl_payType:
                createSelectPayDialog(this, tvPayType);
                break;
            case R.id.rl_submit:
                break;
        }
    }

    private Dialog createSelectPayDialog(final Context context, final TextView tv_payType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialog_select_pay_type, null);
        dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        dialog.show();
        dialog.setContentView(v, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (display.getWidth()); // 设置宽度
        dialog.getWindow().setAttributes(lp);
        final ImageView img_alipay = (ImageView) dialog.findViewById(R.id.img_alipay);
        final ImageView img_wx = (ImageView) dialog.findViewById(R.id.img_wx);
        TextView tv_ok = (TextView) dialog.findViewById(R.id.tv_ok);
        img_alipay.setSelected(true);
        img_wx.setSelected(false);
        img_alipay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                img_alipay.setSelected(true);
                img_wx.setSelected(false);
                tv_payType.setText("支付宝");
                payType = "1";
            }
        });
        img_wx.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                img_alipay.setSelected(false);
                img_wx.setSelected(true);
                tv_payType.setText("微信");
                payType = "2";
            }
        });
        tv_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        return dialog;
    }
}
