package sinia.com.smartmart.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import sinia.com.smartmart.R;
import sinia.com.smartmart.bean.FoodModel;

/**
 * Created by 忧郁的眼神 on 2016/8/5.
 */
public class FoodMenuLeftAdapter extends BaseQuickAdapter<FoodModel> {

    private Context context;
    public int selectPosition;

    public FoodMenuLeftAdapter(Context context, int layoutResId, List<FoodModel> data) {
        super(context, layoutResId, data);
        this.context = context;
    }
//    private List<ClassfyListBean.BigClassBean> list;


//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        if (view == null) {
//            view = LayoutInflater.from(context).inflate(R.layout.item_food_menu_left, null);
//        }
//        TextView tv_name = ViewHolder.get(view, R.id.tv_name);
//        ImageView img_line = ViewHolder.get(view, R.id.img_line);
//        if (selectPosition == i) {
//            tv_name.setSelected(true);
//            view.setBackgroundColor(context.getResources().getColor(R.color.textwhite));
//            img_line.setVisibility(View.VISIBLE);
//        } else {
//            tv_name.setSelected(false);
//            view.setBackgroundColor(Color.parseColor("#f3f3f3"));
//            img_line.setVisibility(View.INVISIBLE);
//        }
//        return view;
//    }

    @Override
    protected void convert(BaseViewHolder holder, FoodModel s) {
        holder.setText(R.id.tv_name, s.getName());
        ImageView img_line = holder.getView(R.id.img_line);
        TextView tv_name = holder.getView(R.id.tv_name);
        LinearLayout ll_root = holder.getView(R.id.ll_root);

        if (selectPosition == holder.getLayoutPosition()) {
            tv_name.setSelected(true);
            ll_root.setBackgroundColor(context.getResources().getColor(R.color.textwhite));
            img_line.setVisibility(View.VISIBLE);
        } else {
            tv_name.setSelected(false);
            ll_root.setBackgroundColor(Color.parseColor("#f3f3f3"));
            img_line.setVisibility(View.INVISIBLE);
        }
    }
}
