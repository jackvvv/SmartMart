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

/**
 * Created by 忧郁的眼神 on 2016/9/5.
 */
public class AreaAdapter extends BaseAdapter {

    private Context context;

    private List<VillageListBean.VillageBean> list;

    public AreaAdapter(Context context, List<VillageListBean.VillageBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
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
            view = LayoutInflater.from(context).inflate(R.layout.item_select_area, null);
        }
        TextView tv_name = ViewHolder.get(view, R.id.tv_name);
        tv_name.setText(list.get(i).getVillagename());
        return view;
    }
}
