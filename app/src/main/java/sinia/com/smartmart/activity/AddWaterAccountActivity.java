package sinia.com.smartmart.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.smartmart.R;
import sinia.com.smartmart.adapter.AreaAdapter;
import sinia.com.smartmart.base.BaseActivity;
import sinia.com.smartmart.bean.JsonBean;
import sinia.com.smartmart.bean.VillageListBean;
import sinia.com.smartmart.utils.AppInfoUtil;
import sinia.com.smartmart.utils.Constants;
import sinia.com.smartmart.utils.DialogUtils;
import sinia.com.smartmart.utils.JsonUtil;
import sinia.com.smartmart.utils.Utils;

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

    private AsyncHttpClient client = new AsyncHttpClient();
    public Dialog dialog;
    public AreaAdapter areaAdapter;
    private List<VillageListBean.VillageBean> list = new ArrayList<>();
    private String villageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_water_account, "水费缴纳");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
        getVillageList();
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

    @OnClick({R.id.tv_area, R.id.tv_company, R.id.tv_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_area:
                createSelectAreaDialog(this, tvArea, list);
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
                tv_name.setBackgroundResource(R.drawable.theme_color_btn);
                tv_name.setTextColor(context.getResources().getColor(R.color.textwhite));
                tv_area.setText(list.get(i).getVillagename());
                villageId = list.get(i).getVillageid();
                dialog.dismiss();
            }
        });
        return dialog;
    }
}
