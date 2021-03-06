package sinia.com.smartmart.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.materialdialog.MaterialDialog;
import sinia.com.smartmart.R;
import sinia.com.smartmart.base.BaseActivity;
import sinia.com.smartmart.fragment.BuildGoodsDetailFragment;
import sinia.com.smartmart.fragment.BuildImageFragment;
import sinia.com.smartmart.utils.ActivityManager;
import sinia.com.smartmart.view.SegmentView;

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
    @Bind(R.id.tv_collect)
    TextView tvCollect;

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

    @OnClick({R.id.back, R.id.tv_call, R.id.tv_collect, R.id.tv_add_cart, R.id.img_cart, R.id.tv_buy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                ActivityManager.getInstance().finishCurrentActivity();
                break;
            case R.id.tv_call:
                callService();
                break;
            case R.id.tv_collect:
                break;
            case R.id.tv_add_cart:
                startActivityForNoIntent(BuildCartActivity.class);
                break;
            case R.id.img_cart:
                startActivityForNoIntent(BuildCartActivity.class);
                break;
            case R.id.tv_buy:
                startActivityForNoIntent(BuildConfirmOrderActivity.class);
                break;
        }
    }

    private void callService() {
        final MaterialDialog materialDialog = new MaterialDialog(this);
        materialDialog.setTitle("联系客服");
        materialDialog.setMessage("18276623009");
        materialDialog.setPositiveButton("立即拨打", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "18276623009"));
                if (ActivityCompat.checkSelfPermission(BuildGoodsDetailActivity.this, Manifest.permission.CALL_PHONE)
                        != PackageManager
                        .PERMISSION_GRANTED) {
                    return;
                }
                startActivity(intent);
                materialDialog.dismiss();
            }
        });
        materialDialog.setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDialog.dismiss();
            }
        });
        materialDialog.show();
    }
}
