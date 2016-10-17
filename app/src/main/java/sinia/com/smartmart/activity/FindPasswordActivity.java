package sinia.com.smartmart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
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
import sinia.com.smartmart.bean.JsonBean;
import sinia.com.smartmart.bean.ValidateCodeBean;
import sinia.com.smartmart.utils.ActivityManager;
import sinia.com.smartmart.utils.Constants;
import sinia.com.smartmart.utils.JsonUtil;
import sinia.com.smartmart.utils.StringUtils;
import sinia.com.smartmart.utils.Utils;
import sinia.com.smartmart.utils.ValidationUtils;

import static android.icu.lang.UScript.getCode;

/**
 * Created by 忧郁的眼神 on 2016/9/5.
 */
public class FindPasswordActivity extends BaseActivity {

    @Bind(R.id.back)
    TextView back;
    @Bind(R.id.title)
    TextView title;
    @Pattern(regex = "^(13[0-9]|15[0-9]|17[0-9]|18[0-9]|14[0-9])[0-9]{8}$", message = "请输入正确的手机号码")
    @Order(1)
    @Bind(R.id.et_tel)
    EditText etTel;
    @NotEmpty(message = "请输入验证码")
    @Order(2)
    @Bind(R.id.et_code)
    EditText etCode;
    @Bind(R.id.tl)
    TextInputLayout tl;
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
    @Bind(R.id.tv_login)
    TextView tvLogin;
    @Bind(R.id.tv_register)
    TextView tvRegister;

    private Validator validator;
    private int i = 60;
    private String code;
    private AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpwd);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        validator = new Validator(this);
        validator.setValidationListener(new ValidationUtils.ValidationListener() {
            @Override
            public void onValidationSucceeded() {
                super.onValidationSucceeded();
                if (!etCode.getEditableText().toString().trim().equals(code)) {
                    showToast("验证码不正确");
                    return;
                }
                findPassword();
            }
        });
    }

    private void findPassword() {
        showLoad("加载中...");
        RequestParams params = new RequestParams();
        params.put("username", etTel.getEditableText().toString().trim());
        params.put("password", etConfirm.getEditableText().toString().trim());
        Log.i("tag", Constants.BASE_URL + "updatePassword");
        client.post(Constants.BASE_URL + "updatePassword", params,
                new AsyncHttpResponseHandler() {

                    @Override
                    public void onFailure(Throwable arg0, String arg1) {
                        super.onFailure(arg0, arg1);
                    }

                    @Override
                    public void onSuccess(int arg0, String s) {
                        dismiss();
                        String resultStr = Utils
                                .getStrVal(new String(s));
                        JsonBean json = JsonUtil.getJsonBean(resultStr);
                        int rescode = json.getRescode();
                        if (0 == rescode) {
                            showToast("修改成功");
                            ActivityManager.getInstance().finishCurrentActivity();
                        } else {
                            showToast((String) json.getRescnt());
                        }
                    }
                });
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
                if (!StringUtils.isMobileNumber(etTel.getEditableText().toString().trim())) {
                    showToast("请输入正确的手机号码");
                } else {
                    isPhoneIsRegister();
                }
                break;
            case R.id.tv_ok:
                validator.validate();
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

    private void isPhoneIsRegister() {
        showLoad("加载中...");
        RequestParams params = new RequestParams();
        params.put("mobile", etTel.getEditableText().toString().trim());
        Log.i("tag", Constants.BASE_URL + "checkmobile&" + params);
        client.post(Constants.BASE_URL + "checkmobile", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, String s) {
                super.onSuccess(i, s);
                dismiss();
                String resultStr = Utils.getStrVal(new String(s));
                if (null != resultStr) {
                    JsonBean bean = JsonUtil.getJsonBean(resultStr);
                    if (null != bean) {
                        int rescode = bean.getRescode();
                        if (rescode != 0) {
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
                            getCode(etTel.getText().toString());
                        } else {
                            showToast("该手机号没有注册");
                        }
                    }
                }
            }
        });
    }

    private void getCode(String string) {
        showLoad("正在发送短信...");
        RequestParams params = new RequestParams();
        params.put("mobile", string);
        client.post(Constants.BASE_URL + "sendVerificationCode", params,
                new AsyncHttpResponseHandler() {

                    @Override
                    public void onFailure(Throwable arg0, String arg1) {
                        super.onFailure(arg0, arg1);
                    }

                    @Override
                    public void onSuccess(int arg0, String s) {
                        dismiss();
                        Gson gson = new Gson();
                        if (s.contains("rescode")
                                && s.contains("rescnt")) {
                            ValidateCodeBean bean = gson.fromJson(s, ValidateCodeBean.class);
                            int state = bean.getRescode();
                            if (0 == state) {
                                showToast(bean.getCode());
                                code = bean.getCode();
                            } else {
                                showToast((String) bean.getRescnt());
                            }
                        }
                    }
                });
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityManager.getInstance().finishCurrentActivity();
        overridePendingTransition(0, R.anim.login_close);
    }
}
