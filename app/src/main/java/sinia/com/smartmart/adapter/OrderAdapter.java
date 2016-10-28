package sinia.com.smartmart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import sinia.com.smartmart.R;
import sinia.com.smartmart.utils.Utility;
import sinia.com.smartmart.utils.ViewHolder;

/**
 * Created by 忧郁的眼神 on 2016/9/5.
 */
public class OrderAdapter extends BaseAdapter {

    private Context context;

    private OrderGoodsAdapter goodsAdapter;

    public OrderAdapter(Context context) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_order, null);
        }
        ImageView img_shop = ViewHolder.get(view, R.id.img_food);
        TextView tv_status = ViewHolder.get(view, R.id.tv_status);
        TextView tv_time = ViewHolder.get(view, R.id.tv_time);
        TextView tv_name = ViewHolder.get(view, R.id.tv_name);
        TextView tv_num = ViewHolder.get(view, R.id.tv_num);
        TextView tv_price = ViewHolder.get(view, R.id.tv_price);
        TextView btn = ViewHolder.get(view, R.id.btn);
        ListView lv_goods = ViewHolder.get(view, R.id.lv_goods);

        goodsAdapter = new OrderGoodsAdapter(context);
        lv_goods.setAdapter(goodsAdapter);
        Utility.setListViewHeightBasedOnChildren(lv_goods);
        return view;
    }
}
