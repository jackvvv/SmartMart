package sinia.com.smartmart.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hedgehog.ratingbar.RatingBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import sinia.com.smartmart.R;
import sinia.com.smartmart.adapter.BuildCommentAdapter;
import sinia.com.smartmart.base.BaseActivity;
import sinia.com.smartmart.view.RecycleViewDivider;

/**
 * Created by 忧郁的眼神 on 2016/10/27 0027.
 */

public class BuildAllCommentActivity extends BaseActivity {

    @Bind(R.id.tv_avg)
    TextView tvAvg;
    @Bind(R.id.ratingBar)
    RatingBar ratingBar;
    @Bind(R.id.tv_comment_num)
    TextView tvCommentNum;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    private BuildCommentAdapter adapter;
    private List<String> sortItem = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_all_comment, "全部评论");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
        initView();
    }

    private void initView() {
        ratingBar.setStar(4);
        sortItem.add("");
        sortItem.add("");
        sortItem.add("");
        sortItem.add("");
        sortItem.add("");
        sortItem.add("");
        sortItem.add("");
        sortItem.add("");
        sortItem.add("");
        sortItem.add("");
        sortItem.add("");
        adapter = new BuildCommentAdapter(this, R.layout.item_build_comment, sortItem);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecycleViewDivider(
                this, LinearLayoutManager.VERTICAL, 1, getResources().getColor(R.color.hint_color)));
        recyclerView.setAdapter(adapter);
    }
}
