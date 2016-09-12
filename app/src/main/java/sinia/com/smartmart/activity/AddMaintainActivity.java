package sinia.com.smartmart.activity;

import android.Manifest;
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
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.smartmart.R;
import sinia.com.smartmart.actionsheetdialog.ActionSheetDialog;
import sinia.com.smartmart.utils.ActivityManager;
import sinia.com.smartmart.utils.SystemBarTintManager;

/**
 * Created by 忧郁的眼神 on 2016/9/6.
 */
public class AddMaintainActivity extends TakePhotoActivity {


    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.tl)
    TextInputLayout tl;
    @Bind(R.id.et_tel)
    EditText etTel;
    @Bind(R.id.tv_issue)
    TextView tvIssue;
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
    private int imgtype;//第几个图片

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_maintain);
        ButterKnife.bind(this);
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
    }

    @OnClick({R.id.tv_issue, R.id.img1, R.id.img2, R.id.img3, R.id.img4, R.id.tv_ok, R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_issue:
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

}
