package sinia.com.smartmart.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.TypedValue;

import java.lang.reflect.Field;

public class AppInfoUtil {
	public static int getStateHeight(Context context) {
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, sbar = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			sbar = context.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return sbar;
	}

	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static int intToDip(Context context, int value) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				value, context.getResources().getDisplayMetrics());
	}

	/**
	 *
	 * TODO:获取屏幕高度
	 *
	 * @param context
	 * @return 屏幕高度
	 */
	public static int getScreenHeight(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}

	/**
	 *
	 * TODO:获取屏幕宽度
	 *
	 * @param context
	 * @return 屏幕宽度
	 */
	public static int getScreenWidth(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 *
	 * TODO:获取IMEI
	 *
	 * @param context
	 * @return IMEI
	 */
	public static String getIMEI(Context context) {
		return ((TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
	}

	/**
	 *
	 * TODO:获取IMSI
	 *
	 * @param context
	 * @return IMSI
	 */
	public static String getIMSI(Context context) {
		return ((TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
	}

	/**
	 *
	 * TODO:获取设备型号
	 *
	 * @return 设备型号
	 */
	public static String getModel() {
		return Build.MODEL;
	}

	/**
	 *
	 * TODO:获取设备制�?�??
	 *
	 * @return 设备制�?�??
	 */
	public static String getManufacturer() {
		return Build.MANUFACTURER;
	}

	/**
	 *
	 * TODO:获取系统版本�??
	 *
	 * @return 系统版本�??
	 */
	public static String getSDKReleaseVersion() {
		return Build.VERSION.RELEASE;
	}

	/**
	 *
	 * TODO:获取系统SDK版本
	 *
	 * @return 系统SDK版本
	 */
	public static int getOsSdkVersion() {
		return Build.VERSION.SDK_INT;
	}

	/**
	 *
	 * TODO:获取程序包名
	 *
	 * @param context
	 * @return 程序包名
	 */
	public static String getPackageName(Context context) {
		return context.getPackageName();
	}

	/**
	 *
	 * TODO:获取程序版本名称
	 *
	 * @param context
	 * @return 程序版本名称
	 */
	public static String getVersionName(Context context) {
		// 获取packagemanager的实�??
		PackageManager packageManager = context.getPackageManager();
		// getPackageName()是你当前类的包名�??代表是获取版本信�??
		PackageInfo packInfo;
		try {
			packInfo = packageManager.getPackageInfo(context.getPackageName(),
					0);
			return packInfo.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	/**
	 *
	 * TODO:获取程序版本�??
	 *
	 * @param context
	 * @return 程序版本�??
	 */
	public static int getVersionCode(Context context) {
		// 获取packagemanager的实�??
		PackageManager packageManager = context.getPackageManager();
		// getPackageName()是你当前类的包名�??代表是获取版本信�??
		PackageInfo packInfo;
		try {
			packInfo = packageManager.getPackageInfo(context.getPackageName(),
					0);
			return packInfo.versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public static int getSDKversion() {
		return Build.VERSION.SDK_INT;
	}

	public static String getLocalMacAddress(Context context) {
		WifiManager wifi = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		if (info.getMacAddress() != null) {
			return info.getMacAddress();
		}
		return "";
	}

	/**
	 * 获取状�?�栏高度
	 *
	 * @return
	 */
	public static int getStatusBarHeight(Context context) {
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0;
		int statusBarHeight = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = context.getResources().getDimensionPixelSize(x);
			return statusBarHeight;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusBarHeight;
	}

	public static String getProductModel() {
		return Build.MODEL;
	}

	public static String getProductVERSION() {
		return Build.VERSION.RELEASE;
	}

	public static String getProduct() {
		return Build.MANUFACTURER;
	}

}