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
import sinia.com.smartmart.utils.ViewHolder;

import static android.R.id.list;

/**
 * Created by 忧郁的眼神 on 2016/8/5.
 */
public class FoodSortAdapter extends BaseAdapter {

    private Context context;
    public int selectPosition;
    private String[] sortItem;

    public FoodSortAdapter(Context context, String[] sortItem) {
        this.context = context;
        this.sortItem = sortItem;
    }

    @Override
    public int getCount() {
        return sortItem.length;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_food_street_right, null);
        }
        TextView tv_name = ViewHolder.get(view, R.id.tv_name);
        ImageView img_select = ViewHolder.get(view, R.id.img_select);
        if (selectPosition == i) {
            tv_name.setSelected(true);
            img_select.setVisibility(View.VISIBLE);
        } else {
            tv_name.setSelected(false);
            img_select.setVisibility(View.INVISIBLE);
        }
        tv_name.setText(sortItem[i]);
        return view;
    }
}
