package sinia.com.smartmart.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.smartmart.R;
import sinia.com.smartmart.base.BaseActivity;
import sinia.com.smartmart.utils.DialogUtils;
import sinia.com.smartmart.utils.ValidationUtils;

/**
 * Created by 忧郁的眼神 on 2016/9/8.
 */
public class AddAddressActivity extends BaseActivity {

    @NotEmpty(message = "请输入收货人姓名")
    @Order(1)
    @Bind(R.id.et_name)
    EditText etName;
    @Pattern(regex = "^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$", message = "请输入正确的手机号码")
    @Order(2)
    @Bind(R.id.et_tel)
    EditText etTel;
    @NotEmpty(message = "请选择所在地区")
    @Order(3)
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @NotEmpty(message = "请输入详细地址")
    @Order(4)
    @Bind(R.id.et_detail)
    EditText etDetail;
    @Bind(R.id.ll_address)
    LinearLayout ll_address;
    @Bind(R.id.tv_ok)
    TextView tvOk;

    private Validator validator;
    private AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address, "添加新地址");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
        validator = new Validator(this);
        init();
    }

    private void init() {
        validator.setValidationListener(new ValidationUtils.ValidationListener() {
            @Override
            public void onValidationSucceeded() {
                super.onValidationSucceeded();
            }
        });
    }

    @OnClick({R.id.ll_address, R.id.tv_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_address:
                DialogUtils.createAddressDialog(AddAddressActivity.this, tvAddress);
                break;
            case R.id.tv_ok:
                validator.validate();
                break;
        }
    }
}
