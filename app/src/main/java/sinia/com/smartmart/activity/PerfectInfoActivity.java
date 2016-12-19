package sinia.com.smartmart.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.smartmart.R;
import sinia.com.smartmart.adapter.AreaAdapter;
import sinia.com.smartmart.base.BaseActivity;
import sinia.com.smartmart.bean.JsonBean;
import sinia.com.smartmart.bean.UserBean;
import sinia.com.smartmart.bean.UserInfo;
import sinia.com.smartmart.bean.UserNoticeBean;
import sinia.com.smartmart.bean.VillageListBean;
import sinia.com.smartmart.utils.ActivityManager;
import sinia.com.smartmart.utils.AppInfoUtil;
import sinia.com.smartmart.utils.Constants;
import sinia.com.smartmart.utils.DialogUtils;
import sinia.com.smartmart.utils.JsonUtil;
import sinia.com.smartmart.utils.MyApplication;
import sinia.com.smartmart.utils.Utils;
import sinia.com.smartmart.utils.ValidationUtils;

/**
 * Created by 忧郁的眼神 on 2016/9/5.
 */
public class PerfectInfoActivity extends BaseActivity {

    @NotEmpty(message = "请选择小区")
    @Order(1)
    @Bind(R.id.tv_area)
    TextView tvArea;
    @NotEmpty(message = "请输入户主姓名")
    @Order(2)
    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.tl)
    TextInputLayout tl;
    @NotEmpty(message = "请输入您的栋数")
    @Order(3)
    @Bind(R.id.et_building)
    EditText etBuilding;
    @Bind(R.id.tv_getCode)
    TextView tvGetCode;
    @NotEmpty(message = "请输入单元")
    @Order(4)
    @Bind(R.id.et_cell)
    EditText etCell;
    @NotEmpty(message = "请输入楼层")
    @Order(5)
    @Bind(R.id.et_floor)
    EditText etFloor;
    @NotEmpty(message = "请输入门牌号")
    @Order(6)
    @Bind(R.id.et_doornum)
    EditText etDoornum;
    @Bind(R.id.tv_ok)
    TextView tvOk;
    @Bind(R.id.tv_login)
    TextView tvLogin;

    private Validator validator;
    private AsyncHttpClient client = new AsyncHttpClient();
    public Dialog dialog;
    public AreaAdapter areaAdapter;
    private List<VillageListBean.VillageBean> list = new ArrayList<VillageListBean.VillageBean>();
    private String villageId, mobile, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfect_info, "完善注册信息");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
        getVillageList();
        initView();
    }

    private void initView() {
        mobile = getIntent().getStringExtra("mobile");
        password = getIntent().getStringExtra("password");
        validator = new Validator(this);
        validator.setValidationListener(new ValidationUtils.ValidationListener() {
            @Override
            public void onValidationSucceeded() {
                super.onValidationSucceeded();
                register();
            }
        });
    }

    private void register() {
        showLoad("加载中...");
        RequestParams params = new RequestParams();
        params.put("mobile", mobile);
        params.put("password", password);
        params.put("villageid", villageId);
        params.put("username", etName.getEditableText().toString().trim());
        params.put("building", etBuilding.getEditableText().toString().trim());
        params.put("unit", etCell.getEditableText().toString().trim());
        params.put("floor", etFloor.getEditableText().toString().trim());
        params.put("number", etDoornum.getEditableText().toString().trim());
        Log.i("tag", Constants.BASE_URL + "register");
        client.post(Constants.BASE_URL + "register", params,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onFailure(Throwable arg0, String arg1) {
                        super.onFailure(arg0, arg1);
                    }

                    @Override
                    public void onSuccess(int arg0, String s) {
                        dismiss();
                        String resultStr = Utils
                                .getStrVal(new String(s));
                        JsonBean json = JsonUtil.getJsonBean(resultStr);
                        Gson gson = new Gson();
                        int rescode = json.getRescode();
                        if (0 == rescode) {
                            UserBean bean = gson.fromJson(resultStr,
                                    UserBean.class);
                            saveUserData(bean);
                            UserInfo info = bean.getRescnt();
                            MyApplication.getInstance().setUserInfo(info);
                            MyApplication.getInstance().setBooleanValue(
                                    "is_login", true);
                            startActivityForNoIntent(MainActivity.class);
                            ActivityManager.getInstance()
                                    .finishCurrentActivity();
                            ActivityManager.getInstance().finishActivity(LoginRegisterActivity.class);
                        } else {
                            showToast((String) json.getRescnt());
                            DialogUtils.createRegisterFailedTipsDialog(PerfectInfoActivity.this);
                        }
                    }
                });
    }

    private void saveUserData(UserBean bean) {
        UserNoticeBean unb = new UserNoticeBean();
        unb.setNoticedetail(bean.getNoticedetail());
        unb.setRatenum(bean.getRatenum());
        MyApplication.getInstance().setUserNoticeBean(unb);
    }

    private void getVillageList() {
        RequestParams params = new RequestParams();
        client.post(Constants.BASE_URL + "villagelist", params,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onFailure(Throwable arg0, String arg1) {
                        super.onFailure(arg0, arg1);
                    }

                    @Override
                    public void onSuccess(int arg0, String s) {
                        dismiss();
                        String resultStr = Utils
                                .getStrVal(new String(s));
                        JsonBean json = JsonUtil.getJsonBean(resultStr);
                        Gson gson = new Gson();
                        int rescode = json.getRescode();
                        if (0 == rescode) {
                            VillageListBean bean = gson.fromJson(resultStr,
                                    VillageListBean.class);
                            list.clear();
                            list.addAll(bean.getRescnt().getList());
                        } else {
                            showToast((String) json.getRescnt());
                        }
                    }
                });
    }

    @OnClick({R.id.tv_area, R.id.tv_ok, R.id.tv_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_area:
                createSelectAreaDialog(this, tvArea, list);
                break;
            case R.id.tv_ok:
//                if (StringUtil.isEmpty(villageId)) {
//                    showToast("请选择小区");
//                } else {
                validator.validate();
//                }
                break;
            case R.id.tv_login:
//                startLoginActivityForNoIntent(LoginRegisterActivity.class);
                startActivityForNoIntent(LoginRegisterActivity.class);
                ActivityManager.getInstance().finishCurrentActivity();
                break;
        }
    }

    public Dialog createSelectAreaDialog(final Context context, final TextView tv_area, final List<VillageListBean
            .VillageBean> list) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialog_select_area, null);
        dialog = new Dialog(context, R.style.DialogScaleStyle);
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (display.getWidth() - AppInfoUtil.dip2px(context, 70)); // 设置宽度
        lp.height = lp.width + AppInfoUtil.dip2px(context, 70);
        dialog.getWindow().setAttributes(lp);
        dialog.setContentView(v, lp);
        final ListView listView = (ListView) dialog.findViewById(R.id.listView);
        areaAdapter = new AreaAdapter(context, list);
        listView.setAdapter(areaAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
                tv_name.setBackgroundResource(R.drawable.theme_color_btn_draw);
                tv_name.setTextColor(context.getResources().getColor(R.color.textwhite));
                tv_area.setText(list.get(i).getVillagename());
                villageId = list.get(i).getVillageid();
                dialog.dismiss();
            }
        });
        return dialog;
    }
}
