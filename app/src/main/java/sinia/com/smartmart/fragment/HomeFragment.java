package sinia.com.smartmart.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.ABaseTransformer;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.cpoopc.scrollablelayoutlib.ScrollableHelper;
import com.cpoopc.scrollablelayoutlib.ScrollableLayout;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.smartmart.R;
import sinia.com.smartmart.adapter.HomeAdapter;
import sinia.com.smartmart.base.BaseFragment;
import sinia.com.smartmart.utils.AppInfoUtil;
import sinia.com.smartmart.utils.DialogUtils;
import sinia.com.smartmart.utils.Utility;
import sinia.com.smartmart.view.LocalImageHolderView;

/**
 * Created by 忧郁的眼神 on 2016/9/3.
 */
public class HomeFragment extends BaseFragment {

    @Bind(R.id.imgSearch)
    ImageView imgSearch;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.convenientBanner)
    ConvenientBanner convenientBanner;
    @Bind(R.id.img)
    ImageView img;
    @Bind(R.id.imgFood)
    ImageView imgFood;
    @Bind(R.id.imgFarm)
    ImageView imgFarm;
    @Bind(R.id.imgJancai)
    ImageView imgJancai;
    @Bind(R.id.imgMore)
    ImageView imgMore;
    @Bind(R.id.img_jiazheng)
    ImageView imgJiazheng;
    @Bind(R.id.imgWash)
    ImageView imgWash;
    @Bind(R.id.imgClean)
    ImageView imgClean;
    @Bind(R.id.img_all)
    ImageView img_all;
    @Bind(R.id.img_smart)
    ImageView img_smart;
    @Bind(R.id.tv_all)
    TextView tvAll;
    @Bind(R.id.tv_smart)
    TextView tvSmart;
    @Bind(R.id.listView)
    ListView listView;
    private View rootView;
    private HomeAdapter adapter;
    private ArrayList<Integer> localImages = new ArrayList<Integer>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home, null);
        ButterKnife.bind(this, rootView);
        initData();
        return rootView;
    }

    private void initData() {
        localImages.add(AppInfoUtil.getResId("img_det", R.drawable.class));
        localImages.add(AppInfoUtil.getResId("img_dets", R.drawable.class));
        int h = AppInfoUtil.getScreenWidth(getActivity()) * 340 / 750;
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

        adapter = new HomeAdapter(getActivity());
        listView.setAdapter(adapter);
        Utility.setListViewHeightBasedOnChildren(listView);
    }

    @OnClick({R.id.imgSearch, R.id.tv_address, R.id.imgFood, R.id.imgFarm, R.id.imgJancai, R.id.imgMore, R.id
            .img_jiazheng, R.id.imgWash, R.id.imgClean, R.id.tv_all, R.id.tv_smart})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgSearch:
                break;
            case R.id.tv_address:
                break;
            case R.id.imgFood:
                break;
            case R.id.imgFarm:
                DialogUtils.createFountionDevelopingTipsDialog(getActivity(), "生活服务正在完善中...");
                break;
            case R.id.imgJancai:
                DialogUtils.createFountionDevelopingTipsDialog(getActivity(), "生活服务正在完善中...");
                break;
            case R.id.imgMore:
                DialogUtils.createFountionDevelopingTipsDialog(getActivity(), "生活服务正在完善中...");
                break;
            case R.id.imgWash:
                DialogUtils.createFountionDevelopingTipsDialog(getActivity(), "洗衣服务正在完善中...");
                break;
            case R.id.imgClean:
                DialogUtils.createFountionDevelopingTipsDialog(getActivity(), "清洁服务正在完善中...");
                break;
            case R.id.tv_all:
                break;
            case R.id.tv_smart:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
