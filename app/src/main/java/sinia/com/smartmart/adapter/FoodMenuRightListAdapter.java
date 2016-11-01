package sinia.com.smartmart.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.List;

import sinia.com.smartmart.R;
import sinia.com.smartmart.bean.FoodModel;
import sinia.com.smartmart.mycallback.AppBarScrollListener;
import sinia.com.smartmart.mycallback.ModifyCountAndPriceInterface;

import static sinia.com.smartmart.R.id.img_add;
import static sinia.com.smartmart.R.id.img_food;
import static sinia.com.smartmart.R.id.img_jian;
import static sinia.com.smartmart.R.id.tv_name;
import static sinia.com.smartmart.R.id.tv_num;
import static sinia.com.smartmart.R.id.tv_price;
import static sinia.com.smartmart.R.id.tv_sall;

/**
 * Created by 忧郁的眼神 on 2016/10/28 0028.
 */

public class FoodMenuRightListAdapter extends android.widget.BaseAdapter {

    private Context context;
    private List<FoodModel> list;
    private ModifyCountAndPriceInterface modifyCountAndPriceInterface;
    private AppBarScrollListener appBarScrollListener;

    public FoodMenuRightListAdapter(Context context, List<FoodModel> list) {
        this.context = context;
        this.list = list;
    }

    public void setModifyCountAndPriceInterface(ModifyCountAndPriceInterface modifyCountAndPriceInterface) {
        this.modifyCountAndPriceInterface = modifyCountAndPriceInterface;
    }

    public void setAppBarScrollListener(AppBarScrollListener appBarScrollListener) {
        this.appBarScrollListener = appBarScrollListener;
    }

    @Override
    public int getCount() {
        return list.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_food_menu_right, null);
        }
        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        TextView tv_sall = (TextView) convertView.findViewById(R.id.tv_sall);
        final TextView tv_price = (TextView) convertView.findViewById(R.id.tv_price);
        final TextView tv_num = (TextView) convertView.findViewById(R.id.tv_num);
        final ImageView img_food = (ImageView) convertView.findViewById(R.id.img_food);
        final ImageView img_jian = (ImageView) convertView.findViewById(R.id.img_jian);
        final ImageView img_add = (ImageView) convertView.findViewById(R.id.img_add);

        if (list.get(position).getCount() == 0) {
            img_add.setImageResource(R.drawable.ic_addto_cart);
            tv_num.setVisibility(View.INVISIBLE);
            img_jian.setVisibility(View.INVISIBLE);
        }

        tv_name.setText(list.get(position).getName());
        tv_price.setText("¥" + list.get(position).getPrice());
        tv_num.setText(list.get(position).getCount() + "");

        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyCountAndPriceInterface.doIncrease(1, position, img_add, img_jian,
                        tv_num, tv_price);
                listener.callBackImage(img_add);
            }
        });
        img_jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyCountAndPriceInterface.doDecrease(1, position, img_add, img_jian,
                        tv_num, tv_price);
            }
        });
        return convertView;
    }

    public FoodMenuRightListAdapter.CallBackListener listener;

    public void setListener(FoodMenuRightListAdapter.CallBackListener listener) {
        this.listener = listener;
    }

    public interface CallBackListener {
        void callBackImage(ImageView image);
    }
}
