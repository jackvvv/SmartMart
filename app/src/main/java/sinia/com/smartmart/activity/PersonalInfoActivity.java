package sinia.com.smartmart.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.lang.reflect.Field;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UploadFileListener;
import sinia.com.smartmart.R;
import sinia.com.smartmart.actionsheetdialog.ActionSheetDialog;
import sinia.com.smartmart.bean.JsonBean;
import sinia.com.smartmart.bean.UserBean;
import sinia.com.smartmart.bean.UserInfo;
import sinia.com.smartmart.bean.VillageListBean;
import sinia.com.smartmart.utils.ActivityManager;
import sinia.com.smartmart.utils.BitmapUtilsHelp;
import sinia.com.smartmart.utils.Constants;
import sinia.com.smartmart.utils.DialogUtils;
import sinia.com.smartmart.utils.JsonUtil;
import sinia.com.smartmart.utils.MyApplication;
import sinia.com.smartmart.utils.StringUtil;
import sinia.com.smartmart.utils.SystemBarTintManager;
import sinia.com.smartmart.utils.Utils;
import sinia.com.smartmart.view.CircleImageView;
import sinia.com.smartmart.view.loadingview.LoadingView;

import static android.R.id.list;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static sinia.com.smartmart.R.id.s;

/**
 * Created by 忧郁的眼神 on 2016/9/8.
 */
public class PersonalInfoActivity extends TakePhotoActivity implements SlidingPaneLayout.PanelSlideListener{

    @Bind(R.id.img_head)
    CircleImageView imgHead;
    @Bind(R.id.et_name)
    TextView etName;
    @Bind(R.id.tv_sex)
    TextView tvSex;
    @Bind(R.id.rl_sex)
    RelativeLayout rlSex;
    @Bind(R.id.et_address)
    TextView etAddress;
    @Bind(R.id.rl_address)
    RelativeLayout rlAddress;
    @Bind(R.id.et_area)
    TextView etArea;
    @Bind(R.id.et_tel)
    TextView etTel;
    @Bind(R.id.back)
    TextView back;
    private SystemBarTintManager mTintManager;
    private String imgPath, imgUrl, sexType;
    private AsyncHttpClient client = new AsyncHttpClient();
    private UserInfo user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);
        ButterKnife.bind(this);
        Bmob.initialize(this, Constants.BMOB_KEY);
        ActivityManager.getInstance().addActivity(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        mTintManager = new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setNavigationBarTintEnabled(true);
        mTintManager.setTintColor(getResources().getColor(R.color.themeColor));
        initData();
    }

    private void initData() {
        user = MyApplication.getInstance().getUserInfo();
        etName.setText(user.getUsername());
        etAddress.setText(user.getAddress());
        etArea.setText(user.getHousearea());
        etTel.setText(user.getMobile());
        BitmapUtilsHelp.getImage(this, R.drawable
                .head_default).display(imgHead, user.getIcon());
        if ("1".equals(user.getSex())) {
            tvSex.setText("男");
            sexType = "1";
        } else {
            tvSex.setText("女");
            sexType = "2";
        }
    }

    @OnClick({R.id.img_head, R.id.rl_sex, R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_head:
                selectHeadImage();
                break;
            case R.id.rl_sex:
                createSexDialog(this, tvSex);
                break;
            case R.id.back:
                ActivityManager.getInstance().finishCurrentActivity();
                break;
        }
    }

    private void createSexDialog(Context context, final TextView tv) {
        new ActionSheetDialog(context)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("男", ActionSheetDialog.SheetItemColor.BLACK,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                tv.setText("男");
                                sexType = "1";
                                updateUserInfo("1", imgUrl);
                            }
                        })
                .addSheetItem("女", ActionSheetDialog.SheetItemColor.BLACK,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                tv.setText("女");
                                sexType = "2";
                                updateUserInfo("2", imgUrl);
                            }
                        }).show();
    }

    private void updateUserInfo(String sex, String imgUrl) {
        showLoad("修改中...");
        RequestParams params = new RequestParams();
        params.put("memberid", user.getMemberid());
        params.put("icon", imgUrl);
        params.put("sex", sex);
        Log.i("tag", Constants.BASE_URL + "changememinfo&" + params);
        client.post(Constants.BASE_URL + "changememinfo", params,
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
                            UserInfo info = bean.getRescnt();
                            MyApplication.getInstance().setUserInfo(info);
                            Toast.makeText(PersonalInfoActivity.this, "修改成功", Toast.LENGTH_SHORT)
                                    .show();
                        } else {
                            Toast.makeText(PersonalInfoActivity.this, (String) json.getRescnt(), Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
    }

    private void selectHeadImage() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        final Uri imageUri = Uri.fromFile(file);
        final CompressConfig compressConfig = new CompressConfig.Builder().setMaxSize(50 * 1024).setMaxPixel(800)
                .create();
        final CropOptions cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(true)
                .create();
        new ActionSheetDialog(this)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("拍照选择", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                //从相机拍取照片不裁剪
//                                getTakePhoto().onEnableCompress(compressConfig, true).onPickFromCapture(imageUri);
                                //从相机拍取照片进行裁剪
                                getTakePhoto().onEnableCompress(compressConfig, true).onPickFromCaptureWithCrop
                                        (imageUri, cropOptions);
                            }
                        })
                .addSheetItem("从手机相册选择", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                //从相册选择照片不裁切
//                                getTakePhoto().onEnableCompress(compressConfig, true).onPickFromGallery();
                                //从相册选择照片进行裁剪
                                getTakePhoto().onEnableCompress(compressConfig, true).onPickFromGalleryWithCrop
                                        (imageUri, cropOptions);
                            }
                        }).show();
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(String msg) {
        super.takeFail(msg);
    }

    @Override
    public void takeSuccess(String imagePath) {
        super.takeSuccess(imagePath);
        showImg(imagePath);
        uploadImage(imagePath);
    }

    private void uploadImage(String avataPath) {
        if (avataPath != null) {
            showLoad("正在上传头像");
            final BmobFile file = new BmobFile(new File(avataPath));
            file.upload(this, new UploadFileListener() {

                @Override
                public void onSuccess() {
                    dismiss();
                    imgUrl = file.getFileUrl(PersonalInfoActivity.this);
                    // showToast("图片上传成功");
                    updateUserInfo(sexType, imgUrl);
                }

                @Override
                public void onFailure(int arg0, String arg1) {
                    Log.i("tag", "图片上传失败" + arg1);
                    dismiss();
                }
            });
        }
    }

    private void showImg(String imagePath) {
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inSampleSize = 2;
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, option);
        imgPath = imagePath;
        imgHead.setImageBitmap(bitmap);
    }


    private void setTranslucentStatus(boolean on) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
    }

    private Dialog dialog;
    private View mDialogContentView;
    private LoadingView mLoadingView;

    public void showLoad(String text) {
        dialog = new Dialog(this, R.style.custom_dialog);
        mDialogContentView = LayoutInflater.from(this).inflate(
                R.layout.layout_loading_dialog, null);
        mLoadingView = (LoadingView) mDialogContentView
                .findViewById(R.id.loadView);
        if (StringUtil.isEmpty(text)) {
            mLoadingView.setLoadingText("加载中...");
        } else {
            mLoadingView.setLoadingText(text);
        }
        Display d = getWindowManager().getDefaultDisplay();
        dialog.show();
        dialog.setContentView(mDialogContentView, new ViewGroup.LayoutParams((int) (d.getWidth() * 0.5),
                (int) (d.getHeight() * 0.3)));
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.setCanceledOnTouchOutside(false);
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private void initSwipeBackFinish() {
        if (isSupportSwipeBack()) {
            SlidingPaneLayout slidingPaneLayout = new SlidingPaneLayout(this);
            //通过反射改变mOverhangSize的值为0，这个mOverhangSize值为菜单到右边屏幕的最短距离，默认
            //是32dp，现在给它改成0
            try {
                //属性
                Field f_overHang = SlidingPaneLayout.class.getDeclaredField("mOverhangSize");
                f_overHang.setAccessible(true);
                f_overHang.set(slidingPaneLayout, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            slidingPaneLayout.setPanelSlideListener(this);
            slidingPaneLayout.setSliderFadeColor(getResources().getColor(android.R.color.transparent));

            View leftView = new View(this);
            leftView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            slidingPaneLayout.addView(leftView, 0);

            ViewGroup decor = (ViewGroup) getWindow().getDecorView();
            ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
            decorChild.setBackgroundColor(getResources().getColor(android.R.color.white));
            decor.removeView(decorChild);
            decor.addView(slidingPaneLayout);
            slidingPaneLayout.addView(decorChild, 1);
        }
    }

    /**
     * 是否支持滑动返回
     *
     * @return
     */
    protected boolean isSupportSwipeBack() {
        return true;
    }

    @Override
    public void onPanelClosed(View view) {

    }

    @Override
    public void onPanelOpened(View view) {
        finish();
        this.overridePendingTransition(0, R.anim.slide_right_out);
    }

    @Override
    public void onPanelSlide(View view, float v) {
    }

}
