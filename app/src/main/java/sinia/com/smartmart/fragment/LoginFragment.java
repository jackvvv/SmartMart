package sinia.com.smartmart.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.smartmart.R;
import sinia.com.smartmart.activity.FindPasswordActivity;
import sinia.com.smartmart.activity.LoginRegisterActivity;
import sinia.com.smartmart.base.BaseFragment;

/**
 * Created by 忧郁的眼神 on 2016/9/3.
 */
public class LoginFragment extends BaseFragment {

    @Bind(R.id.et_tel)
    EditText etTel;
    @Bind(R.id.et_pwd)
    EditText etPwd;
    @Bind(R.id.tv_login)
    TextView tvLogin;
    @Bind(R.id.tv_forgetPwd)
    TextView tvForgetPwd;
    private View rootView;

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

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_login, R.id.tv_forgetPwd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                break;
            case R.id.tv_forgetPwd:
                Intent intent = new Intent(getActivity(), FindPasswordActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.login_open, 0);
                break;
        }
    }
}
