package sinia.com.smartmart;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.smartmart.activity.LoginRegisterActivity;
import sinia.com.smartmart.base.BaseActivity;
import sinia.com.smartmart.utils.StringUtil;
import sinia.com.smartmart.utils.StringUtils;

public class MainActivity extends BaseActivity {

    @Bind(R.id.et_tel)
    EditText etTel;
    @Bind(R.id.et_pwd)
    EditText etPwd;
    @Bind(R.id.login)
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.login)
    public void onClick() {
        startLoginActivityForNoIntent(LoginRegisterActivity.class);
    }
}
