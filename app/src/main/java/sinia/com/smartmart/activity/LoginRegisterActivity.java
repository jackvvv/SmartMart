package sinia.com.smartmart.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.smartmart.R;
import sinia.com.smartmart.adapter.MyFragmentPagerAdapter;
import sinia.com.smartmart.base.BaseActivity;
import sinia.com.smartmart.fragment.LoginFragment;
import sinia.com.smartmart.fragment.RegisterFragment;
import sinia.com.smartmart.utils.ActivityManager;
import sinia.com.smartmart.utils.AppInfoUtil;

/**
 * Created by 忧郁的眼神 on 2016/9/3.
 */
public class LoginRegisterActivity extends BaseActivity {

    @Bind(R.id.tab_title)
    TabLayout tabTitle;
    @Bind(R.id.rl)
    RelativeLayout rl;
    @Bind(R.id.imgClose)
    ImageView imgClose;
    @Bind(R.id.viewPager)
    ViewPager viewPager;

    private MyFragmentPagerAdapter pagerAdapter;
    private List<String> titleList;
    private List<Fragment> fragmentList;
    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;
    private String from_findpwd = "0";//1.切换登录，2.切换注册

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        ButterKnife.bind(this);
        initViews();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initViews() {
        from_findpwd = getIntent().getStringExtra("from_findpwd");
        titleList = new ArrayList<>();
        titleList.add("登录");
        titleList.add("注册");
        fragmentList = new ArrayList<>();
        loginFragment = new LoginFragment();
        registerFragment = new RegisterFragment();
        fragmentList.add(loginFragment);
        fragmentList.add(registerFragment);
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewPager.setAdapter(pagerAdapter);
        tabTitle.setTabMode(TabLayout.MODE_FIXED);
        tabTitle.addTab(tabTitle.newTab().setText(titleList.get(0)));
        tabTitle.addTab(tabTitle.newTab().setText(titleList.get(1)));
        tabTitle.setupWithViewPager(viewPager);

        if ("1".equals(from_findpwd)) {
            viewPager.setCurrentItem(0);
        } else if ("2".equals(from_findpwd)) {
            viewPager.setCurrentItem(1);
        }


        Class<?> tablayout = tabTitle.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tablayout.getDeclaredField("mTabStrip");
            tabStrip.setAccessible(true);
            LinearLayout ll_tab = (LinearLayout) tabStrip.get(tabTitle);
            for (int i = 0; i < ll_tab.getChildCount(); i++) {
                View child = ll_tab.getChildAt(i);
                child.setPadding(0, 0, 0, 0);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams
                        .MATCH_PARENT, 1);
                params.setMarginStart(AppInfoUtil.dip2px(this, 70f));
                params.setMarginEnd(AppInfoUtil.dip2px(this, 70f));
                child.setLayoutParams(params);
                child.invalidate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.height = AppInfoUtil.getScreenHeight(this) * 1 / 3;
        rl.setLayoutParams(lp);
    }

    @OnClick(R.id.imgClose)
    public void onClick() {
        ActivityManager.getInstance().finishCurrentActivity();
        overridePendingTransition(0, R.anim.login_close);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityManager.getInstance().finishCurrentActivity();
        overridePendingTransition(0, R.anim.login_close);
    }
}
