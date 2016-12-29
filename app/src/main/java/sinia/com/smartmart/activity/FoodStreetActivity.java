package sinia.com.smartmart.activity;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.smartmart.R;
import sinia.com.smartmart.adapter.CategoryLeftAdapter;
import sinia.com.smartmart.adapter.CategoryRightAdapter;
import sinia.com.smartmart.adapter.FoodSortAdapter;
import sinia.com.smartmart.adapter.HomeAdapter;
import sinia.com.smartmart.base.BaseActivity;

/**
 * Created by 忧郁的眼神 on 2016/10/26 0026.
 */

public class FoodStreetActivity extends BaseActivity {

    @Bind(R.id.tv_food)
    TextView tvFood;
    @Bind(R.id.img_food)
    ImageView imgFood;
    @Bind(R.id.ll_category)
    LinearLayout llCategory;
    @Bind(R.id.tv_sort)
    TextView tvSort;
    @Bind(R.id.img_sort)
    ImageView imgSort;
    @Bind(R.id.ll_sort)
    LinearLayout llSort;
    @Bind(R.id.tv_coupons)
    TextView tvCoupons;
    @Bind(R.id.img_coupons)
    ImageView imgCoupons;
    @Bind(R.id.ll_coupons)
    LinearLayout llCoupons;
    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.view_line)
    View view_line;
    @Bind(R.id.view_bg)
    View view_bg;

    private HomeAdapter adapter;
    private CategoryLeftAdapter leftAdapter;
    private CategoryRightAdapter rightAdapter;
    private FoodSortAdapter sortAdapter;
    private PopupWindow popWindow;
    private AsyncHttpClient client = new AsyncHttpClient();
    private String sortItem[] = {"智能排序", "距离最近", "评分最高", "销量最高", "起送价最低"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodstreet, "美食街店铺列表");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
        getImg_pic().setVisibility(View.VISIBLE);
        getImg_pic().setImageResource(R.drawable.ic_serarch);
        initView();
    }

    private void initView() {
        adapter = new HomeAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivityForNoIntent(FoodShopDetailActivity.class);
            }
        });
    }

    @Override
    public void doing() {
        super.doing();
    }

    @OnClick({R.id.ll_category, R.id.ll_sort, R.id.ll_coupons})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_category:
                tvFood.setSelected(true);
                imgFood.setSelected(true);
                tvSort.setSelected(false);
                imgSort.setSelected(false);
                showCategoryView();
                break;
            case R.id.ll_sort:
                tvFood.setSelected(false);
                imgFood.setSelected(false);
                tvSort.setSelected(true);
                imgSort.setSelected(true);
                showSortView();
                break;
            case R.id.ll_coupons:
                imgCoupons.setSelected(true);
                break;
        }
    }

    private void showCategoryView() {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.view_category,
                null);
        popWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        popWindow.setAnimationStyle(R.style.ActionSheetDialogAnimation2);
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(false);
        ColorDrawable dw = new ColorDrawable(0x00000000);
        popWindow.setBackgroundDrawable(dw);
        popWindow.showAsDropDown(view_line, 0, 0);
        final WindowManager.LayoutParams lp = getWindow()
                .getAttributes();
        lp.alpha = 1f;
        getWindow().setAttributes(lp);
        view_bg.setVisibility(View.VISIBLE);

        ListView lv_left = (ListView) view.findViewById(R.id.lv_left);
        ListView lv_right = (ListView) view.findViewById(R.id.lv_right);
        leftAdapter = new CategoryLeftAdapter(this);
        rightAdapter = new CategoryRightAdapter(this);
        lv_left.setAdapter(leftAdapter);
        lv_right.setAdapter(rightAdapter);
        leftAdapter.selectPosition = 0;
        rightAdapter.selectPosition = 0;

        lv_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                leftAdapter.selectPosition = position;
                leftAdapter.notifyDataSetChanged();
            }
        });
        lv_right.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                rightAdapter.selectPosition = position;
                rightAdapter.notifyDataSetChanged();
                tvFood.setText("盖浇饭");
                view_bg.setVisibility(View.GONE);
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
                popWindow.dismiss();
            }
        });

        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                lp.alpha = 1f;
                view_bg.setVisibility(View.GONE);
                getWindow().setAttributes(lp);
            }
        });
    }

    private void showSortView() {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.view_food_sort,
                null);
        popWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        popWindow.setAnimationStyle(R.style.ActionSheetDialogAnimation2);
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(false);
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        popWindow.showAsDropDown(view_line, 0, 0);
        final WindowManager.LayoutParams lp = getWindow()
                .getAttributes();
        lp.alpha = 1f;
        getWindow().setAttributes(lp);
        view_bg.setVisibility(View.VISIBLE);

        ListView lv_sort = (ListView) view.findViewById(R.id.lv_sort);
        sortAdapter = new FoodSortAdapter(this, sortItem);
        sortAdapter.selectPosition = 0;
        lv_sort.setAdapter(sortAdapter);
        lv_sort.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sortAdapter.selectPosition = position;
                sortAdapter.notifyDataSetChanged();
                tvSort.setText(sortItem[position]);
                view_bg.setVisibility(View.GONE);
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
                popWindow.dismiss();
            }
        });

        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                lp.alpha = 1f;
                view_bg.setVisibility(View.GONE);
                getWindow().setAttributes(lp);
            }
        });
    }
}
