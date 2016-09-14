package sinia.com.smartmart.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.smartmart.R;
import sinia.com.smartmart.activity.MessageWarnActivity;
import sinia.com.smartmart.activity.PersonalInfoActivity;
import sinia.com.smartmart.activity.SettingsActivity;
import sinia.com.smartmart.base.BaseFragment;
import sinia.com.smartmart.utils.DialogUtils;
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
    private View rootView;

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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.rl_msg, R.id.rl_person_info, R.id.ll_myaccount, R.id.ll_collect, R.id.ll_coupon, R.id.ll_mysay, R
            .id.ll_neighbour, R.id.ll_eye, R.id.img_settings})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.rl_msg:
                intent = new Intent(getActivity(), MessageWarnActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_person_info:
                intent = new Intent(getActivity(), PersonalInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_myaccount:
                break;
            case R.id.ll_collect:
                break;
            case R.id.ll_coupon:
                break;
            case R.id.ll_mysay:
                DialogUtils.createFountionDevelopingTipsDialog(getActivity(), "说说功能正在完善中...");
                break;
            case R.id.ll_neighbour:
                DialogUtils.createFountionDevelopingTipsDialog(getActivity(), "邻里功能正在完善中...");
                break;
            case R.id.ll_eye:
                DialogUtils.createFountionDevelopingTipsDialog(getActivity(), "监控功能正在完善中...");
                break;
            case R.id.img_settings:
                intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
                break;
        }
    }
}
