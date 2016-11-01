package sinia.com.smartmart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import sinia.com.smartmart.R;
import sinia.com.smartmart.bean.FoodModel;
import sinia.com.smartmart.mycallback.ModifyCountAndPriceInterface;
import sinia.com.smartmart.utils.ViewHolder;

import static android.R.id.list;
import static sinia.com.smartmart.R.id.img_add;
import static sinia.com.smartmart.R.id.img_jian;
import static sinia.com.smartmart.R.id.img_select;

/**
 * Created by 忧郁的眼神 on 2016/8/5.
 */
public class FoodMenuRightAdapter extends BaseQuickAdapter<FoodModel> {

    private Context context;
    private List<FoodModel> data;
    private ModifyCountAndPriceInterface modifyCountAndPriceInterface;

    public void setModifyCountAndPriceInterface(ModifyCountAndPriceInterface modifyCountAndPriceInterface) {
        this.modifyCountAndPriceInterface = modifyCountAndPriceInterface;
    }

    public FoodMenuRightAdapter(Context context, int layoutResId, List<FoodModel> data) {
        super(context, layoutResId, data);
        this.data = data;
        this.context = context;
    }

    public CallBackListener listener;

    public void setListener(CallBackListener listener) {
        this.listener = listener;
    }

    @Override
    protected void convert(final BaseViewHolder holder, final FoodModel s) {
        final ImageView img_add = holder.getView(R.id.img_add);
        final ImageView img_jian = holder.getView(R.id.img_jian);
        final TextView tv_num = holder.getView(R.id.tv_num);

        holder.setText(R.id.tv_name, s.getName()).setText(R.id.tv_price, "¥" + s.getPrice()).setText(R.id.tv_num, s
                .getCount() + "");
        if (0 == s.getCount()) {
            img_jian.setVisibility(View.INVISIBLE);
            tv_num.setVisibility(View.INVISIBLE);
            img_add.setImageResource(R.drawable.ic_addto_cart);
        }
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyCountAndPriceInterface.doIncrease(1, holder.getLayoutPosition(), img_add, img_jian, tv_num, null);
                listener.callBackImage(img_add);
            }
        });
        img_jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyCountAndPriceInterface.doDecrease(1, holder.getLayoutPosition(), img_add, img_jian, tv_num, null);
            }
        });
    }

//    public void updateUI(String s) {
//        img_add.setImageBitmap(goods.getmGoodsBitmap());
//    }

    public interface CallBackListener {
        void callBackImage(ImageView image);
    }
}
