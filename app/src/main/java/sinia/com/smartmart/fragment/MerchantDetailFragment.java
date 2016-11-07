package sinia.com.smartmart.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.smartmart.R;
import sinia.com.smartmart.activity.FoodAllCommentActivity;
import sinia.com.smartmart.adapter.FoodCommentAdapter;
import sinia.com.smartmart.base.BaseFragment;
import sinia.com.smartmart.view.RecycleViewDivider;

/**
 * Created by 忧郁的眼神 on 2016/10/27 0027.
 */

public class MerchantDetailFragment extends BaseFragment {
    @Bind(R.id.tv_avg_rating)
    TextView tvAvgRating;
    @Bind(R.id.tv_comment_num)
    TextView tvCommentNum;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.tv_allComment)
    TextView tvAllComment;
    @Bind(R.id.tv_notice_content)
    TextView tvNoticeContent;
    @Bind(R.id.tv_activity_content)
    TextView tvActivityContent;
    @Bind(R.id.tv_category)
    TextView tvCategory;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.rl_photo)
    RelativeLayout rlPhoto;
    @Bind(R.id.rl_lience)
    RelativeLayout rlLience;
    private View rootView;

    private FoodCommentAdapter adapter;
    private List<String> sortItem = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_merchant_dettail, null);
        ButterKnife.bind(this, rootView);
        initData();
        return rootView;
    }

    private void initData() {
        sortItem.add("小吃");
        sortItem.add("咸菜");
        adapter = new FoodCommentAdapter(getActivity(), R.layout.item_food_comment, sortItem);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new RecycleViewDivider(
                getActivity(), LinearLayoutManager.VERTICAL, 1, getResources().getColor(R.color.hint_color)));
        recyclerView.setAdapter(adapter);
    }

    @OnClick({R.id.tv_comment_num, R.id.tv_allComment, R.id.rl_photo, R.id.rl_lience})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_comment_num:
                intent = new Intent(getActivity(), FoodAllCommentActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_allComment:
                intent = new Intent(getActivity(), FoodAllCommentActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_photo:
                break;
            case R.id.rl_lience:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
