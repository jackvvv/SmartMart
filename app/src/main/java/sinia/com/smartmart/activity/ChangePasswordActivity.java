package sinia.com.smartmart.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.smartmart.R;
import sinia.com.smartmart.base.BaseActivity;
import sinia.com.smartmart.utils.StringUtils;
import sinia.com.smartmart.utils.ValidationUtils;

/**
 * Created by 忧郁的眼神 on 2016/9/12.
 */
public class ChangePasswordActivity extends BaseActivity {

    @Pattern(regex = "^(13[0-9]|15[0-9]|17[0-9]|18[0-9]|14[0-9])[0-9]{8}$", message = "请输入正确的手机号码")
    @Order(1)
    @Bind(R.id.et_tel)
    EditText etTel;
    @NotEmpty(message = "请输入验证码")
    @Order(2)
    @Bind(R.id.et_code)
    EditText etCode;
    @Bind(R.id.tv_getCode)
    TextView tvGetCode;
    @Password(sequence = 1, message = "请输入新密码")
    @Order(3)
    @Bind(R.id.et_pwd)
    EditText etPwd;
    @Order(4)
    @ConfirmPassword(message = "两次输入的密码不一致，请重新输入")
    @Bind(R.id.et_confirm)
    EditText etConfirm;
    @Bind(R.id.tv_ok)
    TextView tvOk;

    private Validator validator;
    private int i = 60;
    private String code;
    private AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepwd, "修改密码");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
        initView();
    }

    private void initView() {
        validator = new Validator(this);
        validator.setValidationListener(new ValidationUtils.ValidationListener() {
            @Override
            public void onValidationSucceeded() {
                super.onValidationSucceeded();
//                if (!etCode.getEditableText().toString().trim().equals(code)) {
//                    showToast("验证码不正确");
//                    return;
//                }
//                findPassword();
            }
        });
    }

    @OnClick({R.id.tv_getCode, R.id.tv_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_getCode:
                if (!StringUtils.isMobileNumber(etTel.getEditableText().toString().trim())) {
                    showToast("请输入正确的手机号码");
                } else {
                    tvGetCode.setClickable(false);
                    tvGetCode.setText("重新发送(" + i + ")");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for (; i > 0; i--) {
                                handler.sendEmptyMessage(-9);
                                if (i <= 0) {
                                    break;
                                }
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            handler.sendEmptyMessage(-8);
                        }
                    }).start();
//                    getCode();
                }
                break;
            case R.id.tv_ok:
                validator.validate();
                break;
        }
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == -9) {
                tvGetCode.setText("重新发送(" + i + ")");
            } else if (msg.what == -8) {
                tvGetCode.setText("获取验证码");
                tvGetCode.setClickable(true);
                i = 60;
            }
        }
    };
}
