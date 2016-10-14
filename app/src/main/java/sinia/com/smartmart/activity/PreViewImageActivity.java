package sinia.com.smartmart.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.polites.android.GestureImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import sinia.com.smartmart.R;
import sinia.com.smartmart.base.BaseActivity;
import sinia.com.smartmart.utils.ActivityManager;
import sinia.com.smartmart.utils.BitmapUtilsHelp;


public class PreViewImageActivity extends BaseActivity {

	@Bind(R.id.big_img)
	GestureImageView big_img;

	private String imgUrl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_big_image);
		ButterKnife.bind(this);
		hideHeadView();
		initData();
	}

	private void initData() {
		imgUrl = getIntent().getStringExtra("picUrl");
		BitmapUtilsHelp.getImage(this, R.drawable.ic_launcher).display(big_img,
				imgUrl);
		big_img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				ActivityManager.getInstance().finishCurrentActivity();
			}
		});
	}

}
