package sinia.com.smartmart.utils;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.Set;

public class StringUtil {
    public static boolean isEmpty(String str) {
        if (null != str && !"".equals(str)) {
            return false;
        }
        return true;
    }

    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
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

    public static String formatePrice(double s) {
        if (s == 0) {
            return "0.00";
        } else {
            DecimalFormat df = new DecimalFormat("#.00");
            return df.format(s);
        }
    }

    public static int stringToInteger(String value) {
        if (isEmpty(value)) {
            return 0;
        } else {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                return 0;
            }

        }
    }

    public static float stringToFloat(String value) {
        if (isEmpty(value)) {
            return 0f;
        } else {
            try {
                return Float.parseFloat(value);
            } catch (NumberFormatException e) {
                return 0f;
            }

        }
    }

    public static double stringToDouble(String value) {
        if (isEmpty(value)) {
            return 0d;
        } else {
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException e) {
                return 0d;
            }

        }
    }

    public static String getCurYearAndMonth() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        Date curDate = new Date(System.currentTimeMillis());
        String date = df.format(curDate);
        return date;
    }

    public static String getCurYearAndMonth2() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());
        String date = df.format(curDate);
        return date;
    }

    public static boolean compare_time(String begin, String end, String arg) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date b = df.parse(begin);
            Date e = df.parse(end);
            Date a = df.parse(arg);
            if (b.getTime() > e.getTime())
                return false;
            if (a.getTime() > e.getTime())
                return false;
            if (a.getTime() < b.getTime())
                return false;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean compare_date(String begin, String end) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date b = df.parse(begin);
            Date e = df.parse(end);
            if (b.getTime() > e.getTime())
                return false;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }

    public static String getCurrentDate() {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = sdf1.format(new Date());
        return now;
    }

    public static String getDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat(
                "yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat(
                "yyyyMMdd");
        try {
            Date d = sdf1.parse(date.substring(0, 8));
            String c = sdf.format(d);
            return c;
        } catch (Exception e) {
            // Logger.e("StringUtil", e.getMessage());
        }
        return date;
    }

    public static long getDateMillis(String startTime) {
        DateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = new GregorianCalendar();
        Date date = null;
        try {
            date = sdf1.parse(startTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        Calendar c2 = new GregorianCalendar(c.get(Calendar.YEAR),
                c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH),
                c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),
                c.get(Calendar.SECOND));
        String v_strMonth = "";
        int v_intMonth = c2.get(Calendar.MONTH);
        if (v_intMonth < 10) {
            v_strMonth = v_strMonth + "0" + v_intMonth;
        } else {
            v_strMonth = v_strMonth + v_intMonth;
        }
        String v_strDay = "";
        int v_intDay = c2.get(Calendar.DAY_OF_MONTH);
        if (v_intDay < 10) {
            v_strDay = v_strDay + "0" + v_intDay;
        } else {
            v_strDay = v_strDay + v_intDay;
        }
        String v_strHour = "";
        int v_intHour = c2.get(Calendar.HOUR_OF_DAY);
        if (v_intHour < 10) {
            v_strHour = v_strHour + "0" + v_intHour;
        } else {
            v_strHour = v_strHour + v_intHour;
        }
        String v_strMinute = "";
        int v_intMINUTE = c2.get(Calendar.MINUTE);
        if (v_intMINUTE < 10) {
            v_strMinute = v_strMinute + "0" + v_intMINUTE;
        } else {
            v_strMinute = v_strMinute + v_intMINUTE;
        }
        String newDate = c2.get(Calendar.YEAR) + "-" + v_strMonth + "-"
                + v_strDay + " " + v_strHour + ":" + v_strMinute + ":" + "00";
        try {
            Date d1 = sdf1.parse(newDate);
            long t10 = d1.getTime();
            return t10;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long getDateMillis2(String startTime) {
        DateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = new GregorianCalendar();
        Date date = null;
        try {
            date = sdf1.parse(startTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        Calendar c2 = new GregorianCalendar(c.get(Calendar.YEAR),
                c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH),
                c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),
                c.get(Calendar.SECOND));
        String v_strMonth = "";
        int v_intMonth = c2.get(Calendar.MONTH);
        if (v_intMonth < 10) {
            v_strMonth = v_strMonth + "0" + v_intMonth;
        } else {
            v_strMonth = v_strMonth + v_intMonth;
        }
        String v_strDay = "";
        int v_intDay = c2.get(Calendar.DAY_OF_MONTH);
        if (v_intDay < 10) {
            v_strDay = v_strDay + "0" + v_intDay;
        } else {
            v_strDay = v_strDay + v_intDay;
        }
        String v_strHour = "";
        int v_intHour = c2.get(Calendar.HOUR_OF_DAY);
        if (v_intHour < 10) {
            v_strHour = v_strHour + "0" + v_intHour;
        } else {
            v_strHour = v_strHour + v_intHour;
        }
        String v_strMinute = "";
        int v_intMINUTE = c2.get(Calendar.MINUTE);
        if (v_intMINUTE < 10) {
            v_strMinute = v_strMinute + "0" + v_intMINUTE;
        } else {
            v_strMinute = v_strMinute + v_intMINUTE;
        }
        String newDate = c2.get(Calendar.YEAR) + "-" + v_strMonth + "-"
                + v_strDay + " " + v_strHour + ":" + v_strMinute + ":" + "00";
        try {
            Date d1 = sdf1.parse(newDate);
            long t10 = d1.getTime();
            return t10;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getDate1(String time) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            Date date = sdf2.parse(time);
            String t = sdf1.format(date);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return time;
    }

    public static String toBase64String(String s) {
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(
                        s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "utf-8");// UTF-16le:Not
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

    public static String decryptBASE64(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        try {
            byte[] encode = str.getBytes("UTF-8");
            return new String(Base64.encode(encode, 0, encode.length,
                    Base64.DEFAULT), "UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String BASE64(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        try {
            byte[] encode = str.getBytes("UTF-8");
            // base64 �����??
            return new String(Base64.decode(encode, 0, encode.length,
                    Base64.DEFAULT), "UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String AfterMD5(String s) {
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("MD5");

            messageDigest.reset();

            messageDigest.update(s.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            System.exit(-1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(
                        Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }

        return md5StrBuff.toString();
        /*
         * String md5Str = null; try { StringBuffer buf = new StringBuffer();
		 * 
		 * MessageDigest md = MessageDigest.getInstance("MD5");
		 * 
		 * md.update(s.getBytes());
		 * 
		 * byte b[] = md.digest(); int i;
		 * 
		 * for (int offset = 0; offset < b.length; offset++) {
		 * 
		 * i = b[offset];
		 * 
		 * if (i < 0) { i += 256; }
		 * 
		 * if (i < 16) { buf.append("0"); }
		 * 
		 * buf.append(Integer.toHexString(i));
		 * 
		 * }
		 * 
		 * // 32λ�ļ��� md5Str = buf.toString();
		 * 
		 * } catch (Exception e) { e.printStackTrace(); } return md5Str;
		 */
    }

    public static String AfterMD5(String s, String key) {
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance(key);

            messageDigest.reset();

            messageDigest.update(s.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            System.exit(-1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(
                        Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }

        return md5StrBuff.toString();
    }

    public static String set2string(Set<String> s) {
        String regularEx = "##";
        StringBuffer buffer = new StringBuffer();
        if (s != null | !s.isEmpty()) {
            for (Object obj : s) {
                buffer.append(obj.toString().trim());
                buffer.append(regularEx);
            }
        }
        return buffer.toString().trim();
    }

    public static LinkedHashSet<String> string2set(String s) {
        LinkedHashSet<String> set = new LinkedHashSet<String>();
        if (!"".equals(s) && null != s) {
            String regularEx = "##";
            String[] array = s.split(regularEx);
            for (String string : array) {
                set.add(string);
            }
        }
        return set;
    }

    public static String twoDecimalPoint(double d) {
        DecimalFormat dcmFmt = new DecimalFormat("0.00");
        return dcmFmt.format(d);
    }
}
