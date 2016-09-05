package sinia.com.smartmart.activity;

import android.content.Intent;
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

/**
 * Created by 忧郁的眼神 on 2016/9/5.
 */
public class FindPasswordActivity extends BaseActivity {

    @Bind(R.id.back)
    TextView back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.et_tel)
    EditText etTel;
    @Bind(R.id.et_code)
    EditText etCode;
    @Bind(R.id.tl)
    TextInputLayout tl;
    @Bind(R.id.tv_getCode)
    TextView tvGetCode;
    @Bind(R.id.et_pwd)
    EditText etPwd;
    @Bind(R.id.et_confirm)
    EditText etConfirm;
    @Bind(R.id.tv_ok)
    TextView tvOk;
    @Bind(R.id.tv_login)
    TextView tvLogin;
    @Bind(R.id.tv_register)
    TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpwd);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {

    }

    @OnClick({R.id.back, R.id.tv_getCode, R.id.tv_ok, R.id.tv_login, R.id.tv_register})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.back:
                ActivityManager.getInstance().finishCurrentActivity();
                overridePendingTransition(0, R.anim.login_close);
                break;
            case R.id.tv_getCode:
                break;
            case R.id.tv_ok:
                break;
            case R.id.tv_login:
                intent = new Intent();
                intent.putExtra("from_findpwd", "1");
                ActivityManager.getInstance().finishActivity(LoginRegisterActivity.class);
                startLoginActivityForIntent(LoginRegisterActivity.class, intent);
                ActivityManager.getInstance().finishCurrentActivity();
                break;
            case R.id.tv_register:
                intent = new Intent();
                intent.putExtra("from_findpwd", "2");
                ActivityManager.getInstance().finishActivity(LoginRegisterActivity.class);
                startLoginActivityForIntent(LoginRegisterActivity.class, intent);
                ActivityManager.getInstance().finishCurrentActivity();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityManager.getInstance().finishCurrentActivity();
        overridePendingTransition(0, R.anim.login_close);
    }
}
