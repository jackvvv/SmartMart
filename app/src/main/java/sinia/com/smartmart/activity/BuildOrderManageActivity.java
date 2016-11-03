package sinia.com.smartmart.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import sinia.com.smartmart.R;
import sinia.com.smartmart.adapter.MyFragmentPagerAdapter;
import sinia.com.smartmart.base.BaseActivity;
import sinia.com.smartmart.base.BaseFragment;
import sinia.com.smartmart.fragment.BuildAllOrderFragment;
import sinia.com.smartmart.fragment.BuildCheckingOrderFragment;
import sinia.com.smartmart.fragment.BuildCommentOrderFragment;
import sinia.com.smartmart.fragment.BuildDeliveryingOrderFragment;

/**
 * Created by 忧郁的眼神 on 2016/11/3 0003.
 */

public class BuildOrderManageActivity extends BaseActivity {

    @Bind(R.id.tab_title)
    TabLayout tabTitle;
    @Bind(R.id.viewPager)
    ViewPager viewPager;

    private MyFragmentPagerAdapter pagerAdapter;
    private List<String> titleList;
    private ArrayList<Fragment> fragmentList;
    private BuildAllOrderFragment allFragment;
    private BuildCheckingOrderFragment checkFragment;
    private BuildDeliveryingOrderFragment deliveryFragment;
    private BuildCommentOrderFragment commentFragment;
    private String orderType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_order_manager, "订单管理");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
        initData();
    }

    private void initData() {
        orderType = getIntent().getStringExtra("orderType");
        fragmentList = new ArrayList<>();
        allFragment = new BuildAllOrderFragment();
        checkFragment = new BuildCheckingOrderFragment();
        deliveryFragment = new BuildDeliveryingOrderFragment();
        commentFragment = new BuildCommentOrderFragment();
        fragmentList.add(allFragment);
        fragmentList.add(checkFragment);
        fragmentList.add(deliveryFragment);
        fragmentList.add(commentFragment);

        titleList = new ArrayList<>();
        titleList.add("全部订单");
        titleList.add("待审核");
        titleList.add("待收货");
        titleList.add("评价");
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewPager.setAdapter(pagerAdapter);
        tabTitle.setTabMode(TabLayout.MODE_FIXED);
        tabTitle.addTab(tabTitle.newTab().setText(titleList.get(0)));
        tabTitle.addTab(tabTitle.newTab().setText(titleList.get(1)));
        tabTitle.setupWithViewPager(viewPager);
        tabTitle.getTabAt(0).setIcon(R.drawable.all_order_img_selector);
        tabTitle.getTabAt(1).setIcon(R.drawable.wait_order_img_selector);
        tabTitle.getTabAt(2).setIcon(R.drawable.send_order_img_selector);
        tabTitle.getTabAt(3).setIcon(R.drawable.comment_order_img_selector);

        if ("1".equals(orderType)) {
            viewPager.setCurrentItem(0);
        } else if ("2".equals(orderType)) {
            viewPager.setCurrentItem(1);
        } else if ("3".equals(orderType)) {
            viewPager.setCurrentItem(2);
        } else {
            viewPager.setCurrentItem(3);
        }
    }
}
