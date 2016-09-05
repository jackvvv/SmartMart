package sinia.com.smartmart.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.ABaseTransformer;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.smartmart.R;
import sinia.com.smartmart.base.BaseFragment;
import sinia.com.smartmart.utils.AppInfoUtil;
import sinia.com.smartmart.view.LocalImageHolderView;

/**
 * Created by 忧郁的眼神 on 2016/9/3.
 */
public class PropertyFragment extends BaseFragment {

    @Bind(R.id.img_new)
    ImageView imgNew;
    @Bind(R.id.rl_msg)
    RelativeLayout rlMsg;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.tv_notice)
    TextView tvNotice;
    @Bind(R.id.ll_water)
    LinearLayout llWater;
    @Bind(R.id.ll_elec)
    LinearLayout llElec;
    @Bind(R.id.ll_gas)
    LinearLayout llGas;
    @Bind(R.id.ll_property)
    LinearLayout llProperty;
    @Bind(R.id.ll_maintain)
    LinearLayout llMaintain;
    @Bind(R.id.ll_feedback)
    LinearLayout llFeedback;
    @Bind(R.id.convenientBanner)
    ConvenientBanner convenientBanner;
    private View rootView;
    private ArrayList<Integer> localImages = new ArrayList<Integer>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_property, null);
        ButterKnife.bind(this, rootView);
        initData();
        return rootView;
    }

    private void initData() {
        localImages.add(AppInfoUtil.getResId("img_det", R.drawable.class));
        localImages.add(AppInfoUtil.getResId("xiaoguo", R.drawable.class));
        int h = AppInfoUtil.getScreenWidth(getActivity()) * 200 / 750;
        convenientBanner.getLayoutParams().height = h;
        String transforemerName = "FlipHorizontalTransformer";
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
                    }, localImages).startTurning(3000).setPageIndicator(new int[]{R.drawable.carousel_point, R.drawable
                    .carousel_point_select})
                    .getViewPager().setPageTransformer(true, transforemer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.rl_msg, R.id.ll_water, R.id.ll_elec, R.id.ll_gas, R.id.ll_property, R.id.ll_maintain, R.id
            .ll_feedback})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_msg:
                break;
            case R.id.ll_water:
                break;
            case R.id.ll_elec:
                break;
            case R.id.ll_gas:
                break;
            case R.id.ll_property:
                break;
            case R.id.ll_maintain:
                break;
            case R.id.ll_feedback:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
