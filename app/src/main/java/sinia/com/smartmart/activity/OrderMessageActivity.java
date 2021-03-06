package sinia.com.smartmart.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.ButterKnife;
import sinia.com.smartmart.R;
import sinia.com.smartmart.adapter.OrderMessageAdapter;
import sinia.com.smartmart.base.BaseActivity;

/**
 * Created by 忧郁的眼神 on 2016/9/14.
 */
public class OrderMessageActivity extends BaseActivity {

    @Bind(R.id.listView)
    ListView listView;
    private OrderMessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_message, "订单消息");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
        initData();
    }

    private void initData() {
        adapter = new OrderMessageAdapter(this);
        listView.setAdapter(adapter);
    }
}
