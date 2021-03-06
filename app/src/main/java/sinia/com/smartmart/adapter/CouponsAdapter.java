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
public class CouponsAdapter extends BaseAdapter {
    private Context context;


    public CouponsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 8;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_coupons, null);
        }
        TextView tv_price = ViewHolder.get(view, R.id.tv_price);
        TextView tv_use = ViewHolder.get(view, R.id.tv_use);
        TextView tv_title = ViewHolder.get(view, R.id.tv_title);
        TextView tv_usedfor = ViewHolder.get(view, R.id.tv_usedfor);
        TextView tv_time = ViewHolder.get(view, R.id.tv_time);
        return view;
    }
}
