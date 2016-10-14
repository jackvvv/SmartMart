package sinia.com.smartmart.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.smartmart.R;
import sinia.com.smartmart.actionsheetdialog.ActionSheetDialog;
import sinia.com.smartmart.base.BaseActivity;
import sinia.com.smartmart.bean.JsonBean;
import sinia.com.smartmart.utils.ActivityManager;
import sinia.com.smartmart.utils.Constants;
import sinia.com.smartmart.utils.JsonUtil;
import sinia.com.smartmart.utils.MyApplication;
import sinia.com.smartmart.utils.StringUtil;
import sinia.com.smartmart.utils.Utils;

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
    @Bind(R.id.ll_issue)
    LinearLayout ll_issue;

    private String advisetype = "1", complaintstype;
    private AsyncHttpClient client = new AsyncHttpClient();

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
                ll_issue.setVisibility(View.VISIBLE);
                advisetype = "1";
                break;
            case R.id.tv_suggest:
                tvSuggest.setSelected(true);
                tvTousu.setSelected(false);
                tvIssue.setText("选择建议类别");
                ll_issue.setVisibility(View.GONE);
                advisetype = "2";
                break;
            case R.id.tv_issue:
                createTypeDialog(this, tvIssue);
                break;
            case R.id.tv_ok:
                if (advisetype.equals("1")) {
                    if (StringUtil.isEmpty(complaintstype)) {
                        showToast("请选择投诉类别");
                    } else if (StringUtil.isEmpty(etContent.getEditableText().toString().trim())) {
                        showToast("请输入备注");
                    } else {
                        feedBack(advisetype, complaintstype);
                    }
                } else {
                    if (StringUtil.isEmpty(etContent.getEditableText().toString().trim())) {
                        showToast("请输入备注");
                    } else {
                        feedBack(advisetype, "-1");
                    }
                }
                break;
        }
    }

    private void feedBack(String advisetype, String complaintstype) {
        showLoad("提交中...");
        RequestParams params = new RequestParams();
        params.put("memberid", MyApplication.getInstance().getUserInfo().getMemberid());
        params.put("advisetype", advisetype);
        params.put("complaintstype", complaintstype);
        params.put("content", etContent.getEditableText().toString().trim());
        Log.i("tag", Constants.BASE_URL + "addadvise");
        client.post(Constants.BASE_URL + "addadvise", params,
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
                            showToast("反馈提交成功，感谢您的支持与配合");
                            ActivityManager.getInstance().finishCurrentActivity();
                        } else {
                            showToast((String) json.getRescnt());
                        }
                    }
                });
    }

    private void createTypeDialog(Context context, final TextView tv) {
        new ActionSheetDialog(context)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("设备类别", ActionSheetDialog.SheetItemColor.BLACK,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                tv.setText("设备类别");
                                complaintstype = "1";
                            }
                        })
                .addSheetItem("管理服务类别", ActionSheetDialog.SheetItemColor.BLACK,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                tv.setText("管理服务类别");
                                complaintstype = "2";
                            }
                        })
                .addSheetItem("收费类别", ActionSheetDialog.SheetItemColor.BLACK,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                tv.setText("收费类别");
                                complaintstype = "3";
                            }
                        })
                .addSheetItem("突发", ActionSheetDialog.SheetItemColor.BLACK,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                tv.setText("突发");
                                complaintstype = "4";
                            }
                        }).show();
    }
}
