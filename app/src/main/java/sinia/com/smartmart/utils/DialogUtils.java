package sinia.com.smartmart.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import sinia.com.smartmart.R;
import sinia.com.smartmart.city.CityPicker;


/**
 * Created by 忧郁的眼神 on 2016/7/26.
 */
public class DialogUtils {

    public static Dialog dialog;

    public static Dialog createFountionDevelopingTipsDialog(final Context context, String title) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialog_fountion_developing, null);
        dialog = new Dialog(context, R.style.DialogTopInStyle);
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.TOP); // 此处可以设置dialog显示的位置
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (display.getWidth()); // 设置高度
        dialog.getWindow().setAttributes(lp);
        dialog.setContentView(v, lp);
        final ImageView ok = (ImageView) dialog.findViewById(R.id.tv_ok);
        TextView tv_tips = (TextView) dialog.findViewById(R.id.tv_tips);
        tv_tips.setText(title);
        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        return dialog;
    }

    public static Dialog createRegisterFailedTipsDialog(final Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialog_register_failed, null);
        dialog = new Dialog(context, R.style.DialogTopInStyle);
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.TOP); // 此处可以设置dialog显示的位置
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (display.getWidth()); // 设置高度
        dialog.getWindow().setAttributes(lp);
        dialog.setContentView(v, lp);
        final TextView ok = (TextView) dialog.findViewById(R.id.tv_ok);
        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        return dialog;
    }

    public static Dialog createAddressDialog(Context context,
                                             final TextView province) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.activity_pickview1, null);
        dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        dialog.show();
        dialog.setContentView(v, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (display.getWidth()); // 设置宽度
        lp.height = display.getHeight() / 2;
        dialog.getWindow().setAttributes(lp);
        final CityPicker cityPicker = (CityPicker) v
                .findViewById(R.id.citypicker);
        TextView cancelTextView = (TextView) v.findViewById(R.id.cancel_layout);
        TextView sureTextView = (TextView) v.findViewById(R.id.sure_layout);
        cancelTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        sureTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (cityPicker.getCity().equals("县")
                        || cityPicker.getCity().equals("辖区")) {
                    if (cityPicker.getCouny().equals("县")
                            || cityPicker.getCouny().equals("辖区")) {
                        province.setText(cityPicker.getProvince());
                    } else {
                        province.setText(cityPicker.getProvince()
                                + cityPicker.getCity() + cityPicker.getCouny());
                    }
                } else {
                    province.setText(cityPicker.getProvince()
                            + cityPicker.getCity() + cityPicker.getCouny());
                }
                dialog.dismiss();
            }
        });
        return dialog;
    }

    public interface OnOkListener {
        public void onClick();
    }

    public interface OnCancelListener {
        public void onClick();
    }

}
