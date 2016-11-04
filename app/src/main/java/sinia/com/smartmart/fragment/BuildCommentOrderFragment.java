package sinia.com.smartmart.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.LandingAnimator;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import sinia.com.smartmart.R;
import sinia.com.smartmart.activity.BuildOrderDetailActivity;
import sinia.com.smartmart.adapter.BuildOrderAdapter;
import sinia.com.smartmart.base.BaseFragment;
import sinia.com.smartmart.mycallback.MyRecyclerViewClickListener;
import sinia.com.smartmart.view.RecycleViewDivider;

/**
 * Created by 忧郁的眼神 on 2016/11/3 0003.
 */

public class BuildCommentOrderFragment extends BaseFragment {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    private View rootView;

    private BuildOrderAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_build_material, null);
        ButterKnife.bind(this, rootView);
        initData();
        return rootView;
    }

    private void initData() {
        adapter = new BuildOrderAdapter(getActivity(), "4");
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new SlideInLeftAnimator());
        recyclerView.addItemDecoration(new RecycleViewDivider(
                getActivity(), LinearLayoutManager.HORIZONTAL, 20, getResources().getColor(R.color.divider_color2)));
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(new MyRecyclerViewClickListener() {
            @Override
            public void onitemClick(View v, int position) {
                Intent intent = new Intent(getActivity(), BuildOrderDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
