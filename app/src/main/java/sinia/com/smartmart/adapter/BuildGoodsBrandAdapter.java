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
import sinia.com.smartmart.bean.BuildBrandModel;
import sinia.com.smartmart.utils.ViewHolder;

/**
 * Created by 忧郁的眼神 on 2016/11/7 0007.
 */

public class BuildGoodsBrandAdapter extends BaseAdapter {
    private Context context;
    private List<BuildBrandModel> list;
    public int selectPosition;

    public BuildGoodsBrandAdapter(Context context, List<BuildBrandModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
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
            view = LayoutInflater.from(context).inflate(R.layout.item_build_goods_brand, null);
        }
        TextView tv_name = ViewHolder.get(view, R.id.tv_name);
        tv_name.setText(list.get(position).getBrand());

        if (position == selectPosition) {
            tv_name.setSelected(true);
            tv_name.setTextColor(Color.WHITE);
        } else {
            tv_name.setSelected(false);
            tv_name.setTextColor(Color.BLACK);
        }
        return view;
    }
}
