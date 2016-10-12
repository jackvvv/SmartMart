package sinia.com.smartmart.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.smartmart.R;
import sinia.com.smartmart.activity.FindPasswordActivity;
import sinia.com.smartmart.activity.LoginRegisterActivity;
import sinia.com.smartmart.activity.MainActivity;
import sinia.com.smartmart.activity.PerfectInfoActivity;
import sinia.com.smartmart.base.BaseFragment;
import sinia.com.smartmart.bean.JsonBean;
import sinia.com.smartmart.bean.UserBean;
import sinia.com.smartmart.utils.ActivityManager;
import sinia.com.smartmart.utils.Constants;
import sinia.com.smartmart.utils.DialogUtils;
import sinia.com.smartmart.utils.JsonUtil;
import sinia.com.smartmart.utils.MyApplication;
import sinia.com.smartmart.utils.Utils;
import sinia.com.smartmart.utils.ValidationUtils;

import static android.R.attr.password;

/**
 * Created by 忧郁的眼神 on 2016/9/3.
 */
public class LoginFragment extends BaseFragment {
    @Pattern(regex = "^(13[0-9]|15[0-9]|17[0-9]|18[0-9]|14[0-9])[0-9]{8}$", message = "请输入正确的手机号码")
    @Order(1)
    @Bind(R.id.et_tel)
    EditText etTel;
    @NotEmpty(message = "请输入密码")
    @Order(2)
    @Bind(R.id.et_pwd)
    EditText etPwd;
    @Bind(R.id.tv_login)
    TextView tvLogin;
    @Bind(R.id.tv_forgetPwd)
    TextView tvForgetPwd;
    private View rootView;

    private Validator validator;
    private AsyncHttpClient client = new AsyncHttpClient();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_login, null);
        ButterKnife.bind(this, rootView);
        initData();
        return rootView;
    }

    private void initData() {
        validator = new Validator(this);
        validator.setValidationListener(new ValidationUtils.ValidationListener() {
            @Override
            public void onValidationSucceeded() {
                super.onValidationSucceeded();
                login();
            }
        });
    }

    private void login() {
        showLoad("登录中...");
        RequestParams params = new RequestParams();
        params.put("username", etTel.getEditableText().toString().trim());
        params.put("password", etPwd.getEditableText().toString().trim());
        Log.i("tag",Constants.BASE_URL + "login");
        client.post(Constants.BASE_URL + "login", params,
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
                        Gson gson = new Gson();
                        int rescode = json.getRescode();
                        if (0 == rescode) {
                            UserBean bean = gson.fromJson(resultStr,
                                    UserBean.class);
                            UserBean.UserInfo info = bean.getRescnt();
                            MyApplication.getInstance().setUserBean(info);
                            MyApplication.getInstance().setBooleanValue(
                                    "is_login", true);
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                            ActivityManager.getInstance().finishActivity(LoginRegisterActivity.class);
                        } else {
                            showToast((String) json.getRescnt());
                        }
                    }
                });
    }

    @OnClick({R.id.tv_login, R.id.tv_forgetPwd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                validator.validate();
                break;
            case R.id.tv_forgetPwd:
                Intent intent = new Intent(getActivity(), FindPasswordActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.login_open, 0);
                break;
        }
    }
}
