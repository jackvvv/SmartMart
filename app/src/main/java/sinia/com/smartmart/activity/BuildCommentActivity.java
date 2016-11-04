package sinia.com.smartmart.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.hedgehog.ratingbar.RatingBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.smartmart.R;
import sinia.com.smartmart.adapter.BuildOrderGoodsAdapter;
import sinia.com.smartmart.base.BaseActivity;
import sinia.com.smartmart.utils.Utility;

/**
 * Created by 忧郁的眼神 on 2016/11/3 0003.
 */

public class BuildCommentActivity extends BaseActivity {

    @Bind(R.id.tv_order_no)
    TextView tvOrderNo;
    @Bind(R.id.tv_status)
    TextView tvStatus;
    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.tv_num)
    TextView tvNum;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.rat_service)
    RatingBar ratService;
    @Bind(R.id.tv_rat_service)
    TextView tvRatService;
    @Bind(R.id.rat_goods)
    RatingBar ratGoods;
    @Bind(R.id.tv_rat_goods)
    TextView tvRatGoods;
    @Bind(R.id.et_content)
    EditText etContent;
    @Bind(R.id.tv_ok)
    TextView tvOk;

    private BuildOrderGoodsAdapter goodsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_comment, "评价商品");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
        initView();
    }

    private void initView() {
        ratService.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float RatingCount) {
                tvRatService.setText(Math.round(RatingCount) + "");
            }
        });
        ratGoods.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float RatingCount) {
                tvRatGoods.setText(Math.round(RatingCount) + "");
            }
        });

        goodsAdapter = new BuildOrderGoodsAdapter(this, false);
        listView.setAdapter(goodsAdapter);
        Utility.setListViewHeightBasedOnChildren(listView);
    }

    @OnClick(R.id.tv_ok)
    void click() {

    }
}
