package sinia.com.smartmart.activity;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.smartmart.R;
import sinia.com.smartmart.adapter.CouponsAdapter;
import sinia.com.smartmart.base.BaseActivity;

/**
 * Created by 忧郁的眼神 on 2016/9/18.
 */
public class MyCouponsActivity extends BaseActivity {

    @Bind(R.id.tv_num)
    TextView tvNum;
    @Bind(R.id.toOverdue)
    TextView toOverdue;
    @Bind(R.id.listView)
    ListView listView;

    private CouponsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupons, "我的优惠");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
        initData();
    }

    private void initData() {
        adapter = new CouponsAdapter(this);
        listView.setAdapter(adapter);
    }

    @OnClick(R.id.toOverdue)
    public void onClick() {
        startActivityForNoIntent(OverdueCouponsActivity.class);
    }
}
