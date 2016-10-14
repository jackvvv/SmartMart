package sinia.com.smartmart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import sinia.com.smartmart.R;
import sinia.com.smartmart.bean.MaintainListBean;
import sinia.com.smartmart.utils.ViewHolder;

/**
 * Created by 忧郁的眼神 on 2016/9/5.
 */
public class MaintainAdapter extends BaseAdapter {

    private Context context;

    private List<MaintainListBean.MaintainBean> list;

    public MaintainAdapter(Context context, List<MaintainListBean.MaintainBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_property_maintain, null);
        }
        TextView tv_question = ViewHolder.get(view, R.id.tv_question);
        TextView tv_content = ViewHolder.get(view, R.id.tv_content);
        TextView tv_time = ViewHolder.get(view, R.id.tv_time);
        TextView tv_status = ViewHolder.get(view, R.id.tv_status);
        String repairType = list.get(i).getRepairtype();
        String repairStatus = list.get(i).getRepairstatus();
        if ("1".equals(repairType)) {
            tv_question.setText("报修问题：公共部位报修");
        } else {
            tv_question.setText("报修问题：自用部位报修");
        }

        if ("1".equals(repairStatus)) {
            tv_status.setText("待处理");
        } else if ("2".equals(repairStatus)) {
            tv_status.setText("处理中");
        } else if ("3".equals(repairStatus)) {
            tv_status.setText("已解决");
        } else if ("4".equals(repairStatus)) {
            tv_status.setText("已删除");
        }

        tv_content.setText(list.get(i).getContent());
        tv_time.setText(list.get(i).getCreatetime());
        return view;
    }
}
