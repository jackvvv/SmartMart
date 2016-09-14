package sinia.com.smartmart.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.ABaseTransformer;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.smartmart.R;
import sinia.com.smartmart.activity.SearchActivity;
import sinia.com.smartmart.adapter.HomeAdapter;
import sinia.com.smartmart.base.BaseFragment;
import sinia.com.smartmart.utils.AppInfoUtil;
import sinia.com.smartmart.utils.DialogUtils;
import sinia.com.smartmart.view.LocalImageHolderView;

/**
 * Created by 忧郁的眼神 on 2016/9/3.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.imgSearch)
    ImageView imgSearch;
    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.tv_all)
    TextView tvAll;
    @Bind(R.id.img_all)
    ImageView imgAll;
    @Bind(R.id.tv_smart)
    TextView tvSmart;
    @Bind(R.id.img_smart)
    ImageView imgSmart;
    @Bind(R.id.invis)
    LinearLayout invis;

    private TextView tvAddress;
    private LinearLayout ll_all, ll_smart;
    private ImageView imgFood, imgClean, imgWash, imgMore, imgJancai, imgFarm;
    private View rootView, headerView, stickyFilterView;
    private HomeAdapter adapter;
    private ArrayList<Integer> localImages = new ArrayList<Integer>();
    private ConvenientBanner convenientBanner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home, null);
        ButterKnife.bind(this, rootView);
        initView();
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
    }

    private void initView() {
        headerView = View.inflate(getActivity(), R.layout.view_home_head, null);
        stickyFilterView = View.inflate(getActivity(), R.layout.view_home_sticky_filter, null);
        convenientBanner = (ConvenientBanner) headerView.findViewById(R.id.convenientBanner);
        tvAddress = (TextView) headerView.findViewById(R.id.tv_address);
        imgFood = (ImageView) headerView.findViewById(R.id.imgFood);
        imgFarm = (ImageView) headerView.findViewById(R.id.imgFarm);
        imgJancai = (ImageView) headerView.findViewById(R.id.imgJancai);
        imgMore = (ImageView) headerView.findViewById(R.id.imgMore);
        imgWash = (ImageView) headerView.findViewById(R.id.imgWash);
        imgClean = (ImageView) headerView.findViewById(R.id.imgClean);
        ll_smart = (LinearLayout) stickyFilterView.findViewById(R.id.ll_smart);
        ll_all = (LinearLayout) stickyFilterView.findViewById(R.id.ll_all);
        setClickListener();
        listView.addHeaderView(headerView);
        listView.addHeaderView(stickyFilterView);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem >= 1) {
                    invis.setVisibility(View.VISIBLE);
                } else {
                    invis.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setClickListener() {
        imgFood.setOnClickListener(this);
        imgFarm.setOnClickListener(this);
        imgJancai.setOnClickListener(this);
        imgMore.setOnClickListener(this);
        imgWash.setOnClickListener(this);
        imgClean.setOnClickListener(this);
        ll_smart.setOnClickListener(this);
        ll_all.setOnClickListener(this);
    }

    @OnClick({R.id.imgSearch, R.id.llAll, R.id.llSmart})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.imgSearch:
                intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.llAll:
                showToast("1");
                break;
            case R.id.llSmart:
                showToast("2");
                break;
            case R.id.ll_smart:
                showToast("22");
                break;
            case R.id.ll_all:
                showToast("11");
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
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
