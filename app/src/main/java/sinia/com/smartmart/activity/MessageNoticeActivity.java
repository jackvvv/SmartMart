package sinia.com.smartmart.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.zcw.togglebutton.ToggleButton;

import butterknife.Bind;
import butterknife.ButterKnife;
import sinia.com.smartmart.R;
import sinia.com.smartmart.base.BaseActivity;

/**
 * Created by 忧郁的眼神 on 2016/9/8.
 */
public class MessageNoticeActivity extends BaseActivity {

    @Bind(R.id.tgb_fee)
    ToggleButton tgbFee;
    @Bind(R.id.ll_fee)
    LinearLayout llFee;
    @Bind(R.id.tgb_order)
    ToggleButton tgbOrder;
    @Bind(R.id.ll_order)
    LinearLayout llOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_notice, "消息通知");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
        initData();
    }

    private void initData() {

    }
}
