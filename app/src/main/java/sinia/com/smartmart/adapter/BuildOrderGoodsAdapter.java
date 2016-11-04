package sinia.com.smartmart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import sinia.com.smartmart.R;
import sinia.com.smartmart.utils.ViewHolder;

/**
 * Created by 忧郁的眼神 on 2016/9/5.
 */
public class BuildOrderGoodsAdapter extends BaseAdapter {

    private Context context;
    private boolean isToGoodsDetail;

    public BuildOrderGoodsAdapter(Context context, boolean isToGoodsDetail) {
        this.context = context;
        this.isToGoodsDetail = isToGoodsDetail;
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
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isToGoodsDetail) {
                    //商品详情
                }
            }
        });
        return view;
    }
}
