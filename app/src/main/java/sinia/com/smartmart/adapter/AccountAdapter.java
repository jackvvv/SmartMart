package sinia.com.smartmart.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sinia.com.smartmart.R;
import sinia.com.smartmart.activity.AccountDetailActivity;
import sinia.com.smartmart.bean.BillListBean;
import sinia.com.smartmart.utils.ViewHolder;

import static android.R.id.list;

/**
 * Created by 忧郁的眼神 on 2016/8/5.
 */
public class AccountAdapter extends BaseAdapter {

    private Context context;
    private List<BillListBean.RescntBean.BillBean> list;

    public AccountAdapter(Context context, List<BillListBean.RescntBean.BillBean> list) {
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_my_account, null);
        }
        TextView tv_money = ViewHolder.get(view, R.id.tv_money);
        TextView tv_status = ViewHolder.get(view, R.id.tv_status);
        TextView tv_fee_type = ViewHolder.get(view, R.id.tv_fee_type);
        TextView tv_code = ViewHolder.get(view, R.id.tv_code);
        TextView tv_time = ViewHolder.get(view, R.id.tv_time);
        ImageView img_fee = ViewHolder.get(view, R.id.img_fee);

        final String feeType = list.get(i).getBilltype();
        final String status = list.get(i).getBillstatus();
        if ("1".equals(feeType)) {
            img_fee.setImageResource(R.drawable.ic_water_samll);
            tv_fee_type.setText("水费-缴费号：");
            tv_code.setText(list.get(i).getRateno());
        }
        if ("2".equals(feeType)) {
            img_fee.setImageResource(R.drawable.ic_elec_small);
            tv_fee_type.setText("电费-缴费号：");
            tv_code.setText(list.get(i).getRateno());
        }
        if ("3".equals(feeType)) {
            img_fee.setImageResource(R.drawable.ic_gas_small);
            tv_fee_type.setText("煤气费-缴费号：");
            tv_code.setText(list.get(i).getRateno());
        }
        if ("4".equals(feeType)) {
            img_fee.setImageResource(R.drawable.ic_wuye_samll);
            tv_fee_type.setText("物业费-缴费号：");
            tv_code.setText(list.get(i).getRateno());
        }

        if ("1".equals(status)) {
            tv_status.setText("未支付");
        }
        if ("2".equals(status)) {
            tv_status.setText("已支付");
        }
        if ("3".equals(status)) {
            tv_status.setText("已删除");
        }

        tv_time.setText(list.get(i).getCreatetime());
        tv_money.setText("-" + list.get(i).getBillcost());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AccountDetailActivity.class);
                intent.putExtra("status", status);
                intent.putExtra("feeType", feeType);
                intent.putExtra("billid", list.get(i).getBillid());
                context.startActivity(intent);
            }
        });
        return view;
    }
}
