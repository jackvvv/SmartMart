package sinia.com.smartmart.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import sinia.com.smartmart.R;
import sinia.com.smartmart.base.BaseActivity;
import sinia.com.smartmart.utils.ActivityManager;
import sinia.com.smartmart.utils.SystemBarTintManager;

/**
 * Created by 忧郁的眼神 on 2016/9/3.
 */
public class SplashActivity extends BaseActivity {

   @Bind(R.id.img_splash)
    ImageView imgSplash;
    @Bind(R.id.ll)
    LinearLayout ll;
    private AlphaAnimation aa;
    private SystemBarTintManager mTintManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        mTintManager = new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setNavigationBarTintEnabled(true);
        mTintManager.setTintColor(getResources().getColor(R.color.bg_splash));
        init();
    }

    private void init() {
        aa = new AlphaAnimation(0.1f, 1.0f);
        aa.setDuration(2000);
        ll.startAnimation(aa);

        Timer timer = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {

                startActivityForNoIntent(MainActivity.class);
                ActivityManager.getInstance().finishCurrentActivity();
            }
        };
        timer.schedule(tt, 2000);
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
