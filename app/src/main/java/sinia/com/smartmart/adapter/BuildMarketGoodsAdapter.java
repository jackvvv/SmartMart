package sinia.com.smartmart.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import sinia.com.smartmart.R;
import sinia.com.smartmart.activity.BuildGoodsDetailActivity;
import sinia.com.smartmart.bean.BuildBrandModel;
import sinia.com.smartmart.mycallback.BuildMarketListAddCartCallBack;
import sinia.com.smartmart.utils.AppInfoUtil;
import sinia.com.smartmart.utils.ViewHolder;

import static android.R.id.list;

/**
 * Created by 忧郁的眼神 on 2016/11/7 0007.
 */

public class BuildMarketGoodsAdapter extends BaseAdapter {
    private Context context;
    private BuildMarketListAddCartCallBack buildMarketListAddCartCallBack;

    public BuildMarketGoodsAdapter(Context context) {
        this.context = context;
    }

    public void setBuildMarketListAddCartCallBack(BuildMarketListAddCartCallBack buildMarketListAddCartCallBack) {
        this.buildMarketListAddCartCallBack = buildMarketListAddCartCallBack;
    }

    @Override
    public int getCount() {
        return 23;
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
    public View getView(final int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_build_goods_grid, null);
        }
        TextView tv_name = ViewHolder.get(view, R.id.tv_name);
        TextView tv_price = ViewHolder.get(view, R.id.tv_price);
        ImageView img = ViewHolder.get(view, R.id.img);
        ImageView img_cart = ViewHolder.get(view, R.id.img_cart);

        int itemWidth = AppInfoUtil.getScreenWidth(context) / 2 - AppInfoUtil.dip2px(context, 10);
        int itemHeight = AppInfoUtil.getScreenHeight(context) / 3;
        // 大图片LayoutParams
        LinearLayout.LayoutParams lpB = new LinearLayout.LayoutParams(itemWidth, itemWidth);
        img.setLayoutParams(lpB);
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildMarketListAddCartCallBack.addToCart(position);
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BuildGoodsDetailActivity.class);
                context.startActivity(intent);
            }
        });
        return view;
    }
}
