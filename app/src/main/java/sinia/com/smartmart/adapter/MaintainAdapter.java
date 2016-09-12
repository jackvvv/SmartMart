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
public class MaintainAdapter extends BaseAdapter {

    private Context context;

    public MaintainAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 3;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_property_maintain, null);
        }
        TextView tv_question = ViewHolder.get(view, R.id.tv_question);
        TextView tv_content = ViewHolder.get(view, R.id.tv_content);
        TextView tv_time = ViewHolder.get(view, R.id.tv_time);
        TextView tv_status = ViewHolder.get(view, R.id.tv_status);
        return view;
    }
}
