package sinia.com.smartmart.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.smartmart.R;
import sinia.com.smartmart.activity.LoginRegisterActivity;
import sinia.com.smartmart.activity.MainActivity;
import sinia.com.smartmart.activity.MessageWarnActivity;
import sinia.com.smartmart.activity.MyAccountActivity;
import sinia.com.smartmart.activity.MyCollectActivity;
import sinia.com.smartmart.activity.MyCouponsActivity;
import sinia.com.smartmart.activity.PersonalInfoActivity;
import sinia.com.smartmart.activity.SettingsActivity;
import sinia.com.smartmart.base.BaseFragment;
import sinia.com.smartmart.bean.JsonBean;
import sinia.com.smartmart.bean.UserBean;
import sinia.com.smartmart.bean.UserInfo;
import sinia.com.smartmart.bean.UserNoticeBean;
import sinia.com.smartmart.utils.ActivityManager;
import sinia.com.smartmart.utils.BitmapUtilsHelp;
import sinia.com.smartmart.utils.Constants;
import sinia.com.smartmart.utils.DialogUtils;
import sinia.com.smartmart.utils.JsonUtil;
import sinia.com.smartmart.utils.MyApplication;
import sinia.com.smartmart.utils.Utils;
import sinia.com.smartmart.view.CircleImageView;

/**
 * Created by 忧郁的眼神 on 2016/9/3.
 */
public class MineFragment extends BaseFragment {

    @Bind(R.id.img_new)
    ImageView imgNew;
    @Bind(R.id.rl_msg)
    RelativeLayout rlMsg;
    @Bind(R.id.iv_head)
    CircleImageView ivHead;
    @Bind(R.id.tv_username)
    TextView tvUsername;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.img_into)
    ImageView imgInto;
    @Bind(R.id.img_settings)
    ImageView img_settings;
    @Bind(R.id.rl_person_info)
    RelativeLayout rlPersonInfo;
    @Bind(R.id.ll_myaccount)
    LinearLayout llMyaccount;
    @Bind(R.id.ll_collect)
    LinearLayout llCollect;
    @Bind(R.id.ll_coupon)
    LinearLayout llCoupon;
    @Bind(R.id.ll_mysay)
    LinearLayout llMysay;
    @Bind(R.id.ll_neighbour)
    LinearLayout llNeighbour;
    @Bind(R.id.ll_eye)
    LinearLayout llEye;
    @Bind(R.id.ll_login)
    LinearLayout ll_login;
    @Bind(R.id.ll_notlogin)
    LinearLayout ll_notlogin;
    private View rootView;
    private UserInfo user;
    private AsyncHttpClient client = new AsyncHttpClient();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_mine, null);
        ButterKnife.bind(this, rootView);
        initData();
        return rootView;
    }

    private void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (MyApplication.getInstance().getBoolValue("is_login")) {
            setUserData();
            ll_login.setVisibility(View.VISIBLE);
            ll_notlogin.setVisibility(View.GONE);
        } else {
            ll_notlogin.setVisibility(View.VISIBLE);
            ll_login.setVisibility(View.GONE);
            BitmapUtilsHelp.getImage(getActivity(), R.drawable
                    .head_default).display(ivHead, "");
        }
    }

    private void setUserData() {
        user = MyApplication.getInstance().getUserInfo();
        RequestParams params = new RequestParams();
        params.put("memberid", user.getMemberid());
        Log.i("tag", Constants.BASE_URL + "refreshData");
        client.post(Constants.BASE_URL + "refreshData", params,
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
                            UserInfo info = bean.getRescnt();
                            MyApplication.getInstance().setUserInfo(info);
                            saveUserData(bean);
                            tvUsername.setText(info.getUsername());
                            tvAddress.setText(info.getAddress());
                            BitmapUtilsHelp.getImage(getActivity(), R.drawable
                                    .head_default).display(ivHead, info.getIcon());
                        } else {
                            showToast((String) json.getRescnt());
                        }
                    }
                });
    }

    @OnClick({R.id.rl_msg, R.id.rl_person_info, R.id.ll_myaccount, R.id.ll_collect, R.id.ll_coupon, R.id.ll_mysay, R
            .id.ll_neighbour, R.id.ll_eye, R.id.img_settings, R.id.ll_notlogin})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.rl_msg:
                if (MyApplication.getInstance().getBoolValue("is_login")) {
                    intent = new Intent(getActivity(), MessageWarnActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginRegisterActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.login_open, 0);
                }
                break;
            case R.id.rl_person_info:
                if (MyApplication.getInstance().getBoolValue("is_login")) {
                    intent = new Intent(getActivity(), PersonalInfoActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginRegisterActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.login_open, 0);
                }
                break;
            case R.id.ll_myaccount:
                if (MyApplication.getInstance().getBoolValue("is_login")) {
                    intent = new Intent(getActivity(), MyAccountActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginRegisterActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.login_open, 0);
                }
                break;
            case R.id.ll_collect:
                intent = new Intent(getActivity(), MyCollectActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_coupon:
                intent = new Intent(getActivity(), MyCouponsActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_mysay:
                if (MyApplication.getInstance().getBoolValue("is_login")) {
                    DialogUtils.createFountionDevelopingTipsDialog(getActivity(), "说说功能正在完善中...");
                } else {
                    intent = new Intent(getActivity(), LoginRegisterActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.login_open, 0);
                }
                break;
            case R.id.ll_neighbour:
                DialogUtils.createFountionDevelopingTipsDialog(getActivity(), "邻里功能正在完善中...");
                break;
            case R.id.ll_eye:
                DialogUtils.createFountionDevelopingTipsDialog(getActivity(), "监控功能正在完善中...");
                break;
            case R.id.img_settings:
                if (MyApplication.getInstance().getBoolValue("is_login")) {
                    intent = new Intent(getActivity(), SettingsActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginRegisterActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.login_open, 0);
                }
                break;
            case R.id.ll_notlogin:
                intent = new Intent(getActivity(), LoginRegisterActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.login_open, 0);
                break;
        }
    }

    private void saveUserData(UserBean bean) {
        UserNoticeBean unb = new UserNoticeBean();
        unb.setNoticedetail(bean.getNoticedetail());
        unb.setRatenum(bean.getRatenum());
        MyApplication.getInstance().setUserNoticeBean(unb);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
