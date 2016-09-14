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

/**
 * Created by 忧郁的眼神 on 2016/9/14.
 */
public class MessageWarnActivity extends BaseActivity {

    @Bind(R.id.tv_order_msg_num)
    TextView tvOrderMsgNum;
    @Bind(R.id.s)
    ImageView s;
    @Bind(R.id.ll_order)
    RelativeLayout llOrder;
    @Bind(R.id.tv_fee_msg_num)
    TextView tvFeeMsgNum;
    @Bind(R.id.ss)
    ImageView ss;
    @Bind(R.id.ll_fee)
    RelativeLayout llFee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_warn, "消息提醒");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
    }

    @OnClick({R.id.ll_order, R.id.ll_fee})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_order:
                startActivityForNoIntent(OrderMessageActivity.class);
                break;
            case R.id.ll_fee:
                startActivityForNoIntent(FeeMessageActivity.class);
                break;
        }
    }
}
