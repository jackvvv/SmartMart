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
public class CategoryLeftAdapter extends BaseAdapter {

    private Context context;
    public int selectPosition;
//    private List<ClassfyListBean.BigClassBean> list;

    public CategoryLeftAdapter(Context context
//            , List<ClassfyListBean.BigClassBean> list
    ) {
        this.context = context;
//        this.list = list;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_food_street_left, null);
        }
        TextView tv_name = ViewHolder.get(view, R.id.tv_name);
        if (selectPosition == i) {
            tv_name.setSelected(true);
            view.setBackgroundColor(context.getResources().getColor(R.color.textwhite));
        } else {
            tv_name.setSelected(false);
            view.setBackgroundColor(Color.parseColor("#f3f3f3"));
        }
        return view;
    }
}
