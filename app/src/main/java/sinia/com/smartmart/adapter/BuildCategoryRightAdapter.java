package sinia.com.smartmart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;


import sinia.com.smartmart.R;
import sinia.com.smartmart.utils.ViewHolder;

import static android.R.id.list;

/**
 * Created by 忧郁的眼神 on 2016/8/5.
 */
public class BuildCategoryRightAdapter extends BaseAdapter {

    private Context context;

    public BuildCategoryRightAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 23;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_build_category_right, null);
        }
        TextView tv_name = ViewHolder.get(view, R.id.tv_name);
        ImageView img = ViewHolder.get(view, R.id.img);
//        Glide.with(context).load(list.get(i).getSmallImage()).placeholder(R.drawable.ic_launcher).crossFade().into(img);
//        tv_name.setText(list.get(i).getSmallTypeName());
        return view;
    }
}
