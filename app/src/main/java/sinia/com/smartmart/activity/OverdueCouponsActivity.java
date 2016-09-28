package sinia.com.smartmart.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.smartmart.R;
import sinia.com.smartmart.adapter.CouponsAdapter;
import sinia.com.smartmart.adapter.OverdueCouponsAdapter;
import sinia.com.smartmart.base.BaseActivity;

/**
 * Created by 忧郁的眼神 on 2016/9/18.
 */
public class OverdueCouponsActivity extends BaseActivity {

    @Bind(R.id.listView)
    ListView listView;

    private OverdueCouponsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupons_overdue, "往期红包");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
        initData();
    }

    private void initData() {
        adapter = new OverdueCouponsAdapter(this);
        listView.setAdapter(adapter);
    }

}
