package sinia.com.smartmart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.smartmart.R;
import sinia.com.smartmart.base.BaseActivity;

/**
 * Created by 忧郁的眼神 on 2016/9/7.
 */
public class PropertyFeeActivity extends BaseActivity {

    @Bind(R.id.c)
    ImageView c;
    @Bind(R.id.tv_username)
    TextView tvUsername;
    @Bind(R.id.tv_no)
    TextView tvNo;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.img_into)
    ImageView imgInto;
    @Bind(R.id.tv_payfee)
    TextView tvPayfee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_fee, "物业费缴纳");
        getDoingView().setVisibility(View.GONE);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_payfee)
    public void onClick() {
        Intent intent = new Intent();
        intent.putExtra("fee_type", "4");
        startActivityForIntent(PayPropertyFeeActivity.class, intent);
    }
}
