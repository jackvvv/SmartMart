package sinia.com.smartmart.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.ButterKnife;
import sinia.com.smartmart.R;
import sinia.com.smartmart.adapter.WaterAccountAdapter;
import sinia.com.smartmart.base.BaseActivity;
import sinia.com.smartmart.utils.AppInfoUtil;
import sinia.com.smartmart.view.swipmenulistview.SwipeMenu;
import sinia.com.smartmart.view.swipmenulistview.SwipeMenuCreator;
import sinia.com.smartmart.view.swipmenulistview.SwipeMenuItem;
import sinia.com.smartmart.view.swipmenulistview.SwipeMenuListView;

/**
 * Created by 忧郁的眼神 on 2016/9/6.
 */
public class WaterAccountListActivity extends BaseActivity {

    @Bind(R.id.listView)
    SwipeMenuListView listView;
    private WaterAccountAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_account, "水费缴纳");
        ButterKnife.bind(this);
        getImg_pic().setImageResource(R.drawable.icon_add);
        getImg_pic().setVisibility(View.VISIBLE);
        getDoingView().setVisibility(View.GONE);
        initData();
    }

    private void initData() {
        adapter = new WaterAccountAdapter(this);
        listView.setAdapter(adapter);
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xFF,
                        0xe8, 0xcd)));
                deleteItem.setWidth(AppInfoUtil.dip2px(WaterAccountListActivity.this, 90));
                deleteItem.setIcon(R.drawable.ic_delete);
                deleteItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        listView.setMenuCreator(creator);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu,
                                           int index) {
                switch (index) {
                    case 0:
//                        String id = list.get(position).getAddId();
//                        deleteAddress(id, position);
                }
                return false;
            }
        });
    }

    @Override
    public void doing() {
        super.doing();
        startActivityForNoIntent(AddWaterAccountActivity.class);
    }
}
