package sinia.com.smartmart.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sinia.com.smartmart.R;
import sinia.com.smartmart.activity.FoodDetailActivity;
import sinia.com.smartmart.bean.FoodModel;
import sinia.com.smartmart.mycallback.ModifyCountAndPriceInterface;

import static sinia.com.smartmart.R.id.img_add;
import static sinia.com.smartmart.R.id.img_jian;
import static sinia.com.smartmart.R.id.tv_num;

/**
 * Created by 忧郁的眼神 on 2016/10/27 0027.
 */

public class FoodMenuRightAdapter2 extends RecyclerView.Adapter<FoodMenuRightAdapter2.MyViewHolder> {

    private Context context;
    private List<FoodModel> list;
    private ModifyCountAndPriceInterface modifyCountAndPriceInterface;

    public FoodMenuRightAdapter2(Context context, List<FoodModel> list) {
        this.context = context;
        this.list = list;
    }

    public void setModifyCountAndPriceInterface(ModifyCountAndPriceInterface modifyCountAndPriceInterface) {
        this.modifyCountAndPriceInterface = modifyCountAndPriceInterface;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout
                .item_food_menu_right, parent, false));
        return myViewHolder;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv_name.setText(list.get(position).getName());
        holder.tv_price.setText("¥" + list.get(position).getPrice());
        holder.tv_num.setText(list.get(position).getCount() + "");

        if (0 == list.get(position).getCount()) {
            holder.img_jian.setVisibility(View.INVISIBLE);
            holder.tv_num.setVisibility(View.INVISIBLE);
            holder.img_add.setImageResource(R.drawable.ic_addto_cart);
        }

        holder.img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyCountAndPriceInterface.doIncrease(position, holder.img_add, holder.img_jian,
                        holder.tv_num);
                listener.callBackImage(holder.img_add);
            }
        });
        holder.img_jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyCountAndPriceInterface.doDecrease(position, holder.img_add, holder.img_jian,
                        holder.tv_num);
            }
        });
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name, tv_sall, tv_price, tv_num;
        ImageView img_food, img_jian, img_add;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_sall = (TextView) itemView.findViewById(R.id.tv_sall);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_num = (TextView) itemView.findViewById(R.id.tv_num);
            img_food = (ImageView) itemView.findViewById(R.id.img_food);
            img_jian = (ImageView) itemView.findViewById(R.id.img_jian);
            img_add = (ImageView) itemView.findViewById(R.id.img_add);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, FoodDetailActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }

    public CallBackListener listener;

    public void setListener(CallBackListener listener) {
        this.listener = listener;
    }

    public interface CallBackListener {
        void callBackImage(ImageView image);
    }
}
