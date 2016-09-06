package sinia.com.smartmart.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.ButterKnife;
import sinia.com.smartmart.R;
import sinia.com.smartmart.adapter.WaterAccountAdapter;
import sinia.com.smartmart.base.BaseActivity;

/**
 * Created by 忧郁的眼神 on 2016/9/6.
 */
public class WaterAccountListActivity extends BaseActivity {

    @Bind(R.id.listView)
    ListView listView;
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
    }

    @Override
    public void doing() {
        super.doing();
        startActivityForNoIntent(AddWaterAccountActivity.class);
    }
}
