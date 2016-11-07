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
import sinia.com.smartmart.bean.VillageListBean;
import sinia.com.smartmart.utils.ViewHolder;

import static android.R.id.list;

/**
 * Created by 忧郁的眼神 on 2016/11/7 0007.
 */

public class BuildGoodsNormAdapter extends BaseAdapter {
    private Context context;
    public int selectPosition;

    public BuildGoodsNormAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 4;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_build_goods_norm, null);
        }
        TextView tv_name = ViewHolder.get(view, R.id.tv_name);

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
