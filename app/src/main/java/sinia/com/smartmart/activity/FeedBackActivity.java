package sinia.com.smartmart.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.smartmart.R;
import sinia.com.smartmart.base.BaseActivity;

/**
 * Created by 忧郁的眼神 on 2016/9/7.
 */
public class FeedBackActivity extends BaseActivity {

    @Bind(R.id.tv_tousu)
    TextView tvTousu;
    @Bind(R.id.tv_suggest)
    TextView tvSuggest;
    @Bind(R.id.tv_issue)
    TextView tvIssue;
    @Bind(R.id.et_content)
    EditText etContent;
    @Bind(R.id.tv_ok)
    TextView tvOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback, "投诉建议");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
        initData();
    }

    private void initData() {
        tvTousu.setSelected(true);
        tvSuggest.setSelected(false);
    }

    @OnClick({R.id.tv_tousu, R.id.tv_suggest, R.id.tv_issue, R.id.tv_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_tousu:
                tvTousu.setSelected(true);
                tvSuggest.setSelected(false);
                tvIssue.setText("选择投诉类别");
                break;
            case R.id.tv_suggest:
                tvSuggest.setSelected(true);
                tvTousu.setSelected(false);
                tvIssue.setText("选择建议类别");
                break;
            case R.id.tv_issue:
                break;
            case R.id.tv_ok:
                break;
        }
    }
}
