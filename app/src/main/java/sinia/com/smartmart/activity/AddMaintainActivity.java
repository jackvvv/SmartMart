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
import android.support.design.widget.TextInputLayout;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Pattern;

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
import sinia.com.smartmart.bean.UserInfo;
import sinia.com.smartmart.utils.ActivityManager;
import sinia.com.smartmart.utils.Constants;
import sinia.com.smartmart.utils.JsonUtil;
import sinia.com.smartmart.utils.MyApplication;
import sinia.com.smartmart.utils.StringUtil;
import sinia.com.smartmart.utils.SystemBarTintManager;
import sinia.com.smartmart.utils.Utils;
import sinia.com.smartmart.utils.ValidationUtils;
import sinia.com.smartmart.view.loadingview.LoadingView;

/**
 * Created by 忧郁的眼神 on 2016/9/6.
 */
public class AddMaintainActivity extends TakePhotoActivity implements SlidingPaneLayout.PanelSlideListener{

    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @NotEmpty(message = "请填写联系人")
    @Order(1)
    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.tl)
    TextInputLayout tl;
    @Pattern(regex = "^(13[0-9]|15[0-9]|17[0-9]|18[0-9]|14[0-9])[0-9]{8}$", message = "请输入正确的手机号码")
    @Order(2)
    @Bind(R.id.et_tel)
    EditText etTel;
    @NotEmpty(message = "请选择报修情况")
    @Order(3)
    @Bind(R.id.tv_issue)
    TextView tvIssue;
    @NotEmpty(message = "请输入备注")
    @Order(4)
    @Bind(R.id.et_content)
    EditText etContent;
    @Bind(R.id.img1)
    ImageView img1;
    @Bind(R.id.img2)
    ImageView img2;
    @Bind(R.id.img3)
    ImageView img3;
    @Bind(R.id.img4)
    ImageView img4;
    @Bind(R.id.tv_ok)
    TextView tvOk;
    @Bind(R.id.back)
    TextView back;
    private SystemBarTintManager mTintManager;
    private String imgPath1, imgPath2, imgPath3, imgPath4;
    private String image1 = "-1", image2 = "-1", image3 = "-1", image4 = "-1", imgUrl;
    private int imgtype;//第几个图片
    private UserInfo user;
    private String repairtype;
    private Validator validator;
    private AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_maintain);
        ButterKnife.bind(this);
        Bmob.initialize(this, Constants.BMOB_KEY);
        ActivityManager.getInstance().addActivity(this);
        initSwipeBackFinish();
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
        tvName.setText(user.getUsername());
        tvAddress.setText(user.getAddress());
        validator = new Validator(this);
        validator.setValidationListener(new ValidationUtils.ValidationListener() {
            @Override
            public void onValidationSucceeded() {
                super.onValidationSucceeded();
                submit();
            }
        });
    }

    private void submit() {
        showLoad("提交中...");
        RequestParams params = new RequestParams();
        params.put("memberid", MyApplication.getInstance().getUserInfo().getMemberid());
        params.put("repairname", etName.getEditableText().toString().trim());
        params.put("repairmobile", etTel.getEditableText().toString().trim());
        params.put("repairtype", repairtype);
        params.put("image1", image1);
        params.put("image2", image2);
        params.put("image3", image3);
        params.put("image4", image4);
        params.put("content", etContent.getEditableText().toString().trim());
        Log.i("tag", Constants.BASE_URL + "addrepair");
        client.post(Constants.BASE_URL + "addrepair", params,
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
                            Toast.makeText(AddMaintainActivity.this, (String) json.getRescnt(), Toast.LENGTH_SHORT)
                                    .show();
                            ActivityManager.getInstance().finishCurrentActivity();
                        } else {
                            Toast.makeText(AddMaintainActivity.this, (String) json.getRescnt(), Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
    }

    @OnClick({R.id.tv_issue, R.id.img1, R.id.img2, R.id.img3, R.id.img4, R.id.tv_ok, R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_issue:
                createTypeDialog(this, tvIssue);
                break;
            case R.id.img1:
                imgtype = 1;
                selectHeadImage();
                break;
            case R.id.img2:
                imgtype = 2;
                selectHeadImage();
                break;
            case R.id.img3:
                imgtype = 3;
                selectHeadImage();
                break;
            case R.id.img4:
                imgtype = 4;
                selectHeadImage();
                break;
            case R.id.tv_ok:
                validator.validate();
                Log.i("tag", "1-----" + imgPath1 + "--2--" + imgPath2 + "--3--" + imgPath3 + "--4--" + imgPath4);
                break;
            case R.id.back:
                ActivityManager.getInstance().finishCurrentActivity();
                break;
        }
    }

    private void selectHeadImage() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        final Uri imageUri = Uri.fromFile(file);
        final CompressConfig compressConfig = new CompressConfig.Builder().setMaxSize(50 * 1024).setMaxPixel(800)
                .create();
        CropOptions cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(true)
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
                                getTakePhoto().onEnableCompress(compressConfig, true).onPickFromCapture(imageUri);
                                //从相机拍取照片进行裁剪
                                //getTakePhoto().onEnableCompress(compressConfig, true).onPickFromCaptureWithCrop
                                // (imageUri, cropOptions);
                            }
                        })
                .addSheetItem("从手机相册选择", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                //从相册选择照片不裁切
                                getTakePhoto().onEnableCompress(compressConfig, true).onPickFromGallery();
                                //从相册选择照片进行裁剪
                                //getTakePhoto().onEnableCompress(compressConfig, true).onPickFromGalleryWithCrop
                                // (imageUri, cropOptions);
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
                    imgUrl = file.getFileUrl(AddMaintainActivity.this);
                    // showToast("图片上传成功");
                    if (imgtype == 1) {
                        image1 = imgUrl;
                    }
                    if (imgtype == 2) {
                        image2 = imgUrl;
                    }
                    if (imgtype == 3) {
                        image3 = imgUrl;
                    }
                    if (imgtype == 4) {
                        image4 = imgUrl;
                    }
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
        if (imgtype == 1) {
            imgPath1 = imagePath;
            img1.setImageBitmap(bitmap);
        }
        if (imgtype == 2) {
            imgPath2 = imagePath;
            img2.setImageBitmap(bitmap);
        }
        if (imgtype == 3) {
            imgPath3 = imagePath;
            img3.setImageBitmap(bitmap);
        }
        if (imgtype == 4) {
            imgPath4 = imagePath;
            img4.setImageBitmap(bitmap);
        }
    }

    private void createTypeDialog(Context context, final TextView tv) {
        new ActionSheetDialog(context)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("公共部位报修", ActionSheetDialog.SheetItemColor.BLACK,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                tv.setText("公共部位报修");
                                repairtype = "1";
                            }
                        })
                .addSheetItem("自用部位报修", ActionSheetDialog.SheetItemColor.BLACK,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                tv.setText("自用部位报修");
                                repairtype = "2";
                            }
                        }).show();
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
