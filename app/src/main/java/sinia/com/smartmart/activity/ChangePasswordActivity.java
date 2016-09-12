package sinia.com.smartmart.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.smartmart.R;
import sinia.com.smartmart.base.BaseActivity;

/**
 * Created by 忧郁的眼神 on 2016/9/12.
 */
public class ChangePasswordActivity extends BaseActivity {


    @Bind(R.id.et_tel)
    EditText etTel;
    @Bind(R.id.et_code)
    EditText etCode;
    @Bind(R.id.tv_getCode)
    TextView tvGetCode;
    @Bind(R.id.et_pwd)
    EditText etPwd;
    @Bind(R.id.et_confirm)
    EditText etConfirm;
    @Bind(R.id.tv_ok)
    TextView tvOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepwd, "修改密码");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
        initView();
    }

    private void initView() {
    }

    @OnClick({R.id.tv_getCode, R.id.tv_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_getCode:
                break;
            case R.id.tv_ok:
                break;
        }
    }
}
