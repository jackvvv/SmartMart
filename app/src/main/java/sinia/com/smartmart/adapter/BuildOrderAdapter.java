package sinia.com.smartmart.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import sinia.com.smartmart.R;
import sinia.com.smartmart.activity.BuildCommentActivity;
import sinia.com.smartmart.bean.FoodModel;
import sinia.com.smartmart.mycallback.BuildOrderCallBack;
import sinia.com.smartmart.mycallback.MyRecyclerViewClickListener;
import sinia.com.smartmart.utils.Utility;

import static android.R.attr.type;

/**
 * Created by 忧郁的眼神 on 2016/11/3 0003.
 */

public class BuildOrderAdapter extends RecyclerView.Adapter<BuildOrderAdapter.BuildOrderViewHolder> {

    private Context context;
    private String orderType;
    private BuildOrderGoodsAdapter goodsAdapter;
    private MyRecyclerViewClickListener clickListener;
    private BuildOrderCallBack buildOrderCallBack;

    public BuildOrderAdapter(Context context, String orderType) {
        this.context = context;
        this.orderType = orderType;
    }

    public void setBuildOrderCallBack(BuildOrderCallBack buildOrderCallBack) {
        this.buildOrderCallBack = buildOrderCallBack;
    }

    public void setClickListener(MyRecyclerViewClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public BuildOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BuildOrderViewHolder buildOrderViewHolder = new BuildOrderViewHolder(LayoutInflater.from(context).inflate(R
                .layout.item_build_order, null), clickListener);
        return buildOrderViewHolder;
    }

    @Override
    public void onBindViewHolder(BuildOrderViewHolder holder, final int position) {

        if (orderType.equals("2") || orderType.equals("3")) {
            // 待收货，待审核 显示：取消订单，订单详情
            holder.img_delete.setVisibility(View.INVISIBLE);
            holder.tv_comment.setVisibility(View.INVISIBLE);
            holder.tv_cancle.setText("取消订单");
            holder.tv_cancle.setTextColor(Color.parseColor("#E88D23"));
            holder.tv_cancle.setBackgroundResource(R.drawable.btn_cancle_order);
        } else if (orderType.equals("4")) {
            //评价
            holder.img_delete.setVisibility(View.VISIBLE);
            holder.tv_comment.setVisibility(View.VISIBLE);
            holder.tv_cancle.setText("再次购买");
            holder.tv_cancle.setTextColor(Color.parseColor("#3a3a3a"));
            holder.tv_cancle.setBackgroundResource(R.drawable.btn_order_detail);
        }

        holder.tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderType.equals("2") || orderType.equals("3")) {
                    buildOrderCallBack.cancleOrder(position);
                }
            }
        });
        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemRemoved(0);
            }
        });
        holder.tv_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BuildCommentActivity.class);
                context.startActivity(intent);
            }
        });
        goodsAdapter = new BuildOrderGoodsAdapter(context, false);
        holder.listView.setAdapter(goodsAdapter);
        Utility.setListViewHeightBasedOnChildren(holder.listView);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class BuildOrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_order_no, tv_status, tv_num, tv_money, tv_coupons, tv_time, tv_comment, tv_cancle, tv_detail;
        ImageView img_delete;
        ListView listView;
        private MyRecyclerViewClickListener clickListener;

        public BuildOrderViewHolder(View itemView, MyRecyclerViewClickListener clickListener) {
            super(itemView);
            this.clickListener = clickListener;
            tv_order_no = (TextView) itemView.findViewById(R.id.tv_order_no);
            tv_status = (TextView) itemView.findViewById(R.id.tv_status);
            tv_num = (TextView) itemView.findViewById(R.id.tv_num);
            tv_money = (TextView) itemView.findViewById(R.id.tv_money);
            tv_coupons = (TextView) itemView.findViewById(R.id.tv_coupons);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_comment = (TextView) itemView.findViewById(R.id.tv_comment);
            tv_cancle = (TextView) itemView.findViewById(R.id.tv_cancle);
            tv_detail = (TextView) itemView.findViewById(R.id.tv_detail);
            img_delete = (ImageView) itemView.findViewById(R.id.img_delete);
            listView = (ListView) itemView.findViewById(R.id.listView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onitemClick(v, getPosition());
        }
    }
}
