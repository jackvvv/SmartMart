package sinia.com.smartmart.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.smartmart.R;
import sinia.com.smartmart.adapter.BuildMeterialListAdapter;
import sinia.com.smartmart.base.BaseActivity;

/**
 * Created by 忧郁的眼神 on 2016/11/11 0011.
 */

public class BuildMeterialListActivity extends BaseActivity {

    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.tv_total)
    TextView tvTotal;
    @Bind(R.id.tv_countMoney)
    TextView tvCountMoney;

    private BuildMeterialListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_meterial_list, "材料清单");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
        initView();
    }

    private void initView() {
        adapter = new BuildMeterialListAdapter(this);
        listView.setAdapter(adapter);
    }

    @OnClick(R.id.btnSettle)
    public void onClick() {
    }
}
