package sinia.com.smartmart.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.smartmart.R;
import sinia.com.smartmart.base.BaseActivity;

/**
 * Created by 忧郁的眼神 on 2016/9/8.
 */
public class SettingsActivity extends BaseActivity {

    @Bind(R.id.ll_personal)
    LinearLayout llPersonal;
    @Bind(R.id.ll_address)
    LinearLayout llAddress;
    @Bind(R.id.ll_msg)
    LinearLayout llMsg;
    @Bind(R.id.ll_usual)
    LinearLayout llUsual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings, "设置");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);

    }

    @OnClick({R.id.ll_personal, R.id.ll_address, R.id.ll_msg, R.id.ll_usual})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_personal:
                startActivityForNoIntent(PersonalInfoActivity.class);
                break;
            case R.id.ll_address:
                startActivityForNoIntent(AddressManagerActivity.class);
                break;
            case R.id.ll_msg:
                startActivityForNoIntent(MessageNoticeActivity.class);
                break;
            case R.id.ll_usual:
                startActivityForNoIntent(GeneralSettingsActivity.class);
                break;
        }
    }
}
