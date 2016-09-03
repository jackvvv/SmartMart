package sinia.com.smartmart.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.lidroid.xutils.BitmapUtils;

import sinia.com.smartmart.R;


public class BitmapUtilsHelp
{
    private static BitmapUtils bitmapUtilsForUser;

    private static BitmapUtils bitmapUtils;

    public static BitmapUtils getImage(Context context)
    {
        return getImage(context, -1);
    }

    public static BitmapUtils getImage(Context context, String filePath)
    {
        if (bitmapUtils == null)
        {
            bitmapUtils = new BitmapUtils(context, filePath);
        }
        bitmapUtils
                .configDefaultLoadFailedImage(R.drawable.ic_launcher);
        bitmapUtils.configDefaultLoadingImage(R.drawable.ic_launcher);
        return bitmapUtils;
    }
    
    public static BitmapUtils getImage(Context context, int defaultImage)
    {
        if (bitmapUtils == null)
        {
            bitmapUtils = new BitmapUtils(context,
                    ImageFileCache.getDirectory());
        }
        if (defaultImage == -1)
        {
            bitmapUtils
                    .configDefaultLoadFailedImage(R.drawable.ic_launcher);
            bitmapUtils
                    .configDefaultLoadingImage(R.drawable.ic_launcher);
        }
        else if (defaultImage == 0)
        {
        }
        else
        {
            bitmapUtils.configDefaultLoadFailedImage(defaultImage);
            bitmapUtils.configDefaultLoadingImage(defaultImage);
        }
        return bitmapUtils;
    }
    
    public static double getDiv(Bitmap bitmap)
    {
        double width = bitmap.getWidth();
        double height = bitmap.getHeight();
        if (width != 0)
        {
            return height / width;
        }
        return 0;

    }
}
