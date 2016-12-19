package sinia.com.smartmart.activity;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.labo.kaji.relativepopupwindow.RelativePopupWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.smartmart.R;
import sinia.com.smartmart.adapter.BuildGoodsBrandAdapter;
import sinia.com.smartmart.adapter.BuildGoodsBrandTopAdapter;
import sinia.com.smartmart.adapter.BuildGoodsColorAdapter;
import sinia.com.smartmart.adapter.BuildGoodsNormAdapter;
import sinia.com.smartmart.adapter.BuildMarketGoodsAdapter;
import sinia.com.smartmart.base.BaseActivity;
import sinia.com.smartmart.bean.BuildBrandModel;
import sinia.com.smartmart.mycallback.BuildMarketListAddCartCallBack;
import sinia.com.smartmart.utils.ActivityManager;
import sinia.com.smartmart.view.HorizontalListView;
import sinia.com.smartmart.view.MyGridView;
import sinia.com.smartmart.view.MyPopupWindow;

import static sinia.com.smartmart.R.id.grid_brand;
import static sinia.com.smartmart.R.id.top;

/**
 * Created by 忧郁的眼神 on 2016/11/10 0010.
 */

public class BuildMarketListActivity extends BaseActivity implements BuildMarketListAddCartCallBack {

    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.tv_goods_count)
    TextView tvGoodsCount;
    @Bind(R.id.hlv_type)
    HorizontalListView hlvType;
    @Bind(R.id.img_expand)
    ImageView imgExpand;
    @Bind(R.id.v1)
    View v1;
    @Bind(R.id.view_bg)
    View view_bg;
    @Bind(R.id.view_anchor)
    View view_anchor;
    @Bind(R.id.gv_goods)
    GridView gvGoods;
    private BuildGoodsBrandTopAdapter topAdapter;
    private BuildGoodsBrandAdapter gridBrandAdapter;
    private BuildMarketGoodsAdapter goodsAdapter;
    private List<BuildBrandModel> brandList = new ArrayList<>();
    private PopupWindow popWindow;
    private boolean isExpand = false;//是否展开
    private boolean isCanExpand = false;//是否可以展开
    private String title;
    private GridView grid_brand;
    private int selection = 0;//topAdapter，gridBrandAdapter 选中的品牌
    private BuildGoodsNormAdapter normAdapter;
    private BuildGoodsColorAdapter colorAdapter;
    private PopupWindow normPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getIntent().getStringExtra("title");
        setContentView(R.layout.activity_build_market_list);
        ButterKnife.bind(this);
        tv_title.setText(title);
        initView();
    }

    private void initView() {
        for (int i = 0; i < 10; i++) {
            BuildBrandModel model = new BuildBrandModel();
            model.setBrand("绿野" + i);
            brandList.add(model);
        }
        if (brandList.size() <= 4) {
            //不能下拉显示更多
            isCanExpand = false;
            topAdapter = new BuildGoodsBrandTopAdapter(this, brandList);
            topAdapter.selectPosition = selection;
            hlvType.setAdapter(topAdapter);
        } else {
            //hlv 显示四条
            isCanExpand = true;
            topAdapter = new BuildGoodsBrandTopAdapter(this, brandList.subList(0, 4));
            topAdapter.selectPosition = selection;
            hlvType.setAdapter(topAdapter);
            gridBrandAdapter = new BuildGoodsBrandAdapter(this, brandList);
        }
        goodsAdapter = new BuildMarketGoodsAdapter(this);
        goodsAdapter.setBuildMarketListAddCartCallBack(this);
        gvGoods.setAdapter(goodsAdapter);
        hlvType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selection = position;
                topAdapter.selectPosition = position;
                topAdapter.notifyDataSetChanged();
            }
        });
    }

    @OnClick({R.id.back, R.id.img_cart, R.id.rl_expand})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                ActivityManager.getInstance().finishCurrentActivity();
                break;
            case R.id.img_cart:
                startActivityForNoIntent(BuildCartActivity.class);
                break;
            case R.id.rl_expand:
                if (isCanExpand) {
                    if (isExpand) {
                        isExpand = false;
                        RotateAnimation animation = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f,
                                Animation.RELATIVE_TO_SELF, 0.5f);
                        animation.setDuration(350);
                        animation.setFillAfter(true);
                        imgExpand.startAnimation(animation);
                        if (null != popWindow && popWindow.isShowing()) {
                            popWindow.dismiss();
                        }
                    } else {
                        isExpand = true;
                        RotateAnimation animation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f,
                                Animation.RELATIVE_TO_SELF, 0.5f);
                        animation.setDuration(350);
                        animation.setFillAfter(true);
                        imgExpand.startAnimation(animation);
                        showtBrandGrid(selection);
                    }
                }
                break;
        }
    }

    private void showtBrandGrid(int select) {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context
                .LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.view_build_brand_girdview,
                null);
        popWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(true);
        popWindow.setAnimationStyle(R.style.ActionSheetDialogAnimation2);
        view_bg.setVisibility(View.VISIBLE);
        popWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popWindow.showAsDropDown(v1, 0, 0);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1f;
        getWindow().setAttributes(lp);
        view_bg.setVisibility(View.VISIBLE);
        view_bg.setAlpha(0.5f);
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                view_bg.setVisibility(View.GONE);
                getWindow().setAttributes(lp);
                RotateAnimation animation = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                animation.setDuration(350);
                animation.setFillAfter(true);
                imgExpand.startAnimation(animation);
                isExpand = false;
            }
        });
        grid_brand = (GridView) view.findViewById(R.id.grid_brand);
        gridBrandAdapter.selectPosition = select;
        grid_brand.setAdapter(gridBrandAdapter);
        grid_brand.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                gridBrandAdapter.selectPosition = position;
                gridBrandAdapter.notifyDataSetChanged();
                //横向listview最后一个item更新
                if (position >= 4) {
                    selection = position;
                    hlvType.setSelection(3);
                    topAdapter.selectPosition = 3;
                    topAdapter.notifyDataSetChanged();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            View view1 = hlvType.getChildAt(3);
                            TextView textView = (TextView) view1.findViewById(R.id.tv_name);
                            textView.setText(brandList.get(position).getBrand());
                        }
                    }, 5);
                } else {
                    hlvType.setSelection(position);
                    topAdapter.selectPosition = position;
                    topAdapter.notifyDataSetChanged();
                }
                popWindow.dismiss();
            }
        });
    }

    @Override
    public void addToCart(int position) {
        showNormWindow();
    }

    private void showNormWindow() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popw_build_goods_norm, null);
        ImageView img = (ImageView) view.findViewById(R.id.img);
        ImageView img_colse = (ImageView) view.findViewById(R.id.img_colse);
        TextView tv_num = (TextView) view.findViewById(R.id.tv_num);
        TextView tv_goods_name = (TextView) view.findViewById(R.id.tv_goods_name);
        TextView tv_storeNum = (TextView) view.findViewById(R.id.tv_storeNum);
        TextView tv_singlePrice = (TextView) view.findViewById(R.id.tv_singlePrice);
        TextView tv_money = (TextView) view.findViewById(R.id.tv_money);
        TextView tv_cart = (TextView) view.findViewById(R.id.tv_cart);
        MyGridView gv_norm = (MyGridView) view.findViewById(R.id.gv_norm);
        MyGridView gv_color = (MyGridView) view.findViewById(R.id.gv_color);
        RelativeLayout rl_jian = (RelativeLayout) view.findViewById(R.id.rl_jian);
        RelativeLayout rl_jia = (RelativeLayout) view.findViewById(R.id.rl_jia);

        normAdapter = new BuildGoodsNormAdapter(this);
        normAdapter.selectPosition = 0;
        gv_norm.setAdapter(normAdapter);
        gv_norm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                normAdapter.selectPosition = position;
                normAdapter.notifyDataSetChanged();
            }
        });

        colorAdapter = new BuildGoodsColorAdapter(this);
        colorAdapter.selectPosition = 0;
        gv_color.setAdapter(colorAdapter);
        gv_color.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                colorAdapter.selectPosition = position;
                colorAdapter.notifyDataSetChanged();
            }
        });

        normPopupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams
                .WRAP_CONTENT);
        normPopupWindow.setAnimationStyle(R.style.ActionSheetDialogAnimation);
        normPopupWindow.setFocusable(true);
        normPopupWindow.setOutsideTouchable(false);// 设置允许在外点击消失
        ColorDrawable dw = new ColorDrawable(0x50000000);
        normPopupWindow.setBackgroundDrawable(dw);
        normPopupWindow.showAtLocation(view_anchor, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        img_colse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                normPopupWindow.dismiss();
            }
        });
        rl_jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        rl_jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        tv_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForNoIntent(BuildCartActivity.class);
                normPopupWindow.dismiss();
            }
        });
    }
}
