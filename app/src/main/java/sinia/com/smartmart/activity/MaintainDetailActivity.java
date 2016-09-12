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
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.materialdialog.MaterialDialog;
import sinia.com.smartmart.R;
import sinia.com.smartmart.base.BaseActivity;
import sinia.com.smartmart.utils.AppInfoUtil;

/**
 * Created by 忧郁的眼神 on 2016/9/7.
 */
public class MaintainDetailActivity extends BaseActivity {

    @Bind(R.id.tv_question)
    TextView tvQuestion;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_status)
    TextView tvStatus;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.img1)
    ImageView img1;
    @Bind(R.id.img2)
    ImageView img2;
    @Bind(R.id.img3)
    ImageView img3;
    @Bind(R.id.img4)
    ImageView img4;
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
    private Dialog dialog;
    private MaterialDialog materialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintain_detail, "报修详情");
        getDoingView().setText("删除");
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_done, R.id.tv_notdone, R.id.tv_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_done:
                createMaintainResultDialog(this, "确认已解决相关问题？", "如已解决您的问题，请点击确定。感谢您的使用！");
                break;
            case R.id.tv_notdone:
                createMaintainResultDialog(this, "我们将重新提交您的请求！", "给您带来不便，敬请谅解！我们将尽快安排人员向您联系！");
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
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "025-8888666"));
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

    private Dialog createMaintainResultDialog(final Context context, String title, String content) {
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
