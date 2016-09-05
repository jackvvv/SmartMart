package sinia.com.smartmart.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.smartmart.R;
import sinia.com.smartmart.base.BaseActivity;
import sinia.com.smartmart.utils.ActivityManager;
import sinia.com.smartmart.utils.DialogUtils;

/**
 * Created by 忧郁的眼神 on 2016/9/5.
 */
public class PerfectInfoActivity extends BaseActivity {

    @Bind(R.id.tv_area)
    TextView tvArea;
    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.tl)
    TextInputLayout tl;
    @Bind(R.id.et_building)
    EditText etBuilding;
    @Bind(R.id.tv_getCode)
    TextView tvGetCode;
    @Bind(R.id.et_cell)
    EditText etCell;
    @Bind(R.id.et_floor)
    EditText etFloor;
    @Bind(R.id.et_doornum)
    EditText etDoornum;
    @Bind(R.id.tv_ok)
    TextView tvOk;
    @Bind(R.id.tv_login)
    TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfect_info, "完善注册信息");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
    }

    @OnClick({R.id.tv_area, R.id.tv_ok, R.id.tv_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_area:
                break;
            case R.id.tv_ok:
                DialogUtils.createRegisterFailedTipsDialog(this);
                break;
            case R.id.tv_login:
                startLoginActivityForNoIntent(LoginRegisterActivity.class);
                ActivityManager.getInstance().finishCurrentActivity();
                break;
        }
    }
}
