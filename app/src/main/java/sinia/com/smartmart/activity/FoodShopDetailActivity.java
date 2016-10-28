package sinia.com.smartmart.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hedgehog.ratingbar.RatingBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.smartmart.R;
import sinia.com.smartmart.adapter.MyFragmentPagerAdapter;
import sinia.com.smartmart.base.BaseActivity;
import sinia.com.smartmart.fragment.FoodMenuFragment;
import sinia.com.smartmart.fragment.LoginFragment;
import sinia.com.smartmart.fragment.RegisterFragment;
import sinia.com.smartmart.utils.ActivityManager;
import sinia.com.smartmart.utils.AppInfoUtil;
import sinia.com.smartmart.utils.SystemBarTintManager;

import static sinia.com.smartmart.R.id.tv_cancle;
import static sinia.com.smartmart.R.id.tv_content;
import static sinia.com.smartmart.R.id.tv_ok;
import static sinia.com.smartmart.R.id.tv_title;

/**
 * Created by 忧郁的眼神 on 2016/10/26 0026.
 */

public class FoodShopDetailActivity extends BaseActivity {

    @Bind(R.id.back)
    TextView back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.img_call)
    ImageView imgCall;
    @Bind(R.id.img_collect)
    ImageView imgCollect;
    @Bind(R.id.img_food)
    ImageView imgShop;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.img_quan)
    ImageView imgQuan;
    @Bind(R.id.ratingBar)
    RatingBar ratingBar;
    @Bind(R.id.tv_sall)
    TextView tvSall;
    @Bind(R.id.ll_sall)
    LinearLayout llSall;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_distance)
    TextView tvDistance;
    @Bind(R.id.ll_time)
    LinearLayout llTime;
    @Bind(R.id.rl_shop_detail)
    RelativeLayout rlShopDetail;
    @Bind(R.id.t)
    TextView t;
    @Bind(R.id.tv_use_condition)
    TextView tvUseCondition;
    @Bind(R.id.tv_coupons_money)
    TextView tvCouponsMoney;
    @Bind(R.id.tv_use_time)
    TextView tvUseTime;
    @Bind(R.id.rl_get_coupons)
    RelativeLayout rlGetCoupons;
    @Bind(R.id.tab_title)
    TabLayout tabTitle;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    private SystemBarTintManager mTintManager;

    private MyFragmentPagerAdapter pagerAdapter;
    private List<String> titleList;
    private List<Fragment> fragmentList;
    private FoodMenuFragment menuFragment;
    private MerchantDetailFragment merchantFragment;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideHeadView();
        setContentView(R.layout.activity_food_shop_detail);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        mTintManager = new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setNavigationBarTintEnabled(true);
        mTintManager.setTintColor(getResources().getColor(R.color.themeColor));
        initViews();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initViews() {
        titleList = new ArrayList<>();
        titleList.add("菜单");
        titleList.add("商家信息");
        fragmentList = new ArrayList<>();
        menuFragment = new FoodMenuFragment();
        merchantFragment = new MerchantDetailFragment();
        fragmentList.add(menuFragment);
        fragmentList.add(merchantFragment);
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewPager.setAdapter(pagerAdapter);
        tabTitle.setTabMode(TabLayout.MODE_FIXED);
        tabTitle.addTab(tabTitle.newTab().setText(titleList.get(0)));
        tabTitle.addTab(tabTitle.newTab().setText(titleList.get(1)));
        tabTitle.setupWithViewPager(viewPager);
    }

    @OnClick({R.id.back, R.id.img_call, R.id.img_collect, R.id.rl_shop_detail, R.id.rl_get_coupons})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                ActivityManager.getInstance().finishCurrentActivity();
                break;
            case R.id.img_call:
                break;
            case R.id.img_collect:
                break;
            case R.id.rl_shop_detail:
                createShopIntroduceDialog();
                break;
            case R.id.rl_get_coupons:
                break;
        }
    }

    private Dialog createShopIntroduceDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.view_food_shop_intro, null);
        dialog = new Dialog(this, R.style.DialogScaleStyle);
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (display.getWidth() - AppInfoUtil.dip2px(this, 40)); // 设置宽度
        dialog.getWindow().setAttributes(lp);
        dialog.setContentView(v, lp);

        TextView tv_close = (TextView) v.findViewById(R.id.tv_close);
        TextView tv_name = (TextView) v.findViewById(R.id.tv_name);
        TextView tv_startMoney = (TextView) v.findViewById(R.id.tv_startMoney);
        TextView tv_sendMoney = (TextView) v.findViewById(R.id.tv_sendMoney);
        TextView tv_sendTime = (TextView) v.findViewById(R.id.tv_sendTime);
        TextView tv_activity_content = (TextView) v.findViewById(R.id.tv_activity_content);
        TextView tv_notice_content = (TextView) v.findViewById(R.id.tv_notice_content);
        RatingBar ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);

        ratingBar.setStar(4);
        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        return dialog;
    }


    private void setTranslucentStatus(boolean on) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
    }
}
