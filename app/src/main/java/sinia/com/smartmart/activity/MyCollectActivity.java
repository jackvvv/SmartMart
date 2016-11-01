package sinia.com.smartmart.activity;

import android.content.Context;
import android.graphics.Color;
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
import sinia.com.smartmart.adapter.AddressAdapter;
import sinia.com.smartmart.adapter.CategoryLeftAdapter;
import sinia.com.smartmart.adapter.CategoryRightAdapter;
import sinia.com.smartmart.adapter.FoodSortAdapter;
import sinia.com.smartmart.adapter.HomeAdapter;
import sinia.com.smartmart.base.BaseActivity;
import sinia.com.smartmart.utils.AppInfoUtil;
import sinia.com.smartmart.view.swipmenulistview.SwipeMenu;
import sinia.com.smartmart.view.swipmenulistview.SwipeMenuCreator;
import sinia.com.smartmart.view.swipmenulistview.SwipeMenuItem;
import sinia.com.smartmart.view.swipmenulistview.SwipeMenuListView;

import static sinia.com.smartmart.R.id.imgFood;
import static sinia.com.smartmart.R.id.listView;
import static sinia.com.smartmart.R.id.view_line;

/**
 * Created by 忧郁的眼神 on 2016/10/26 0026.
 */

public class MyCollectActivity extends BaseActivity {

    @Bind(R.id.listview)
    SwipeMenuListView listview;

    private HomeAdapter adapter;
    private AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collect, "我的收藏");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
        initView();
    }

    private void initView() {
        adapter = new HomeAdapter(this);
        listview.setAdapter(adapter);
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xFF,
                        0xe8, 0xcd)));
                deleteItem.setWidth(AppInfoUtil.dip2px(MyCollectActivity.this, 90));
                deleteItem.setIcon(R.drawable.ic_delete);
                deleteItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        listview.setMenuCreator(creator);
        listview.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu,
                                           int index) {
                switch (index) {
                    case 0:
                        break;
                }
                return false;
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivityForNoIntent(FoodShopDetailActivity.class);
            }
        });
    }

}
