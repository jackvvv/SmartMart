package sinia.com.smartmart.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.smartmart.R;
import sinia.com.smartmart.adapter.BuildCategoryLeftAdapter;
import sinia.com.smartmart.adapter.BuildCategoryRightAdapter;
import sinia.com.smartmart.base.BaseActivity;
import sinia.com.smartmart.utils.ActivityManager;

/**
 * Created by 忧郁的眼神 on 2016/11/14 0014.
 */

public class BuildMoreCategoryActivity extends BaseActivity {

    @Bind(R.id.et_content)
    EditText etContent;
    @Bind(R.id.lv_category)
    ListView lvCategory;
    @Bind(R.id.gv_brand)
    GridView gvBrand;
    private BuildCategoryLeftAdapter leftAdapter;
    private BuildCategoryRightAdapter rightAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_more_category);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        leftAdapter = new BuildCategoryLeftAdapter(this);
        leftAdapter.selectPosition = 0;
        lvCategory.setAdapter(leftAdapter);
        lvCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                leftAdapter.selectPosition = position;
                leftAdapter.notifyDataSetChanged();
            }
        });
        rightAdapter = new BuildCategoryRightAdapter(this);
        gvBrand.setAdapter(rightAdapter);
        gvBrand.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivityForNoIntent(BuildMarketListActivity.class);
            }
        });
    }

    @OnClick({R.id.back, R.id.tv_look_all})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                ActivityManager.getInstance().finishCurrentActivity();
                break;
            case R.id.tv_look_all:
                break;
        }
    }
}
