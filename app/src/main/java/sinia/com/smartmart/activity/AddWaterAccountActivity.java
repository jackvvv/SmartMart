package sinia.com.smartmart.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.smartmart.R;
import sinia.com.smartmart.base.BaseActivity;
import sinia.com.smartmart.utils.DialogUtils;

/**
 * Created by 忧郁的眼神 on 2016/9/6.
 */
public class AddWaterAccountActivity extends BaseActivity {

    @Bind(R.id.tv_area)
    TextView tvArea;
    @Bind(R.id.tv_company)
    TextView tvCompany;
    @Bind(R.id.et_card)
    EditText etCard;
    @Bind(R.id.tl)
    TextInputLayout tl;
    @Bind(R.id.tv_ok)
    TextView tvOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_water_account, "水费缴纳");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
    }

    @OnClick({R.id.tv_area, R.id.tv_company, R.id.tv_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_area:
                DialogUtils.createSelectAreaDialog(this, tvArea);
                break;
            case R.id.tv_company:
                break;
            case R.id.tv_ok:
                Intent intent = new Intent();
                intent.putExtra("fee_type", "1");
                startActivityForIntent(PayFeeActivity.class, intent);
                break;
        }
    }
}
