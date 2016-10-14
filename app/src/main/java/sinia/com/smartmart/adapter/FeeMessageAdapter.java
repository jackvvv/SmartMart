package sinia.com.smartmart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sinia.com.smartmart.R;
import sinia.com.smartmart.bean.FeeMessageListBean;
import sinia.com.smartmart.utils.ViewHolder;

import static sinia.com.smartmart.R.id.tv_money;
import static sinia.com.smartmart.R.id.tv_time;

/**
 * Created by 忧郁的眼神 on 2016/9/14.
 */
public class FeeMessageAdapter extends BaseAdapter {
    private Context context;
    private List<FeeMessageListBean.RescntBean.FeeMessageBean> list;

    public FeeMessageAdapter(Context context, List<FeeMessageListBean.RescntBean.FeeMessageBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_fee_msg, null);
        }
        TextView tv_money = ViewHolder.get(view, R.id.tv_money);
        TextView tv_type = ViewHolder.get(view, R.id.tv_type);
        TextView tv_time = ViewHolder.get(view, R.id.tv_time);
        TextView tv_name = ViewHolder.get(view, R.id.tv_name);
        TextView tv_address = ViewHolder.get(view, R.id.tv_address);
        TextView tv_company = ViewHolder.get(view, R.id.tv_company);
        TextView tv_house_no = ViewHolder.get(view, R.id.tv_house_no);

        String feeType = list.get(i).getType();
        tv_type.setText(list.get(i).getMessagetitle());
        tv_money.setText("欠费金额：" + list.get(i).getRatecost() + "元");
        tv_time.setText(list.get(i).getCreatetime());
        tv_name.setText(list.get(i).getUsername());
        tv_address.setText(list.get(i).getAddress());
        tv_company.setText(list.get(i).getCompanyname());
        tv_house_no.setText(list.get(i).getRateno());
        return view;
    }
}
