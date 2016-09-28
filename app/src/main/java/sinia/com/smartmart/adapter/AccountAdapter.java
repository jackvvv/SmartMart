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
 * Created by 忧郁的眼神 on 2016/8/5.
 */
public class AccountAdapter extends BaseAdapter {

    private Context context;


    public AccountAdapter(Context context) {
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_my_account, null);
        }
        TextView tv_money = ViewHolder.get(view, R.id.tv_money);
        TextView tv_status = ViewHolder.get(view, R.id.tv_status);
        TextView tv_fee_type = ViewHolder.get(view, R.id.tv_fee_type);
        TextView tv_code = ViewHolder.get(view, R.id.tv_code);
        TextView tv_time = ViewHolder.get(view, R.id.tv_time);
        ImageView img_fee = ViewHolder.get(view, R.id.img_fee);
        return view;
    }
}
