package sinia.com.smartmart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import sinia.com.smartmart.R;
import sinia.com.smartmart.utils.ViewHolder;

/**
 * Created by 忧郁的眼神 on 2016/8/5.
 */
public class CategoryRightAdapter extends BaseAdapter {

    private Context context;
    public int selectPosition;
//    private List<ClassfyListBean.BigClassBean> list;

    public CategoryRightAdapter(Context context
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
            view = LayoutInflater.from(context).inflate(R.layout.item_food_street_right, null);
        }
        TextView tv_name = ViewHolder.get(view, R.id.tv_name);
        ImageView img_select = ViewHolder.get(view, R.id.img_select);
        if (selectPosition == i) {
            tv_name.setSelected(true);
            img_select.setVisibility(View.VISIBLE);
//            view.setBackgroundColor(context.getResources().getColor(R.color.textwhite));
        } else {
            tv_name.setSelected(false);
//            view.setBackgroundColor(Color.parseColor("#f3f3f3"));
            img_select.setVisibility(View.INVISIBLE);
        }
        return view;
    }
}
