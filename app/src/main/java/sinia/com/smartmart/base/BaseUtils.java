package sinia.com.smartmart.base;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.lang.reflect.Field;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xujin on 2016/1/13.
 */
public class BaseUtils {


    public static String getStrVal(Object o) {
        String result = (o != null) ? o.toString() : "";
        return result;
    }

    public static String getStrDefaultVal(Object o, String defaultVal) {
        String result = (o != null) ? o.toString() : defaultVal;
        return result;
    }

    public static int getIntVal(Object o, int defaultVal) {
        String resultStr = (o != null) ? o.toString() : "";
        int result = RegexUtils.checkDigit(resultStr) ? Integer
                .parseInt(resultStr) : defaultVal;
        return result;
    }

    public static long getLongVal(Object o, long defaultVal) {
        String resultStr = (o != null) ? o.toString() : "";
        Long result = RegexUtils.checkDigit(resultStr) ? Long
                .parseLong(resultStr) : defaultVal;
        return result;
    }

    public static Float getFloatVal(Object o, Float defaultVal) {
        String resultStr = (o != null) ? o.toString() : "";
        Float result = RegexUtils.checkDecimals(resultStr) ? Float
                .parseFloat(resultStr) : defaultVal;
        return result;
    }

    public static Double getDoubleVal(Object o, Double defaultVal) {
        String resultStr = (o != null) ? o.toString() : "";
        Double result = RegexUtils.checkDecimals(resultStr) ? Double
                .parseDouble(resultStr) : defaultVal;
        return result;
    }

    /**
     * 根据文件名生成由md5生成的新的文件名
     *
     * @param str
     * @return
     */
    public static String fileNameMd5(String str) {

        if (str.lastIndexOf(".") > -1) {
            str = DigestUtils.md5(str.substring(0, str.lastIndexOf(".")))
                    + str.substring(str.lastIndexOf("."), str.length());
        }
        return str;
    }

    public static String fileNameMd5(String str, String ext) {

        str = DigestUtils.md5(str) + ext;

        return str;
    }

    public static String numFormat(int len, int num) {
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumIntegerDigits(len);
        formatter.setGroupingUsed(false);
        String numStr = formatter.format(num);
        return numStr;
    }

    public static boolean detectNet(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        if (networkinfo == null || !networkinfo.isAvailable()) {
            return false;
        }
        return true;
    }

    // 清除html格式
    public static String clearHtml(String content) {

        String strHtml = content;// HTML文本代码
        String strClear = strHtml
                .replaceAll(".*?<body.*?>(.*?)<\\/body>", "$1"); // 读出body内里所有内容
        strClear = strClear.replaceAll("</?[^/?(br)|(p)|(img)][^><]*>", "");// 保留br标签和p标签
        return strHtml;
    }

    @SuppressWarnings("rawtypes")
    public static HashMap<String, String> objToHash(Object obj) {

        HashMap<String, String> hashMap = new HashMap<String, String>();
        try {
            Class clazz = obj.getClass();
            List<Class> clazzs = new ArrayList<Class>();

            while (!clazz.equals(Object.class)) {
                clazzs.add(clazz);
                clazz = clazz.getSuperclass();
            }

            for (Class iClazz : clazzs) {
                Field[] fields = iClazz.getDeclaredFields();
                for (Field field : fields) {
                    Object objVal = null;
                    field.setAccessible(true);
                    objVal = field.get(obj);
                    hashMap.put(field.getName(), getStrVal(objVal));
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        return hashMap;
    }

    public static String getNetworkType(Context context) {
        String strNetworkType = "";
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return strNetworkType;
        }
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                strNetworkType = "WIFI";
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                String _strSubTypeName = networkInfo.getSubtypeName();
                // TD-SCDMA networkType is 17
                int networkType = networkInfo.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN: // api<8 : replace by
                        // 11
                        strNetworkType = "2G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: // api<9 : replace by
                        // 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD: // api<11 : replace by
                        // 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP: // api<13 : replace by
                        // 15
                        strNetworkType = "3G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE: // api<11 : replace by
                        // 13
                        strNetworkType = "4G";
                        break;
                    default:
                        // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                        if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA")
                                || _strSubTypeName.equalsIgnoreCase("WCDMA")
                                || _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                            strNetworkType = "3G";
                        } else {
                            strNetworkType = _strSubTypeName;
                        }

                        break;
                }

            }
        }

        return strNetworkType;
    }

}
