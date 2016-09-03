package sinia.com.smartmart.utils;

import android.annotation.SuppressLint;
import android.content.Context;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

	private Context context;

	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	public static String hideIdCard(String s) {
		if (s.length() == 18) {
			return s.substring(0, 3) + "********" + s.substring(14, s.length());
		} else if (s.length() == 15) {
			return s.substring(0, 3) + "********" + s.substring(11, s.length());
		}
		return "";
	}

	public static String encodePhone(String tel) {
		String ss = tel
				.substring(0, tel.length() - (tel.substring(3)).length())
				+ "****" + tel.substring(7);
		return ss;
	}

	/**
	 * 密码强度
	 */
	public static String checkPassword(String passwordStr) {
		String str = "^\\d+$"; // 不超�?20位的数字组合
		String str1 = "(?i)^((\\d+[\\da-z]*[a-z]+)|([a-z]+[\\da-z]*\\d+)|([a-z]+[\\da-z]*[a-z]*)|(\\d+[\\da-z]*\\d*))$";
		String str2 = "^[a-zA-Z]+$"; // 由字母不超过20�?
		if (passwordStr.matches(str)) {
			// �?
			return "�?";
		}
		if (passwordStr.matches(str2)) {
			// �?
			return "�?";
		}
		if (passwordStr.matches(str1)) {
			// �?
			return "�?";
		}
		return "�?";

	}

	public static boolean isPhoneNumberValid(String phoneNumber) {
		boolean isValid = false;

		if (phoneNumber.length() != 11) {
			return isValid;

		} else {
			isValid = true;
		}

		return isValid;
	}

	public static boolean isMobileNumber(String mobiles) {
		Pattern p = Pattern
				.compile("^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	public static Boolean isNotEmpty(String contant) {
		if (contant == null || contant.equals("")) {
			return false;
		}
		return true;
	}

	public static boolean emailFormat(String email) {
		boolean tag = true;
		final String pattern1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		final Pattern pattern = Pattern.compile(pattern1);
		final Matcher mat = pattern.matcher(email);
		if (!mat.find()) {
			tag = false;
		}
		return tag;
	}

	public static String md5(String password) {
		byte[] hash;
		try {
			hash = MessageDigest.getInstance("MD5").digest(
					password.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Huh, MD5 should be supported?", e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Huh, UTF-8 should be supported?", e);
		}

		StringBuilder hex = new StringBuilder(hash.length * 2);
		for (byte b : hash) {
			if ((b & 0xFF) < 0x10)
				hex.append("0");
			hex.append(Integer.toHexString(b & 0xFF));
		}
		return hex.toString();
	}

	public static String pudgeScore(int score) {
		int level = 0;
		int num = 0;
		if (score < 2191) {
			level = 1;
		} else if (score < 6571) {
			level = 2;
		} else {
			level = 3;
		}

		switch (level) {
		case 1:
			if (score <= 438) {
				num = 1;
			} else if (score <= 877) {
				num = 2;
			} else if (score <= 1315) {
				num = 3;
			} else if (score <= 1753) {
				num = 4;
			} else {
				num = 5;
			}

			break;
		case 2:

			if (score <= 3067) {
				num = 1;
			} else if (score <= 3943) {
				num = 2;
			} else if (score <= 4819) {
				num = 3;
			} else if (score <= 5695) {
				num = 4;
			} else {
				num = 5;
			}
			break;
		case 3:

			if (score <= 7885) {
				num = 1;
			} else if (score <= 9199) {
				num = 2;
			} else if (score <= 10513) {
				num = 3;
			} else if (score <= 11827) {
				num = 4;
			} else {
				num = 5;
			}
			break;

		default:
			break;
		}
		return level + "_" + num;
	}

	public static String getStartValue(String key) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("7:00", "1");
		map.put("8:00", "2");
		map.put("9:00", "3");
		map.put("10:00", "4");
		map.put("11:00", "5");
		map.put("12:00", "6");
		map.put("13:00", "7");
		map.put("14:00", "8");
		map.put("15:00", "9");
		map.put("16:00", "10");
		map.put("17:00", "11");
		map.put("18:00", "12");
		return map.get(key);
	}

	public static String getEndValue(String key) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("10:00", "1");
		map.put("11:00", "2");
		map.put("12:00", "3");
		map.put("13:00", "4");
		map.put("14:00", "5");
		map.put("15:00", "6");
		map.put("16:00", "7");
		map.put("17:00", "8");
		map.put("18:00", "9");
		map.put("19:00", "10");
		map.put("20:00", "11");
		return map.get(key);
	}

	@SuppressLint("SimpleDateFormat")
	public static String getCurYearAndMonthDay() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date curDate = new Date(System.currentTimeMillis());
		String date = df.format(curDate);
		return date;
	}

}
