package sinia.com.smartmart.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.smartmart.R;
import sinia.com.smartmart.base.BaseActivity;
import sinia.com.smartmart.fragment.BuildGoodsDetailFragment;
import sinia.com.smartmart.fragment.BuildImageFragment;
import sinia.com.smartmart.utils.ActivityManager;
import sinia.com.smartmart.view.SegmentView;

import static sinia.com.smartmart.R.id.f1;

/**
 * Created by 忧郁的眼神 on 2016/11/4 0004.
 */

public class BuildGoodsDetailActivity extends BaseActivity {

    @Bind(R.id.img_cart)
    ImageView imgCart;
    @Bind(R.id.tv_goods_count)
    TextView tvGoodsCount;
    @Bind(R.id.segmentView)
    SegmentView segmentView;
    @Bind(R.id.rl_bottom)
    LinearLayout rl_bottom;

    private BuildGoodsDetailFragment goodsDetailFragment;
    private BuildImageFragment imageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_goods_detail);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        goodsDetailFragment = BuildGoodsDetailFragment.newInstance();
        imageFragment = BuildImageFragment.newInstance();
        Bundle args = new Bundle();
        args.putSerializable("goodsBean", "");
        goodsDetailFragment.setArguments(args);
        imageFragment.setArguments(args);

        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        if (!goodsDetailFragment.isAdded()) {
            fragmentTransaction.hide(imageFragment)
                    .add(R.id.frame_content, goodsDetailFragment).show(goodsDetailFragment);
        } else {
            fragmentTransaction.hide(imageFragment).show(goodsDetailFragment);
        }
        fragmentTransaction.commit();

        segmentView.setOnSegmentViewClickListener(new SegmentView.onSegmentViewClickListener() {
            @Override
            public void onSegmentViewClick(View v, int position) {
                FragmentTransaction fragmentTransaction = BuildGoodsDetailActivity.this
                        .getSupportFragmentManager().beginTransaction();
                if (position == 0) {
                    if (!goodsDetailFragment.isAdded()) {
                        fragmentTransaction.hide(imageFragment)
                                .add(R.id.frame_content, goodsDetailFragment).show(goodsDetailFragment);
                    } else {
                        fragmentTransaction.hide(imageFragment).show(goodsDetailFragment);
                    }
                    fragmentTransaction.commit();
                } else {
                    if (!imageFragment.isAdded()) {
                        fragmentTransaction.hide(goodsDetailFragment)
                                .add(R.id.frame_content, imageFragment).show(imageFragment);
                    } else {
                        fragmentTransaction.hide(goodsDetailFragment).show(imageFragment);
                    }
                    fragmentTransaction.commit();
                }
            }
        });

    }

    @OnClick(R.id.back)
    public void onClick() {
        ActivityManager.getInstance().finishCurrentActivity();
    }
}
