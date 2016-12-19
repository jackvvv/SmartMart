package sinia.com.smartmart.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import sinia.com.smartmart.R;
import sinia.com.smartmart.bean.BuildBrandModel;
import sinia.com.smartmart.utils.AppInfoUtil;
import sinia.com.smartmart.utils.ViewHolder;

import static sinia.com.smartmart.R.id.img;
import static sinia.com.smartmart.R.id.tv_name;

/**
 * Created by 忧郁的眼神 on 2016/11/7 0007.
 */

public class BuildGoodsBrandTopAdapter extends BaseAdapter {
    private Context context;
    private List<BuildBrandModel> list;
    public int selectPosition;

    public BuildGoodsBrandTopAdapter(Context context, List<BuildBrandModel> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_build_goods_brand_top, null);
        }
        LinearLayout ll = ViewHolder.get(view, R.id.ll);
        TextView tv_name = ViewHolder.get(view, R.id.tv_name);
        ImageView img_cursor = ViewHolder.get(view, R.id.img_cursor);
        tv_name.setText(list.get(position).getBrand());

        tv_name.setTag(1);
        int itemWidth = AppInfoUtil.getScreenWidth(context) / 5;
        LinearLayout.LayoutParams lpB = new LinearLayout.LayoutParams(itemWidth, LinearLayout.LayoutParams
                .WRAP_CONTENT);
        ll.setLayoutParams(lpB);

        if (position == selectPosition) {
            tv_name.setTextColor(Color.parseColor("#E88D23"));
            img_cursor.setVisibility(View.VISIBLE);
        } else {
            tv_name.setTextColor(Color.parseColor("#3a3a3a"));
            img_cursor.setVisibility(View.INVISIBLE);
        }
        return view;
    }
}
