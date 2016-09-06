package sinia.com.smartmart.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.smartmart.R;
import sinia.com.smartmart.adapter.HotSearchAdapter;
import sinia.com.smartmart.base.BaseActivity;
import sinia.com.smartmart.utils.ActivityManager;

/**
 * Created by 忧郁的眼神 on 2016/9/6.
 */
public class SearchActivity extends BaseActivity {

    @Bind(R.id.back)
    TextView back;
    @Bind(R.id.et_content)
    EditText etContent;
    @Bind(R.id.tv_cancle)
    TextView tvCancle;
    @Bind(R.id.gv_hot)
    GridView gvHot;
    private HotSearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        adapter = new HotSearchAdapter(this);
        gvHot.setAdapter(adapter);
    }

    @OnClick({R.id.back, R.id.tv_cancle})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                ActivityManager.getInstance().finishCurrentActivity();
                break;
            case R.id.tv_cancle:
                ActivityManager.getInstance().finishCurrentActivity();
                break;
        }
    }
}
