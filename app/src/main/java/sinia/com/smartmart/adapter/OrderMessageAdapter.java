package sinia.com.smartmart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import sinia.com.smartmart.R;
import sinia.com.smartmart.utils.ViewHolder;

/**
 * Created by 忧郁的眼神 on 2016/9/14.
 */
public class OrderMessageAdapter extends BaseAdapter {
    private Context context;


    public OrderMessageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_order_msg, null);
        }
        TextView tv_goods_name = ViewHolder.get(view, R.id.tv_goods_name);
        TextView tv_status = ViewHolder.get(view, R.id.tv_status);
        TextView tv_status_content = ViewHolder.get(view, R.id.tv_status_content);
        TextView tv_order_no = ViewHolder.get(view, R.id.tv_order_no);
        ImageView img_goods = ViewHolder.get(view, R.id.img_goods);
        return view;
    }
}
