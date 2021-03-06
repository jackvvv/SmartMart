package sinia.com.smartmart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tencent.bugly.beta.Beta;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.materialdialog.MaterialDialog;
import sinia.com.smartmart.R;
import sinia.com.smartmart.base.BaseActivity;
import sinia.com.smartmart.utils.ActivityManager;
import sinia.com.smartmart.utils.AppInfoUtil;
import sinia.com.smartmart.utils.DataCleanManager;
import sinia.com.smartmart.utils.MyApplication;

/**
 * Created by 忧郁的眼神 on 2016/9/12.
 */
public class GeneralSettingsActivity extends BaseActivity {

    @Bind(R.id.ll_modifypwd)
    LinearLayout llModifypwd;
    @Bind(R.id.ll_contact)
    LinearLayout llContact;
    @Bind(R.id.ll_about)
    LinearLayout llAbout;
    @Bind(R.id.tv_version)
    TextView tvVersion;
    @Bind(R.id.ll_version)
    LinearLayout llVersion;
    @Bind(R.id.tv_temp)
    TextView tvTemp;
    @Bind(R.id.ll_clear)
    LinearLayout llClear;
    @Bind(R.id.ll_feedback)
    LinearLayout llFeedback;
    private MaterialDialog materialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_settings, "通用设置");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
        initView();
    }

    private void initView() {
        tvTemp.setText(DataCleanManager.getTotalCacheSize(this));
        tvVersion.setText("v" + AppInfoUtil.getVersionName(this));
    }

    @OnClick({R.id.ll_modifypwd, R.id.ll_contact, R.id.ll_about, R.id.ll_version, R.id.ll_clear, R.id.ll_feedback, R
            .id.tv_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_modifypwd:
                if (MyApplication.getInstance().getBoolValue("is_login")) {
                    startActivityForNoIntent(ChangePasswordActivity.class);
                } else {
                    Intent intent = new Intent(this, LoginRegisterActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.login_open, 0);
                }
                break;
            case R.id.ll_contact:
                startActivityForNoIntent(ContactUsActivity.class);
                break;
            case R.id.ll_about:
                break;
            case R.id.ll_version:
                //手动检测更新
                Beta.checkUpgrade();
                break;
            case R.id.ll_clear:
                clearCache();
                break;
            case R.id.ll_feedback:
                if (MyApplication.getInstance().getBoolValue("is_login")) {
                    startActivityForNoIntent(ReportBackActivity.class);
                } else {
                    Intent intent = new Intent(this, LoginRegisterActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.login_open, 0);
                }
                break;
            case R.id.tv_ok:
                if (MyApplication.getInstance().getBoolValue("is_login")) {
                    logout();
                } else {
                    Intent intent = new Intent(this, LoginRegisterActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.login_open, 0);
                }
                break;
        }
    }

    private void clearCache() {
        materialDialog = new MaterialDialog(this);
        materialDialog.setTitle("提示").setMessage("缓存数据清理后将无法恢复，您确定要清理吗?")
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("清理了缓存" + DataCleanManager
                                .getTotalCacheSize(GeneralSettingsActivity.this));
                        DataCleanManager.clearAllCache(GeneralSettingsActivity.this);
                        tvTemp.setText(DataCleanManager
                                .getTotalCacheSize(GeneralSettingsActivity.this));
                        materialDialog.dismiss();
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDialog.dismiss();
            }
        }).show();
    }

    private void logout() {
        materialDialog = new MaterialDialog(this);
        materialDialog.setTitle("提示").setMessage("确定退出此账号?")
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyApplication.getInstance().setBooleanValue("is_login",
                                false);
                        MyApplication.getInstance().setUserNoticeBean(null);
                        MyApplication.getInstance().setUserInfo(null);
                        startLoginActivityForNoIntent(LoginRegisterActivity.class);
                        ActivityManager.getInstance().finishCurrentActivity();
                        ActivityManager.getInstance().finishActivity(SettingsActivity.class);
                        materialDialog.dismiss();
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDialog.dismiss();
            }
        }).show();
    }

}
