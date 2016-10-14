package sinia.com.smartmart.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
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
import me.drakeet.materialdialog.MaterialDialog;
import sinia.com.smartmart.R;
import sinia.com.smartmart.adapter.MaintainImageAdapter;
import sinia.com.smartmart.base.BaseActivity;
import sinia.com.smartmart.bean.JsonBean;
import sinia.com.smartmart.bean.MaintainDetailBean;
import sinia.com.smartmart.utils.ActivityManager;
import sinia.com.smartmart.utils.AppInfoUtil;
import sinia.com.smartmart.utils.BitmapUtilsHelp;
import sinia.com.smartmart.utils.Constants;
import sinia.com.smartmart.utils.JsonUtil;
import sinia.com.smartmart.utils.MyApplication;
import sinia.com.smartmart.utils.Utils;
import sinia.com.smartmart.view.MyGridView;

import static sinia.com.smartmart.R.id.tv_question;
import static sinia.com.smartmart.R.id.tv_status;

/**
 * Created by 忧郁的眼神 on 2016/9/7.
 */
public class MaintainDetailActivity extends BaseActivity {

    @Bind(tv_question)
    TextView tvQuestion;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(tv_status)
    TextView tvStatus;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_tel)
    TextView tvTel;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.tv_submit)
    TextView tvSubmit;
    @Bind(R.id.tv_ing)
    TextView tvIng;
    @Bind(R.id.tv_success)
    TextView tvSuccess;
    @Bind(R.id.tv_done)
    TextView tvDone;
    @Bind(R.id.tv_notdone)
    TextView tvNotdone;
    @Bind(R.id.tv_ok)
    TextView tvOk;
    @Bind(R.id.img_submit)
    ImageView imgSubmit;
    @Bind(R.id.img_ing)
    ImageView imgIng;
    @Bind(R.id.img_success)
    ImageView imgSuccess;
    @Bind(R.id.gridView)
    MyGridView gridView;
    private Dialog dialog;
    private MaterialDialog materialDialog;

    private String repairid, repairStatus;
    private MaintainImageAdapter adapter;
    private List<MaintainDetailBean.MaintainImageRescntBean.MaintainImageListBean> list = new ArrayList<>();
    private AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintain_detail, "报修详情");
        getDoingView().setText("删除");
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        repairid = getIntent().getStringExtra("repairid");
        showLoad("加载中...");
        RequestParams params = new RequestParams();
        params.put("memberid", MyApplication.getInstance().getUserInfo().getMemberid());
        params.put("repairid", repairid);
        Log.i("tag", Constants.BASE_URL + "repairdetail" + params);
        client.post(Constants.BASE_URL + "repairdetail", params,
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
                            MaintainDetailBean detailBean = gson.fromJson(resultStr, MaintainDetailBean.class);
                            setDetailData(detailBean);
                        } else {
                            showToast((String) json.getRescnt());
                        }
                    }
                });
        adapter = new MaintainImageAdapter(this, list);
        gridView.setAdapter(adapter);
    }

    private void setDetailData(MaintainDetailBean detailBean) {
        String repairType = detailBean.getRepairtype();
        repairStatus = detailBean.getRepairstatus();
        if ("1".equals(repairType)) {
            tvQuestion.setText("报修问题：公共部位报修");
        } else {
            tvQuestion.setText("报修问题：自用部位报修");
        }

        if ("1".equals(repairStatus)) {
            tvStatus.setText("待处理");
            imgSubmit.setImageResource(R.drawable.ic_process);
            imgIng.setImageResource(R.drawable.ic_process_n);
            imgSuccess.setImageResource(R.drawable.ic_process_n);
            tvSubmit.setTextColor(getResources().getColor(R.color.themeColor));
            tvIng.setTextColor(getResources().getColor(R.color.textblackColor));
            tvSuccess.setTextColor(getResources().getColor(R.color.textblackColor));
        } else if ("2".equals(repairStatus)) {
            tvStatus.setText("处理中");
            imgSubmit.setImageResource(R.drawable.ic_process);
            imgIng.setImageResource(R.drawable.ic_process);
            imgSuccess.setImageResource(R.drawable.ic_process_n);
            tvSubmit.setTextColor(getResources().getColor(R.color.themeColor));
            tvIng.setTextColor(getResources().getColor(R.color.themeColor));
            tvSuccess.setTextColor(getResources().getColor(R.color.textblackColor));
        } else if ("3".equals(repairStatus)) {
            tvStatus.setText("已解决");
            imgSubmit.setImageResource(R.drawable.ic_process);
            imgIng.setImageResource(R.drawable.ic_process);
            imgSuccess.setImageResource(R.drawable.ic_process);
            tvSubmit.setTextColor(getResources().getColor(R.color.themeColor));
            tvIng.setTextColor(getResources().getColor(R.color.themeColor));
            tvSuccess.setTextColor(getResources().getColor(R.color.themeColor));
            tvDone.setBackgroundResource(R.drawable.btn_cancle_s);
            tvNotdone.setBackgroundResource(R.drawable.btn_cancle_s);
            tvDone.setClickable(false);
            tvNotdone.setClickable(false);
        }
        tvContent.setText(detailBean.getContent());
        tvTime.setText(detailBean.getCreatetime());
        tvName.setText("户主：" + detailBean.getRepairname());
        tvTel.setText("联系电话：" + detailBean.getRepairmobile());
        tvAddress.setText("地址" + detailBean.getAddress());
        list.clear();
        list.addAll(detailBean.getRescnt().getList());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void doing() {
        super.doing();
        delete();
    }

    private void delete() {
        showLoad("加载中...");
        RequestParams params = new RequestParams();
        params.put("memberid", MyApplication.getInstance().getUserInfo().getMemberid());
        params.put("repairid", repairid);
        Log.i("tag", Constants.BASE_URL + "delrepair");
        client.post(Constants.BASE_URL + "delrepair", params,
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
                        int rescode = json.getRescode();
                        if (0 == rescode) {
                            showToast((String) json.getRescnt());
                            ActivityManager.getInstance().finishCurrentActivity();
                        } else {
                            showToast((String) json.getRescnt());
                        }
                    }
                });
    }


    private void handleMaintain(String handletype) {
        showLoad("加载中...");
        RequestParams params = new RequestParams();
        params.put("memberid", MyApplication.getInstance().getUserInfo().getMemberid());
        params.put("repairid", repairid);
        params.put("handletype", handletype);
        Log.i("tag", Constants.BASE_URL + "handlerepair");
        client.post(Constants.BASE_URL + "handlerepair", params,
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
                        int rescode = json.getRescode();
                        if (0 == rescode) {
                            showToast((String) json.getRescnt());
                            ActivityManager.getInstance().finishCurrentActivity();
                        } else {
                            showToast((String) json.getRescnt());
                        }
                    }
                });
    }

    @OnClick({R.id.tv_done, R.id.tv_notdone, R.id.tv_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_done:
                createMaintainResultDialog(this, "确认已解决相关问题？", "如已解决您的问题，请点击确定。感谢您的使用！", "1");
                break;
            case R.id.tv_notdone:
                createMaintainResultDialog(this, "我们将重新提交您的请求！", "给您带来不便，敬请谅解！我们将尽快安排人员向您联系！", "2");
                break;
            case R.id.tv_ok:
                callService();
                break;
        }
    }

    private void callService() {
        materialDialog = new MaterialDialog(this);
        materialDialog.setTitle("联系物业");
        materialDialog.setMessage("025-8888666");
        materialDialog.setPositiveButton("呼叫", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "400-20392888"));
                if (ActivityCompat.checkSelfPermission(MaintainDetailActivity.this, Manifest.permission.CALL_PHONE)
                        != PackageManager
                        .PERMISSION_GRANTED) {
                    return;
                }
                startActivity(intent);
                materialDialog.dismiss();
            }
        });
        materialDialog.setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDialog.dismiss();
            }
        });
        materialDialog.show();
    }

    private Dialog createMaintainResultDialog(final Context context, String title, String content, final String
            handletype) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialog_maintain_result, null);
        dialog = new Dialog(context, R.style.DialogScaleStyle);
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (display.getWidth() - AppInfoUtil.dip2px(context, 70)); // 设置宽度
        dialog.getWindow().setAttributes(lp);
        dialog.setContentView(v, lp);
        TextView tv_title = (TextView) v.findViewById(R.id.tv_title);
        TextView tv_content = (TextView) v.findViewById(R.id.tv_content);
        TextView tv_ok = (TextView) v.findViewById(R.id.tv_ok);
        TextView tv_cancle = (TextView) v.findViewById(R.id.tv_cancle);
        tv_title.setText(title);
        tv_content.setText(content);
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleMaintain(handletype);
                dialog.dismiss();
            }
        });
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        return dialog;
    }

}
