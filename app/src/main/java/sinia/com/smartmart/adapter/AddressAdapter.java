package sinia.com.smartmart.adapter;

import android.content.Context;
import android.content.Intent;
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
public class AddressAdapter extends BaseAdapter {

    private Context context;


    public AddressAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 3;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_address, null);
        }
        TextView tv_name = ViewHolder.get(view, R.id.tv_name);
        TextView tv_tel = ViewHolder.get(view, R.id.tv_tel);
        TextView tv_address = ViewHolder.get(view, R.id.tv_address);

//        tv_name.setText(list.get(i).getAddName());
//        tv_tel.setText(list.get(i).getAddTelephone());
//        tv_address.setText(list.get(i).getAddArea() + list.get(i).getAddAddress());
        return view;
    }
}
