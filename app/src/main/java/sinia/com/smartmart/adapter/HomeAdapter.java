package sinia.com.smartmart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import sinia.com.smartmart.R;
import sinia.com.smartmart.utils.ViewHolder;

/**
 * Created by 忧郁的眼神 on 2016/9/5.
 */
public class HomeAdapter extends BaseAdapter {

    private Context context;

    public HomeAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 5;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_home_list, null);
        }
        ImageView img_shop = ViewHolder.get(view, R.id.img_shop);
        ImageView img_quan = ViewHolder.get(view, R.id.img_quan);
        RatingBar ratingBar = ViewHolder.get(view, R.id.ratingBar);
        TextView tv_sall = ViewHolder.get(view, R.id.tv_sall);
        TextView tv_name = ViewHolder.get(view, R.id.tv_name);
        TextView tv_time = ViewHolder.get(view, R.id.tv_time);
        TextView tv_distance = ViewHolder.get(view, R.id.tv_distance);
        TextView tv_startMoney = ViewHolder.get(view, R.id.tv_startMoney);
        TextView tv_sendMoney = ViewHolder.get(view, R.id.tv_sendMoney);
        return view;
    }
}
