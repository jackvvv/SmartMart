package sinia.com.smartmart.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.materialdialog.MaterialDialog;
import sinia.com.smartmart.R;
import sinia.com.smartmart.adapter.MainFragmentAdapter;
import sinia.com.smartmart.base.BaseActivity;
import sinia.com.smartmart.base.BaseFragment;
import sinia.com.smartmart.fragment.FoodMenuFragment;
import sinia.com.smartmart.fragment.MerchantDetailFragment;
import sinia.com.smartmart.utils.ActivityManager;
import sinia.com.smartmart.utils.AppInfoUtil;
import sinia.com.smartmart.utils.SystemBarTintManager;

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
    //    @Bind(R.id.tab_title)
//    TabLayout tabTitle;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.tab_menu)
    TextView tabMenu;
    @Bind(R.id.line_menu)
    ImageView lineMenu;
    @Bind(R.id.tab_shop)
    TextView tabShop;
    @Bind(R.id.line_shop)
    ImageView lineShop;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.coordinator)
    CoordinatorLayout coordinator;
    private SystemBarTintManager mTintManager;

    private ArrayList<BaseFragment> fragmentList;
    private FoodMenuFragment menuFragment;
    private MerchantDetailFragment merchantFragment;
    private Dialog dialog;
    private MaterialDialog materialDialog;

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
        fragmentList = new ArrayList<>();
        menuFragment = new FoodMenuFragment();
        merchantFragment = new MerchantDetailFragment();
        fragmentList.add(menuFragment);
        fragmentList.add(merchantFragment);
        MainFragmentAdapter adapter = new MainFragmentAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        lineMenu.setVisibility(View.VISIBLE);
                        lineShop.setVisibility(View.INVISIBLE);
                        tabMenu.setSelected(true);
                        tabShop.setSelected(false);
                        break;
                    case 1:
                        lineMenu.setVisibility(View.INVISIBLE);
                        lineShop.setVisibility(View.VISIBLE);
                        tabMenu.setSelected(false);
                        tabShop.setSelected(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @OnClick({R.id.back, R.id.img_call, R.id.img_collect, R.id.rl_shop_detail, R.id.rl_get_coupons, R.id.tab_menu, R
            .id.tab_shop})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                ActivityManager.getInstance().finishCurrentActivity();
                break;
            case R.id.img_call:
                callService();
                break;
            case R.id.img_collect:
                break;
            case R.id.rl_shop_detail:
                createShopIntroduceDialog();
                break;
            case R.id.rl_get_coupons:
                break;
            case R.id.tab_menu:
                lineMenu.setVisibility(View.VISIBLE);
                lineShop.setVisibility(View.INVISIBLE);
                tabMenu.setSelected(true);
                tabShop.setSelected(false);
                viewPager.setCurrentItem(0);
                break;
            case R.id.tab_shop:
                lineMenu.setVisibility(View.INVISIBLE);
                lineShop.setVisibility(View.VISIBLE);
                tabMenu.setSelected(false);
                tabShop.setSelected(true);
                viewPager.setCurrentItem(1);
                break;
        }
    }

    private void callService() {
        materialDialog = new MaterialDialog(this);
        materialDialog.setTitle("联系商家");
        materialDialog.setMessage("18276623009");
        materialDialog.setPositiveButton("立即拨打", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "18276623009"));
                if (ActivityCompat.checkSelfPermission(FoodShopDetailActivity.this, Manifest.permission.CALL_PHONE)
                        != PackageManager
                        .PERMISSION_GRANTED) {
                    return;
                }
                startActivity(intent);
                materialDialog.dismiss();
            }
        });
        materialDialog.setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDialog.dismiss();
            }
        });
        materialDialog.show();
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

    private void appBarScroll() {
        CoordinatorLayout.Behavior behavior = ((android.support.design.widget.CoordinatorLayout
                .LayoutParams) appbar.getLayoutParams()).getBehavior();
        behavior.onNestedScroll(coordinator, appbar, viewPager, 0, appbar.getHeight(), 0, 0);

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
