package sinia.com.smartmart.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import sinia.com.smartmart.R;
import sinia.com.smartmart.actionsheetdialog.ActionSheetDialog;
import sinia.com.smartmart.utils.ViewHolder;

/**
 * Created by 忧郁的眼神 on 2016/11/7 0007.
 */

public class BuildConfirmOrderGoodsAdapter extends BaseAdapter {
    private Context context;

    public BuildConfirmOrderGoodsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_confirm_order_build_goods, null);
        }
        TextView tv_type = ViewHolder.get(view, R.id.tv_type);
        ImageView img_good = ViewHolder.get(view, R.id.img_good);
        TextView tv_name = ViewHolder.get(view, R.id.tv_name);
        TextView tv_size = ViewHolder.get(view, R.id.tv_size);
        TextView tv_color = ViewHolder.get(view, R.id.tv_color);
        TextView tv_num = ViewHolder.get(view, R.id.tv_num);
        TextView tv_sigleprice = ViewHolder.get(view, R.id.tv_sigleprice);
        final TextView tv_sendTime = ViewHolder.get(view, R.id.tv_sendTime);
        tv_sendTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSendTimeDialog(context, tv_sendTime);
            }
        });
        return view;
    }

    private void createSendTimeDialog(Context context, final TextView tv_sendTime) {
        new ActionSheetDialog(context)
                .builder()
                .setCancelable(true)
                .setTitle("选择配送时间")
                .setCanceledOnTouchOutside(true)
                .addSheetItem("明天上午12:00之前", ActionSheetDialog.SheetItemColor.BLACK,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
//                                cancleType = "1";
                                tv_sendTime.setText("明天上午12:00之前");
                            }
                        })
                .addSheetItem("明天下午19:00之前", ActionSheetDialog.SheetItemColor.BLACK,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
//                                cancleType = "2";
                                tv_sendTime.setText("明天下午19:00之前");
                            }
                        })
                .addSheetItem("后天上午12:00之前", ActionSheetDialog.SheetItemColor.BLACK,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
//                                cancleType = "3";
                                tv_sendTime.setText("后天上午12:00之前");
                            }
                        })
                .addSheetItem("后天下午19:00之前", ActionSheetDialog.SheetItemColor.BLACK,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
//                                cancleType = "3";
                                tv_sendTime.setText("后天下午19:00之前");
                            }
                        }).show();
    }
}
