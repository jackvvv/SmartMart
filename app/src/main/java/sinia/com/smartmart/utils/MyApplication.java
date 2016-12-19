package sinia.com.smartmart.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import sinia.com.smartmart.bean.UserInfo;
import sinia.com.smartmart.bean.UserNoticeBean;


/**
 * Created by 忧郁的眼神 on 2016/7/15.
 */
public class MyApplication extends Application {

    private static MyApplication instance;

    public static Context context;
    private UserNoticeBean userNoticeBean;
    private UserInfo userInfo;
//    public static PushAgent mPushAgent;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        instance = this;
//        Bugly.init(getApplicationContext(), "16bbef9827", false);
//        Beta.checkUpgrade(false, false);
        initUMPush();
        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS).build();
        OkHttpUtils.initClient(okHttpClient);
    }

    private void initUMPush() {
//        mPushAgent = PushAgent.getInstance(this);
//        mPushAgent.enable();
//
//        UmengMessageHandler messageHandler = new UmengMessageHandler() {
//            /**
//             * 参考集成文档的1.6.3
//             * http://dev.umeng.com/push/android/integration#1_6_3
//             * */
//            @Override
//            public void dealWithCustomMessage(final Context context, final UMessage msg) {
//                new Handler().post(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        // TODO Auto-generated method stub
//                        // 对自定义消息的处理方式，点击或者忽略
//                        boolean isClickOrDismissed = true;
//                        if (isClickOrDismissed) {
//                            //自定义消息的点击统计
//                            UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
//                        } else {
//                            //自定义消息的忽略统计
//                            UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
//                        }
//                        Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
//                    }
//                });
//            }
//
//            @Override
//            public Notification getNotification(Context context,
//                                                UMessage msg) {
//                switch (msg.builder_id) {
//                    case 1:
//                        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//                        RemoteViews myNotificationView = new RemoteViews(context.getPackageName(), R.layout
//                                .notification_view);
//                        myNotificationView.setTextViewText(R.id.notification_title, msg.title);
//                        myNotificationView.setTextViewText(R.id.notification_text, msg.text);
//                        myNotificationView.setImageViewBitmap(R.id.notification_large_icon, getLargeIcon(context,
// msg));
//                        myNotificationView.setImageViewResource(R.id.notification_small_icon, getSmallIconId(context,
//                                msg));
//                        builder.setContent(myNotificationView)
//                                .setSmallIcon(getSmallIconId(context, msg))
//                                .setTicker(msg.ticker)
//                                .setAutoCancel(true);
//
//                        return builder.build();
//                    default:
//                        //默认为0，若填写的builder_id并不存在，也使用默认。
//                        return super.getNotification(context, msg);
//                }
//            }
//        };
//        mPushAgent.setMessageHandler(messageHandler);
//
//        /**
//         * 该Handler是在BroadcastReceiver中被调用，故
//         * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
//         * 参考集成文档的1.6.2
//         * http://dev.umeng.com/push/android/integration#1_6_2
//         * */
//        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
//            @Override
//            public void dealWithCustomAction(Context context, UMessage msg) {
//                Toast.makeText(context, "dealWithCustomAction", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void handleMessage(Context context, UMessage uMessage) {
//                super.handleMessage(context, uMessage);
//                Log.i("tag", "UMessage====" + uMessage);
////                Toast.makeText(context, "handleMessage", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void openUrl(Context context, UMessage uMessage) {
//                super.openUrl(context, uMessage);
////                Toast.makeText(context, "openUrl", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void openActivity(Context context, UMessage uMessage) {
//                super.openActivity(context, uMessage);
////                Toast.makeText(context, "openActivity", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void launchApp(Context context, UMessage uMessage) {
//                super.launchApp(context, uMessage);
////                Toast.makeText(context, "launchApp", Toast.LENGTH_LONG).show();
//            }
//        };
//        //使用自定义的NotificationHandler，来结合友盟统计处理消息通知
//        //参考http://bbs.umeng.com/thread-11112-1-1.html
//        //CustomNotificationHandler notificationClickHandler = new CustomNotificationHandler();
//        mPushAgent.setNotificationClickHandler(notificationClickHandler);
    }

    public static synchronized MyApplication getInstance() {
        return instance;
    }

    public UserNoticeBean getUserNoticeBean() {
        if (null == userNoticeBean) {
            UserNoticeBean unb = SaveUtils.getShareObject(context, "UserNotice",
                    "usernotice", UserNoticeBean.class);
            return unb;
        }
        return userNoticeBean;
    }

    public void setUserNoticeBean(UserNoticeBean userNoticeBean) {
        this.userNoticeBean = userNoticeBean;
        if (userNoticeBean != null) {
            SaveUtils.putShareObject(context, "UserNotice", "usernotice",
                    userNoticeBean);
        }
    }

    public UserInfo getUserInfo() {
        if (null == userInfo) {
            UserInfo userInfo = SaveUtils.getShareObject(context, "USERINFO",
                    "userinfo", UserInfo.class);
            return userInfo;
        }
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
        if (userInfo != null) {
            SaveUtils.putShareObject(context, "USERINFO", "userinfo",
                    userInfo);
        }
    }

    public void setBooleanValue(String in_settingName, boolean in_val) {
        SharedPreferences sp = context.getSharedPreferences("is_login",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putBoolean(in_settingName, in_val);
        ed.commit();
        ed = null;
        sp = null;
    }

    public void setStringValue(String in_settingName, String in_val) {
        SharedPreferences sp = context.getSharedPreferences("userId",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString(in_settingName, in_val);
        ed.commit();
        ed = null;
        sp = null;
    }

    public String getStringValue(String in_settingName) {
        SharedPreferences sp = context.getSharedPreferences("userId",
                Context.MODE_PRIVATE);
        String ret = sp.getString(in_settingName, "");
        sp = null;
        return ret;
    }

    public Boolean getBoolValue(String in_settingName) {
        SharedPreferences sp = context.getSharedPreferences("is_login",
                Context.MODE_PRIVATE);
        boolean ret = sp.getBoolean(in_settingName, false);
        sp = null;
        return ret;
    }

    // 回收
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

}
