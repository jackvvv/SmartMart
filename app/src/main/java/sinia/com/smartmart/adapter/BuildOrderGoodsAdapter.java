package sinia.com.smartmart.adapter;

import android.content.Context;
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
 * Created by 忧郁的眼神 on 2016/9/5.
 */
public class BuildOrderGoodsAdapter extends BaseAdapter {

    private Context context;

    public BuildOrderGoodsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 2;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_build_order_goods, null);
        }
        TextView tv_goods = ViewHolder.get(view, R.id.tv_goods);
        TextView tv_money_unit = ViewHolder.get(view, R.id.tv_money_unit);
        return view;
    }
}
