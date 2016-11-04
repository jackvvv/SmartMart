package sinia.com.smartmart.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.ABaseTransformer;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.materialdialog.MaterialDialog;
import sinia.com.smartmart.R;
import sinia.com.smartmart.base.BaseActivity;
import sinia.com.smartmart.utils.ActivityManager;
import sinia.com.smartmart.utils.AppInfoUtil;
import sinia.com.smartmart.view.LocalImageHolderView;

import static sinia.com.smartmart.R.drawable.star;

/**
 * Created by 忧郁的眼神 on 2016/11/2 0002.
 */

public class BuildMaterialMarketActivity extends BaseActivity {

    @Bind(R.id.et_content)
    EditText etContent;
    @Bind(R.id.tv_goods_count)
    TextView tvGoodsCount;
    @Bind(R.id.convenientBanner)
    ConvenientBanner convenientBanner;
    private ArrayList<Integer> localImages = new ArrayList<Integer>();
    private MaterialDialog materialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_markets);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        localImages.add(AppInfoUtil.getResId("img_detss", R.drawable.class));
        localImages.add(AppInfoUtil.getResId("img_det", R.drawable.class));
        int h = AppInfoUtil.getScreenWidth(this) * 340 / 750;
        convenientBanner.getLayoutParams().height = h;
        String transforemerName = "StackTransformer";
        ABaseTransformer transforemer = null;
        try {
            Class cls = Class.forName("com.ToxicBakery.viewpager.transforms." + transforemerName);
            transforemer = (ABaseTransformer) cls.newInstance();
            convenientBanner.setPages(
                    new CBViewHolderCreator<LocalImageHolderView>() {
                        @Override
                        public LocalImageHolderView createHolder() {
                            return new LocalImageHolderView();
                        }
                    }, localImages).startTurning(3000).setPageIndicator(new int[]{R.drawable.carousel_point1, R.drawable
                    .carousel_point_select1})
                    .getViewPager().setPageTransformer(true, transforemer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.back, R.id.img_cart, R.id.tv_all, R.id.tv_comfirm, R.id.tv_delivery, R.id.tv_comment, R.id
            .tv_call, R.id.tv_tuliao, R.id.tv_bancai, R.id.tv_shuidian, R.id.tv_door, R.id.tv_diaoding, R.id
            .tv_chugui, R.id.tv_diban, R.id.tv_more, R.id.img_new, R.id.img_hot, R.id.img_cuxiao})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.back:
                ActivityManager.getInstance().finishCurrentActivity();
                break;
            case R.id.img_cart:
                break;
            case R.id.tv_all:
                intent = new Intent();
                intent.putExtra("orderType", "1");
                startActivityForIntent(BuildGoodsDetailActivity.class, intent);
                break;
            case R.id.tv_comfirm:
                intent = new Intent();
                intent.putExtra("orderType", "2");
                startActivityForIntent(BuildOrderManageActivity.class, intent);
                break;
            case R.id.tv_delivery:
                intent = new Intent();
                intent.putExtra("orderType", "3");
                startActivityForIntent(BuildOrderManageActivity.class, intent);
                break;
            case R.id.tv_comment:
                intent = new Intent();
                intent.putExtra("orderType", "4");
                startActivityForIntent(BuildOrderManageActivity.class, intent);
                break;
            case R.id.tv_call:
                callService();
                break;
            case R.id.tv_tuliao:
                break;
            case R.id.tv_bancai:
                break;
            case R.id.tv_shuidian:
                break;
            case R.id.tv_door:
                break;
            case R.id.tv_diaoding:
                break;
            case R.id.tv_chugui:
                break;
            case R.id.tv_diban:
                break;
            case R.id.tv_more:
                break;
            case R.id.img_new:
                break;
            case R.id.img_hot:
                break;
            case R.id.img_cuxiao:
                break;
        }
    }

    private void callService() {
        materialDialog = new MaterialDialog(this);
        materialDialog.setTitle("联系我们");
        materialDialog.setMessage("400-20392888");
        materialDialog.setPositiveButton("呼叫", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "400-20392888"));
                if (ActivityCompat.checkSelfPermission(BuildMaterialMarketActivity.this, Manifest.permission.CALL_PHONE)
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
