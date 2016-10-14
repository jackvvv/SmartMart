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

import com.ToxicBakery.viewpager.transforms.ABaseTransformer;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.smartmart.R;
import sinia.com.smartmart.activity.AddWaterAccountActivity;
import sinia.com.smartmart.activity.FeedBackActivity;
import sinia.com.smartmart.activity.LoginRegisterActivity;
import sinia.com.smartmart.activity.MaintainListActivity;
import sinia.com.smartmart.activity.MessageWarnActivity;
import sinia.com.smartmart.activity.PayFeeActivity;
import sinia.com.smartmart.activity.PropertyFeeActivity;
import sinia.com.smartmart.activity.WaterAccountListActivity;
import sinia.com.smartmart.base.BaseFragment;
import sinia.com.smartmart.bean.FeeDetailBean;
import sinia.com.smartmart.bean.JsonBean;
import sinia.com.smartmart.bean.UserBean;
import sinia.com.smartmart.bean.UserInfo;
import sinia.com.smartmart.bean.UserNoticeBean;
import sinia.com.smartmart.utils.AppInfoUtil;
import sinia.com.smartmart.utils.BitmapUtilsHelp;
import sinia.com.smartmart.utils.Constants;
import sinia.com.smartmart.utils.JsonUtil;
import sinia.com.smartmart.utils.MyApplication;
import sinia.com.smartmart.utils.Utils;
import sinia.com.smartmart.view.LocalImageHolderView;

/**
 * Created by 忧郁的眼神 on 2016/9/3.
 */
public class PropertyFragment extends BaseFragment {

    @Bind(R.id.img_new)
    ImageView imgNew;
    @Bind(R.id.rl_msg)
    RelativeLayout rlMsg;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.tv_notice)
    TextView tvNotice;
    @Bind(R.id.ll_water)
    LinearLayout llWater;
    @Bind(R.id.ll_elec)
    LinearLayout llElec;
    @Bind(R.id.ll_gas)
    LinearLayout llGas;
    @Bind(R.id.ll_property)
    LinearLayout llProperty;
    @Bind(R.id.ll_maintain)
    LinearLayout llMaintain;
    @Bind(R.id.ll_feedback)
    LinearLayout llFeedback;
    @Bind(R.id.convenientBanner)
    ConvenientBanner convenientBanner;
    private View rootView;
    private ArrayList<Integer> localImages = new ArrayList<Integer>();
    private UserInfo userInfo;
    private UserNoticeBean userNoticeBean;
    private AsyncHttpClient client = new AsyncHttpClient();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_property, null);
        ButterKnife.bind(this, rootView);
        initData();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MyApplication.getInstance().getBoolValue("is_login")) {
            userInfo = MyApplication.getInstance().getUserInfo();
            userNoticeBean = MyApplication.getInstance().getUserNoticeBean();
            if (null != userInfo && null != userNoticeBean) {
                tvAddress.setText(userInfo.getAddress());
                tvNotice.setText(userNoticeBean.getNoticedetail());
            }
        } else {
            tvAddress.setText("未登录");
        }
    }

    private void initData() {
        localImages.add(AppInfoUtil.getResId("img_det", R.drawable.class));
        localImages.add(AppInfoUtil.getResId("xiaoguo", R.drawable.class));
        int h = AppInfoUtil.getScreenWidth(getActivity()) * 200 / 750;
        convenientBanner.getLayoutParams().height = h;
        String transforemerName = "StackTransformer";
        ABaseTransformer transforemer = null;
        try {
            Class cls = Class.forName("com.ToxicBakery.viewpager.transforms." + transforemerName);
            transforemer = (ABaseTransformer) cls.newInstance();
            convenientBanner.setPages(
                    new CBViewHolderCreator<LocalImageHolderView>() {
                        @Override
                        public LocalImageHolderView createHolder() {
                            return new LocalImageHolderView();
                        }
                    }, localImages).startTurning(3000).setPageIndicator(new int[]{R.drawable.carousel_point, R.drawable
                    .carousel_point_select})
                    .getViewPager().setPageTransformer(true, transforemer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.rl_msg, R.id.ll_water, R.id.ll_elec, R.id.ll_gas, R.id.ll_property, R.id.ll_maintain, R.id
            .ll_feedback})
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
            case R.id.ll_water:
                if (MyApplication.getInstance().getBoolValue("is_login")) {
                    checkFeeStatus("1", "水费缴纳");
                } else {
                    intent = new Intent(getActivity(), LoginRegisterActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.login_open, 0);
                }
//                intent = new Intent(getActivity(), WaterAccountListActivity.class);
//                startActivity(intent);
                break;
            case R.id.ll_elec:
                if (MyApplication.getInstance().getBoolValue("is_login")) {
                    checkFeeStatus("2", "电费缴纳");
                } else {
                    intent = new Intent(getActivity(), LoginRegisterActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.login_open, 0);
                }
                break;
            case R.id.ll_gas:
                if (MyApplication.getInstance().getBoolValue("is_login")) {
                    checkFeeStatus("3", "煤气费缴纳");
                } else {
                    intent = new Intent(getActivity(), LoginRegisterActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.login_open, 0);
                }
                break;
            case R.id.ll_property:
                if (MyApplication.getInstance().getBoolValue("is_login")) {
                    intent = new Intent(getActivity(), PropertyFeeActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginRegisterActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.login_open, 0);
                }
                break;
            case R.id.ll_maintain:
                if (MyApplication.getInstance().getBoolValue("is_login")) {
                    intent = new Intent(getActivity(), MaintainListActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginRegisterActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.login_open, 0);
                }
                break;
            case R.id.ll_feedback:
                if (MyApplication.getInstance().getBoolValue("is_login")) {
                    intent = new Intent(getActivity(), FeedBackActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginRegisterActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.login_open, 0);
                }
                break;
        }
    }

    private void checkFeeStatus(final String ratetype, final String title) {
        RequestParams params = new RequestParams();
        params.put("memberid", MyApplication.getInstance().getUserInfo().getMemberid());
        params.put("ratetype", ratetype);
        Log.i("tag", Constants.BASE_URL + "checkmemberrate" + params);
        client.post(Constants.BASE_URL + "checkmemberrate", params,
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
                            //0 已完善对应信息，跳转欠费详情
                            FeeDetailBean detailBean = gson.fromJson(resultStr, FeeDetailBean.class);
                            Intent intent = new Intent(getActivity(), PayFeeActivity.class);
                            intent.putExtra("feeBean", detailBean);
                            intent.putExtra("fee_type", ratetype);
                            intent.putExtra("isFromProperty", "1");
                            startActivity(intent);
                        } else if (1 == rescode) {
                            //未完善，跳转完善页面
                            Intent intent = new Intent(getActivity(), AddWaterAccountActivity.class);
                            intent.putExtra("fee_type", ratetype);
                            intent.putExtra("title", title);
                            startActivity(intent);
                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
