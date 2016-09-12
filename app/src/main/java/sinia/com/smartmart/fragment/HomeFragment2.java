package sinia.com.smartmart.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.smartmart.R;
import sinia.com.smartmart.adapter.HomeAdapter;
import sinia.com.smartmart.base.BaseFragment;
import sinia.com.smartmart.utils.AppInfoUtil;
import sinia.com.smartmart.utils.DensityUtil;
import sinia.com.smartmart.view.FilterView;
import sinia.com.smartmart.view.HeaderAdViewView;
import sinia.com.smartmart.view.HeaderFilterViewView;
import sinia.com.smartmart.view.HeaderLocationView;
import sinia.com.smartmart.view.HeaderOperationViewView;
import sinia.com.smartmart.view.SmoothListView.SmoothListView;

/**
 * Created by 忧郁的眼神 on 2016/9/3.
 */
public class HomeFragment2 extends BaseFragment {

    @Bind(R.id.listView)
    SmoothListView smoothListView;
    @Bind(R.id.imgSearch)
    ImageView imgSearch;
    @Bind(R.id.rl_bar)
    RelativeLayout rlBar;
    @Bind(R.id.fv_top_filter)
    FilterView fvTopFilter;
    private View rootView;
    private HomeAdapter adapter;
    private ArrayList<Integer> localImages = new ArrayList<Integer>();
    private int mScreenHeight; // 屏幕高度

    private HeaderAdViewView listViewAdHeaderView; // 广告视图
    private HeaderOperationViewView headerOperationViewView; // 运营视图
    private HeaderLocationView locationView;//地址视图
    private HeaderFilterViewView headerFilterViewView; // 分类筛选视图

    private View itemHeaderAdView; // 从ListView获取的广告子View
    private View itemHeaderFilterView; // 从ListView获取的筛选子View
    private boolean isScrollIdle = true; // ListView是否在滑动
    private boolean isStickyTop = false; // 是否吸附在顶部
    private boolean isSmooth = false; // 没有吸附的前提下，是否在滑动
    private int titleViewHeight = 45; // 标题栏的高度
    private int filterPosition = -1; // 点击FilterView的位置：分类(0)、排序(1)、筛选(2)

    private int adViewHeight = 180; // 广告视图的高度
    private int adViewTopSpace; // 广告视图距离顶部的距离

    private int filterViewPosition = 4; // 筛选视图的位置
    private int filterViewTopSpace; // 筛选视图距离顶部的距离

    private List<String> adList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home2, null);
        ButterKnife.bind(this, rootView);
        initData();
        initView();
        initListener();
        return rootView;
    }

    private void initView() {
        fvTopFilter.setVisibility(View.GONE);
        // 设置定位地址
        locationView = new HeaderLocationView(getActivity());
        locationView.fillView("月桂圆 14栋 2306室", smoothListView);

        // 设置广告数据
        listViewAdHeaderView = new HeaderAdViewView(getActivity());
        listViewAdHeaderView.fillView(adList, smoothListView);

        // 设置运营数据
        headerOperationViewView = new HeaderOperationViewView(getActivity());
        headerOperationViewView.fillView("", smoothListView);

        // 设置筛选数据
        headerFilterViewView = new HeaderFilterViewView(getActivity());
        headerFilterViewView.fillView(new Object(), smoothListView);

        //设置listivew
        adapter = new HomeAdapter(getActivity());
        smoothListView.setAdapter(adapter);

        filterViewPosition = smoothListView.getHeaderViewsCount() - 1;
    }

    private void initData() {
        mScreenHeight = DensityUtil.getWindowHeight(getActivity());
        adList.add("http://img0.imgtn.bdimg.com/it/u=1270781761,1881354959&fm=21&gp=0.jpg");
        adList.add("http://img0.imgtn.bdimg.com/it/u=2138116966,3662367390&fm=21&gp=0.jpg");
        adList.add("http://img0.imgtn.bdimg.com/it/u=1296117362,655885600&fm=21&gp=0.jpg");
    }

    private void initListener() {
        // (假的ListView头部展示的)筛选视图点击
        headerFilterViewView.setOnFilterClickListener(new HeaderFilterViewView.OnFilterClickListener() {
            @Override
            public void onFilterClick(int position) {
                filterPosition = position;
                isSmooth = true;
                smoothListView.smoothScrollToPositionFromTop(filterViewPosition, DensityUtil.dip2px(getActivity(),
                        titleViewHeight));
            }
        });

        // (真正的)筛选视图点击
        fvTopFilter.setOnFilterClickListener(new FilterView.OnFilterClickListener() {
            @Override
            public void onFilterClick(int position) {
                if (isStickyTop) {
                    filterPosition = position;
                    fvTopFilter.showFilterLayout(position);
                    if (titleViewHeight - 3 > filterViewTopSpace || filterViewTopSpace > titleViewHeight + 3) {
                        smoothListView.smoothScrollToPositionFromTop(filterViewPosition, DensityUtil.dip2px
                                (getActivity(), titleViewHeight));
                    }
                }
            }
        });

//        smoothListView.setRefreshEnable(true);
//        smoothListView.setLoadMoreEnable(true);
//        smoothListView.setSmoothListViewListener(ge);
        smoothListView.setOnScrollListener(new SmoothListView.OnSmoothScrollListener() {
            @Override
            public void onSmoothScrolling(View view) {
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                isScrollIdle = (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (isScrollIdle && adViewTopSpace < 0) return;

                // 获取广告头部View、自身的高度、距离顶部的高度
                if (itemHeaderAdView == null) {
                    itemHeaderAdView = smoothListView.getChildAt(1 - firstVisibleItem);
                }
                if (itemHeaderAdView != null) {
                    adViewTopSpace = DensityUtil.px2dip(getActivity(), itemHeaderAdView.getTop());
                    adViewHeight = DensityUtil.px2dip(getActivity(), itemHeaderAdView.getHeight());
                }

                // 获取筛选View、距离顶部的高度
                if (itemHeaderFilterView == null) {
                    itemHeaderFilterView = smoothListView.getChildAt(filterViewPosition - firstVisibleItem);
                }
                if (itemHeaderFilterView != null) {
                    filterViewTopSpace = DensityUtil.px2dip(getActivity(), itemHeaderFilterView.getTop());
                }

                // 处理筛选是否吸附在顶部
                if (filterViewTopSpace > titleViewHeight) {
                    isStickyTop = false; // 没有吸附在顶部
                    fvTopFilter.setVisibility(View.GONE);
                } else {
                    isStickyTop = true; // 吸附在顶部
                    fvTopFilter.setVisibility(View.VISIBLE);
                }

                if (firstVisibleItem > filterViewPosition) {
                    isStickyTop = true;
                    fvTopFilter.setVisibility(View.VISIBLE);
                }

                if (isSmooth && isStickyTop) {
                    isSmooth = false;
                    fvTopFilter.showFilterLayout(filterPosition);
                }

                fvTopFilter.setStickyTop(isStickyTop);

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.imgSearch)
    public void onClick() {
    }
}
