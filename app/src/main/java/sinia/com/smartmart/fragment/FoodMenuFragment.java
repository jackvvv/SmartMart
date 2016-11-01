package sinia.com.smartmart.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.animation.SlideInLeftAnimation;
import com.labo.kaji.relativepopupwindow.RelativePopupWindow;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import sinia.com.smartmart.R;
import sinia.com.smartmart.activity.FoodDetailActivity;
import sinia.com.smartmart.adapter.FoodCartAdapter;
import sinia.com.smartmart.adapter.FoodMenuLeftAdapter;
import sinia.com.smartmart.adapter.FoodMenuRightAdapter;
import sinia.com.smartmart.adapter.FoodMenuRightAdapter2;
import sinia.com.smartmart.adapter.FoodMenuRightListAdapter;
import sinia.com.smartmart.base.BaseFragment;
import sinia.com.smartmart.bean.FoodModel;
import sinia.com.smartmart.mycallback.AppBarScrollListener;
import sinia.com.smartmart.mycallback.ModifyCountAndPriceInterface;
import sinia.com.smartmart.utils.Utility;
import sinia.com.smartmart.view.MyPopupWindow;
import sinia.com.smartmart.view.RecycleViewDivider;

import static sinia.com.smartmart.R.id.recyclerView;
import static sinia.com.smartmart.R.id.tv_buy_price;
import static sinia.com.smartmart.R.id.tv_select_price;

/**
 * Created by 忧郁的眼神 on 2016/10/26 0026.
 */

public class FoodMenuFragment extends BaseFragment implements ModifyCountAndPriceInterface, AppBarScrollListener {

    @Bind(R.id.lv_left)
    RecyclerView lvLeft;
    @Bind(R.id.tv_num)
    TextView tvNum;
    @Bind(R.id.lv_right)
    ListView lvRight;
    @Bind(R.id.img_cart)
    ImageView imgCart;
    @Bind(R.id.rl_cart)
    RelativeLayout rlCart;
    @Bind(R.id.rl_parent)
    RelativeLayout rl_parent;
    @Bind(tv_select_price)
    TextView tvSelectPrice;
    @Bind(R.id.tv_send_price)
    TextView tvSendPrice;
    @Bind(tv_buy_price)
    TextView tvBuyPrice;
    @Bind(R.id.tv_goods_count)
    TextView tv_goods_count;
    @Bind(R.id.rl_bottom)
    RelativeLayout rlBottom;
    @Bind(R.id.rl_btn_pay)
    RelativeLayout rl_btn_pay;
    @Bind(R.id.view_line)
    View view_line;
    @Bind(R.id.view_bg)
    View view_bg;
    private View rootView;
    private FoodMenuLeftAdapter leftAdapter;
    //        private FoodMenuRightAdapter rightAdapter;
//    private FoodMenuRightAdapter2 rightAdapter2;
    private FoodMenuRightListAdapter adapter;
    private List<FoodModel> foodList = new ArrayList<>();
    private List<FoodModel> cartFoodList = new ArrayList<>();//加入购物车中的
    // 贝塞尔曲线中间过程点坐标
    private float[] mCurrentPosition = new float[2];
    // 路径测量
    private PathMeasure mPathMeasure;
    private int goodsCount = 0;//购买的总数量
    private double totalPrice = 0.00;// 购买的商品总价
    private MyPopupWindow popWindow;
    private Dialog dialog;
    private FoodCartAdapter cartAdapter;
    private TextView tv_goods_count1, tv_select_price1, tv_buy_price1, tv_send_price1;
    private RelativeLayout rl_btn_pay1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_food_menu, null);
        ButterKnife.bind(this, rootView);
        virturlData();
        initData();
        isShowGoodsCountTv();
        return rootView;
    }

    private void virturlData() {
        foodList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            FoodModel fm = new FoodModel();
            fm.setCount(0);
            fm.setId(i + 1 + "");
            fm.setName("黄焖鸡微辣" + i + 1);
            fm.setPrice(15 + i);
            foodList.add(fm);
        }
    }

    private void isShowGoodsCountTv() {
        if (goodsCount == 0) {
            tv_goods_count.setVisibility(View.INVISIBLE);
        } else {
            tv_goods_count.setVisibility(View.VISIBLE);
            tv_goods_count.setText(String.valueOf(goodsCount));
        }
    }

    private void initData() {
        leftAdapter = new FoodMenuLeftAdapter(getActivity(), R.layout.item_food_menu_left, foodList);
        lvLeft.setLayoutManager(new LinearLayoutManager(getActivity()));
        lvLeft.setAdapter(leftAdapter);
        leftAdapter.selectPosition = 0;

//        rightAdapter = new FoodMenuRightAdapter(getActivity(), R.layout.item_food_menu_right, foodList);
//        rightAdapter2 = new FoodMenuRightAdapter2(getActivity(), foodList);
        adapter = new FoodMenuRightListAdapter(getActivity(), foodList);
        lvRight.setAdapter(adapter);
        Utility.setListViewHeightBasedOnChildren(lvRight);

        leftAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                leftAdapter.selectPosition = i;
                leftAdapter.notifyDataSetChanged();
            }
        });

        lvRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), FoodDetailActivity.class);
                startActivity(intent);
            }
        });
        adapter.setModifyCountAndPriceInterface(this);
        adapter.setAppBarScrollListener(this);
        adapter.setListener(new FoodMenuRightListAdapter.CallBackListener() {
            @Override
            public void callBackImage(ImageView image) {
                addToCart(image);
            }
        });
    }

    private void addToCart(ImageView goodsImg) {
        // 创造出执行动画的主题goodsImg（这个图片就是执行动画的图片,从开始位置出发,经过一个抛物线（贝塞尔曲线）,移动到购物车里）
        final ImageView goods = new ImageView(getActivity());
        goods.setImageDrawable(goodsImg.getDrawable());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(80, 80);
        rl_parent.addView(goods, params);
        // 得到父布局的起始点坐标（用于辅助计算动画开始/结束时的点的坐标）
        int[] parentLocation = new int[2];
        rl_parent.getLocationInWindow(parentLocation);
        // 得到商品图片的坐标（用于计算动画开始的坐标）
        int startLoc[] = new int[2];
        goodsImg.getLocationInWindow(startLoc);

        // 得到购物车图片的坐标(用于计算动画结束后的坐标)
        int endLoc[] = new int[2];
        imgCart.getLocationInWindow(endLoc);

        // 开始掉落的商品的起始点：商品起始点-父布局起始点+该商品图片的一半
        float startX = startLoc[0] - parentLocation[0] + goodsImg.getWidth() / 2;
        float startY = startLoc[1] - parentLocation[1] + goodsImg.getHeight() / 2;

        // 商品掉落后的终点坐标：购物车起始点-父布局起始点+购物车图片的1/5
        float toX = endLoc[0] - parentLocation[0] + imgCart.getWidth() / 5;
        float toY = endLoc[1] - parentLocation[1];

        // 开始绘制贝塞尔曲线
        Path path = new Path();
        // 移动到起始点（贝塞尔曲线的起点）
        path.moveTo(startX, startY);
        // 使用二阶贝塞尔曲线：注意第一个起始坐标越大，贝塞尔曲线的横向距离就会越大，一般按照下面的式子取即可
        path.quadTo((startX + toX) / 2, startY, toX, toY);
        // mPathMeasure用来计算贝塞尔曲线的曲线长度和贝塞尔曲线中间插值的坐标，如果是true，path会形成一个闭环
        mPathMeasure = new PathMeasure(path, false);

        // 属性动画实现（从0到贝塞尔曲线的长度之间进行插值计算，获取中间过程的距离值）
        android.animation.ValueAnimator valueAnimator = android.animation.ValueAnimator.ofFloat(0, mPathMeasure
                .getLength());
        valueAnimator.setDuration(500);

        // 匀速线性插值器
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new android.animation.ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(android.animation.ValueAnimator animation) {
                // 当插值计算进行时，获取中间的每个值，
                // 这里这个值是中间过程中的曲线长度（下面根据这个值来得出中间点的坐标值）
                float value = (Float) animation.getAnimatedValue();
                // 获取当前点坐标封装到mCurrentPosition
                // boolean getPosTan(float distance, float[] pos, float[] tan) ：
                // 传入一个距离distance(0<=distance<=getLength())，然后会计算当前距离的坐标点和切线，pos会自动填充上坐标，这个方法很重要。
                // mCurrentPosition此时就是中间距离点的坐标值
                mPathMeasure.getPosTan(value, mCurrentPosition, null);
                // 移动的商品图片（动画图片）的坐标设置为该中间点的坐标
                goods.setTranslationX(mCurrentPosition[0]);
                goods.setTranslationY(mCurrentPosition[1]);
            }
        });

        // 开始执行动画
        valueAnimator.start();

        // 动画结束后的处理
        valueAnimator.addListener(new android.animation.Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(android.animation.Animator animation) {
            }

            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                // 购物车商品数量加1
                goodsCount++;
//                isShowGoodsCountTv();
                // 把执行动画的商品图片从父布局中移除
                rl_parent.removeView(goods);
            }

            @Override
            public void onAnimationCancel(android.animation.Animator animation) {
            }

            @Override
            public void onAnimationRepeat(android.animation.Animator animation) {
            }
        });
    }

    @OnClick(R.id.rl_cart)
    public void onClick() {
        if (goodsCount == 0) {
            return;
        }
        showCart();
    }

    @OnClick(R.id.rl_btn_pay)
    public void rl_btn_pay() {
        if (tvBuyPrice.getText().toString().contains("去结算")) {
            showToast("去结算");
        } else {

        }
    }

    @Override
    public void doIncrease(int adapterType, int position, ImageView imgAdd, ImageView imgSubstract, TextView tvNum,
                           TextView tvPrice) {
        if (adapterType == 1) {
            int count = foodList.get(position).getCount();
            count++;
            tvNum.setText(count + "");

            foodList.get(position).setCount(count);
            foodList.get(position).setChecked(true);
            imgAdd.setImageResource(R.drawable.ic_num_add);
            tvNum.setVisibility(View.VISIBLE);
            imgSubstract.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged();
            Utility.setListViewHeightBasedOnChildren(lvRight);
        } else {
            int countCart = cartFoodList.get(position).getCount();
            countCart++;
            tvPrice.setText("¥" + cartFoodList.get(position).getPrice() * countCart);
            tvNum.setText(countCart + "");
            String id = cartFoodList.get(position).getId();
            for (int i = 0; i < foodList.size(); i++) {
                FoodModel fm = foodList.get(i);
                String id2 = fm.getId();
                //更新菜单中选中的菜品数量
                if (id.equals(id2)) {
                    fm.setCount(countCart);
                    adapter.notifyDataSetChanged();
                    Utility.setListViewHeightBasedOnChildren(lvRight);
                }
            }
            cartFoodList.get(position).setCount(countCart);
            cartAdapter.notifyDataSetChanged();
        }

        calculateMoneyAndNum(adapterType);
    }

    @Override
    public void doDecrease(int adapterType, int position, ImageView imgAdd, ImageView imgSubstract, TextView tvNum,
                           TextView tvPrice) {
        if (adapterType == 1) {
            int count = foodList.get(position).getCount();
            count--;
            tvNum.setText(count + "");
            foodList.get(position).setCount(count);
            if (count <= 0) {
                imgAdd.setImageResource(R.drawable.ic_addto_cart);
                tvNum.setVisibility(View.INVISIBLE);
                imgSubstract.setVisibility(View.INVISIBLE);
                //数量减为0，从购物车删除
                foodList.get(position).setChecked(false);
            }
            adapter.notifyDataSetChanged();
            Utility.setListViewHeightBasedOnChildren(lvRight);
        } else {
            int countCart = cartFoodList.get(position).getCount();
            countCart--;
            tvNum.setText(countCart + "");
            cartFoodList.get(position).setCount(countCart);

            String id = cartFoodList.get(position).getId();
            for (int i = 0; i < foodList.size(); i++) {
                FoodModel fm = foodList.get(i);
                String id2 = fm.getId();
                //更新菜单中选中的菜品数量
                if (id.equals(id2)) {
                    fm.setCount(countCart);
                    adapter.notifyDataSetChanged();
                    Utility.setListViewHeightBasedOnChildren(lvRight);
                }
            }

            if (countCart <= 0) {
                //数量减为0，从购物车删除
                cartFoodList.get(position).setChecked(false);
                cartFoodList.remove(position);
                cartAdapter.notifyItemRemoved(position);
                if (cartFoodList.size() == 0) {
                    setCartViewData();
                    popWindow.dismiss();
                }
            }
        }
        calculateMoneyAndNum(adapterType);
    }

    private void calculateMoneyAndNum(int adapterType) {
        goodsCount = 0;
        totalPrice = 0;
        if (adapterType == 2) {
            for (int i = 0; i < cartFoodList.size(); i++) {
                FoodModel fm = cartFoodList.get(i);
                if (fm.isChecked()) {
                    goodsCount += fm.getCount();
                    totalPrice += fm.getCount() * fm.getPrice();
                }
            }
        } else {
            for (int i = 0; i < foodList.size(); i++) {
                FoodModel fm = foodList.get(i);
                if (fm.isChecked()) {
                    goodsCount += fm.getCount();
                    totalPrice += fm.getCount() * fm.getPrice();
                }
            }
        }

        isShowGoodsCountTv();
        tv_goods_count.setText(goodsCount + "");
        tvSelectPrice.setText(totalPrice + "元");
        if (totalPrice >= 20) {
            //大于起送价20
            tvBuyPrice.setText("去结算");
            rl_btn_pay.setBackgroundColor(getResources().getColor(R.color.themeColor));
            rl_btn_pay.setClickable(true);
        } else {
            //不足20,差价
            double chajia = 20 - totalPrice;
            tvBuyPrice.setText("还差¥ " + chajia);
            rl_btn_pay.setBackgroundColor(getResources().getColor(R.color.btn_cant));
            rl_btn_pay.setClickable(false);
        }
        setCartViewData();
    }

    private void showCart() {
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context
                .LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.view_bottom_food_cart,
                null);
        popWindow = new MyPopupWindow(getActivity(), view, rlBottom);
        popWindow.showOnAnchor(rlBottom, RelativePopupWindow.VerticalPosition.ALIGN_BOTTOM, RelativePopupWindow
                .HorizontalPosition.ALIGN_LEFT);

        TextView tvClear = (TextView) view.findViewById(R.id.tv_clear_cart);
        tv_goods_count1 = (TextView) view.findViewById(R.id.tv_goods_count);
        tv_send_price1 = (TextView) view.findViewById(R.id.tv_send_price);
        tv_select_price1 = (TextView) view.findViewById(tv_select_price);
        tv_buy_price1 = (TextView) view.findViewById(tv_buy_price);
        rl_btn_pay1 = (RelativeLayout) view.findViewById(R.id.rl_btn_pay);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        setCartViewData();

        cartFoodList = new ArrayList<>();
        for (int i = 0; i < foodList.size(); i++) {
            FoodModel fm = foodList.get(i);
            if (fm.isChecked()) {
                cartFoodList.add(fm);
            }
        }
        cartAdapter = new FoodCartAdapter(getActivity(), cartFoodList);
        cartAdapter.setModifyCountAndPriceInterface(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new SlideInLeftAnimator());
        recyclerView.addItemDecoration(new RecycleViewDivider(
                getActivity(), LinearLayoutManager.HORIZONTAL, 1, getResources().getColor(R.color.divider_color)));
        recyclerView.setAdapter(cartAdapter);

        tvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartFoodList.clear();
                cartAdapter.notifyDataSetChanged();
                calculateMoneyAndNum(2);
                for (int i = 0; i < foodList.size(); i++) {
                    FoodModel fm = foodList.get(i);
                    if (fm.isChecked()) {
                        fm.setCount(0);
                        fm.setChecked(false);
                        adapter.notifyDataSetChanged();
                    }
                }
                popWindow.dismiss();
            }
        });

    }

    private void setCartViewData() {
        if (null != tv_goods_count1 && null != tv_select_price1 && null != tv_buy_price1 && null != tv_send_price1 &&
                null != rl_btn_pay1) {
            tv_goods_count1.setText(tv_goods_count.getText().toString());
            tv_select_price1.setText(tvSelectPrice.getText().toString());
            tv_buy_price1.setText(tvBuyPrice.getText().toString());
            tv_send_price1.setText(tvSendPrice.getText().toString());
            if (tv_buy_price1.getText().toString().contains("去结算")) {
                //大于起送价20
                rl_btn_pay1.setBackgroundColor(getResources().getColor(R.color.themeColor));
                rl_btn_pay1.setClickable(true);
            } else {
                rl_btn_pay1.setBackgroundColor(getResources().getColor(R.color.btn_cant));
                rl_btn_pay1.setClickable(false);
            }
            rl_btn_pay1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tv_buy_price1.getText().toString().contains("去结算")) {
                        showToast("去结算");
                    } else {

                    }
                }
            });
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void scroll() {

    }
}
