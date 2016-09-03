package sinia.com.smartmart.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.math.BigDecimal;

public class DataCleanManager {

	/**
	 * 计算缓存大小
	 * 
	 * @param context
	 * @return
	 */
	public static String getTotalCacheSize(Context context) {
		long cacheSize = getFolderSize(context.getCacheDir());
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			cacheSize += getFolderSize(context.getExternalCacheDir());
		}
		return getFormatSize(cacheSize);
	}

	/**
	 * 格式化单位
	 * 
	 * @param size
	 * @return
	 */
	public static String getFormatSize(double size) {
		double kiloByte = size / 1024;
		if (kiloByte < 1) {
			return size + "K";
		}

		double megaByte = kiloByte / 1024;
		if (megaByte < 1) {
			BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
			return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "K";
		}

		double gigaByte = megaByte / 1024;
		if (gigaByte < 1) {
			BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
			return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "M";
		}

		double teraBytes = gigaByte / 1024;
		if (teraBytes < 1) {
			BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
			return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "G";
		}
		BigDecimal result4 = new BigDecimal(teraBytes);
		return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
				+ "T";
	}

	/**
	 * 获取文件
	 * 
	 * @param cacheDir
	 * @return
	 */
	private static long getFolderSize(File cacheDir) {
		long size = 0;
		File[] fileList = cacheDir.listFiles();
		for (int i = 0; i < fileList.length; i++) {
			// 如果下面还有文件
			if (fileList[i].isDirectory()) {
				size = size + getFolderSize(fileList[i]);
			} else {
				size = size + fileList[i].length();
			}
		}
		return size;
	}

	/**
	 * 清除所有缓存
	 * 
	 * @param context
	 */
	public static void clearAllCache(Context context) {
		deleteDir(context.getCacheDir());
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			deleteDir(context.getExternalCacheDir());
		}
	}

	private static boolean deleteDir(File cacheDir) {
		if (cacheDir != null && cacheDir.isDirectory()) {
			String[] child = cacheDir.list();
			for (int i = 0; i < child.length; i++) {
				boolean success = deleteDir(new File(cacheDir, child[i]));
				if (!success) {
					return false;
				}
			}
		}
		return cacheDir.delete();
	}
}
