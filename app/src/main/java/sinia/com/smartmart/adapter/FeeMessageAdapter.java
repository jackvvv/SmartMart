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
public class FeeMessageAdapter extends BaseAdapter {
    private Context context;


    public FeeMessageAdapter(Context context) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_fee_msg, null);
        }
        TextView tv_money = ViewHolder.get(view, R.id.tv_money);
        TextView tv_status = ViewHolder.get(view, R.id.tv_status);
        TextView tv_time = ViewHolder.get(view, R.id.tv_time);
        TextView tv_name = ViewHolder.get(view, R.id.tv_name);
        TextView tv_address = ViewHolder.get(view, R.id.tv_address);
        TextView tv_company = ViewHolder.get(view, R.id.tv_company);
        TextView tv_house_no = ViewHolder.get(view, R.id.tv_house_no);
        return view;
    }
}
