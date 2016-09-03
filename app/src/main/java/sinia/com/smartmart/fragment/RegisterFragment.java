package sinia.com.smartmart.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.smartmart.R;
import sinia.com.smartmart.base.BaseFragment;

/**
 * Created by 忧郁的眼神 on 2016/9/3.
 */
public class RegisterFragment extends BaseFragment {

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
    @Bind(R.id.tv_register)
    TextView tvRegister;
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_register, null);
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

    @OnClick({R.id.tv_getCode, R.id.tv_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_getCode:
                break;
            case R.id.tv_register:
                break;
        }
    }
}
