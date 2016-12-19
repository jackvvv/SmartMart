package sinia.com.smartmart.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import sinia.com.smartmart.R;
import sinia.com.smartmart.utils.ViewHolder;

import static android.R.id.list;

/**
 * Created by 忧郁的眼神 on 2016/8/5.
 */
public class BuildCategoryLeftAdapter extends BaseAdapter {

    private Context context;
    public int selectPosition;

    public BuildCategoryLeftAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 12;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_build_category_left, null);
        }
        TextView tv_name = ViewHolder.get(view, R.id.tv_name);
        if (selectPosition == i) {
            view.setBackgroundColor(context.getResources().getColor(R.color.textgray2));
        } else {
            view.setBackgroundColor(context.getResources().getColor(R.color.pickerview_bg_topbar));
        }
        return view;
    }
}
