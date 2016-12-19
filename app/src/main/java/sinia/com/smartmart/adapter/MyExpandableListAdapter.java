package sinia.com.smartmart.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import sinia.com.smartmart.R;
import sinia.com.smartmart.activity.MainActivity;
import sinia.com.smartmart.bean.CartBean;
import sinia.com.smartmart.mycallback.CheckInterface;
import sinia.com.smartmart.mycallback.ModifyCountInterface;
import sinia.com.smartmart.utils.StringUtil;
import sinia.com.smartmart.utils.ViewHolder;
import sinia.com.smartmart.view.swiplayout.SwipeLayout;
import sinia.com.smartmart.view.swiplayout.SwipeLayoutManager;

import static sinia.com.smartmart.R.id.s;

/**
 * Created by 忧郁的眼神 on 2016/8/11.
 */
public class MyExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    public List<CartBean.MerchantitemsBean> groups = new ArrayList<CartBean.MerchantitemsBean>();
    public HashMap<String, List<CartBean.MerchantitemsBean.GoodsItemsBean>> childs = new HashMap<String,
            List<CartBean.MerchantitemsBean.GoodsItemsBean>>();
    private ModifyCountInterface modifyCountInterface;
    private CheckInterface checkInterface;

    public boolean isEdit = false;

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public void setCheckInterface(CheckInterface checkInterface) {
        this.checkInterface = checkInterface;
    }

    public void setModifyCountInterface(ModifyCountInterface modifyCountInterface) {
        this.modifyCountInterface = modifyCountInterface;
    }

    public MyExpandableListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return childs.get(groups.get(i).getMerName()).size();
    }

    @Override
    public Object getGroup(int i) {
        return groups.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return childs.get(groups.get(i).getMerName()).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int i, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_cart_group, null);
        }
        CheckBox ivCheckGroup = ViewHolder.get(view, R.id.ivCheckGroup);
        TextView tv_shopname = ViewHolder.get(view, R.id.tv_shopname);
        final CartBean.MerchantitemsBean groupBean = groups.get(i);
        tv_shopname.setText(groupBean.getMerName());
        if (groupBean.isChecked()) {
            ivCheckGroup.setChecked(true);
        } else {
            ivCheckGroup.setChecked(false);
        }
        ivCheckGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupBean.setChecked(((CheckBox) v).isChecked());
                checkInterface.checkGroup(i, ((CheckBox) v).isChecked());// 暴露组选接口
            }
        });
        return view;
    }

    @Override
    public View getChildView(final int i, final int i1, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_build_cart_child, null);
        }
        final SwipeLayout swipeLayout = ViewHolder.get(view, R.id.swipelayout);
        final CheckBox ivCheckChild = ViewHolder.get(view, R.id.ivCheckChild);
        ImageView img_goods = ViewHolder.get(view, R.id.img_goods);
        RelativeLayout rl_jia = ViewHolder.get(view, R.id.rl_jia);
        RelativeLayout rl_jian = ViewHolder.get(view, R.id.rl_jian);
        TextView tv_goodsname = ViewHolder.get(view, R.id.tv_goodsname);
//        TextView tv_weight = ViewHolder.get(view, R.id.tv_weight);
        LinearLayout ll_goods_bg = ViewHolder.get(view, R.id.ll_goods_bg);
        LinearLayout ll_jiajian = ViewHolder.get(view, R.id.ll_jiajian);
        final TextView tv_price = ViewHolder.get(view, R.id.tv_price);
        final TextView tv_num = ViewHolder.get(view, R.id.tv_num);

        swipeLayout.setOnSwipeLayoutClickListener(new SwipeLayout.OnSwipeLayoutClickListener() {
            @Override
            public void onClick() {
                //item 点击事件
            }
        });
        ((LinearLayout) swipeLayout.getDeleteView()).getChildAt(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwipeLayoutManager.getInstance().closeOpenInstance();
                Toast.makeText(context, "删除", Toast.LENGTH_SHORT).show();
            }
        });

        final CartBean.MerchantitemsBean.GoodsItemsBean goodsBean = childs.get(groups.get(i).getMerName()).get(i1);

        Glide.with(context).load(goodsBean.getGoodImage()).bitmapTransform(new RoundedCornersTransformation(context,
                20, 0, RoundedCornersTransformation.CornerType.ALL)).placeholder(R.drawable.ic_launcher).into(img_goods);
        tv_goodsname.setText(goodsBean.getGoodName());
        tv_price.setText("¥ " + StringUtil.formatePrice(goodsBean.getPrice()));
        tv_num.setText(goodsBean.getNum() + "");
//        tv_weight.setText(goodsBean.getNormName() + " " + goodsBean.getGoodNum() + goodsBean.getUnit());

        //商品上架
        if ("1".equals(goodsBean.getGoodStatus())) {
            ll_goods_bg.setBackgroundColor(Color.parseColor("#ffffff"));
//            tv_down.setVisibility(View.GONE);
        }
        //商品已下架
        if ("2".equals(goodsBean.getGoodStatus())) {
            ll_goods_bg.setBackgroundColor(Color.parseColor("#dddde5"));
//            tv_down.setVisibility(View.VISIBLE);
        }

        if (goodsBean.isChecked()) {
            ivCheckChild.setChecked(true);
        } else {
            ivCheckChild.setChecked(false);
        }
        rl_jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("1".equals(goodsBean.getGoodStatus())) {
                    modifyCountInterface.doDecrease(i, i1, tv_num, ivCheckChild.isChecked());
                }
            }
        });
        rl_jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("1".equals(goodsBean.getGoodStatus())) {
                    modifyCountInterface.doIncrease(i, i1, tv_num, ivCheckChild.isChecked());
                }
            }
        });

        if ("1".equals(goodsBean.getGoodStatus())) {
            ivCheckChild.setClickable(true);
        } else if ("2".equals(goodsBean.getGoodStatus())) {
            if (!isEdit) {
                ivCheckChild.setClickable(false);
            }
        }
        ivCheckChild.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (isEdit) {
                                                    goodsBean.setChecked(((CheckBox) v).isChecked());
                                                    ivCheckChild.setChecked(((CheckBox) v).isChecked());
                                                    checkInterface.checkChild(i, i1,
                                                            ((CheckBox) v).isChecked());// 暴露子选接口
                                                } else {
                                                    if ("1".equals(goodsBean.getGoodStatus())) {
                                                        ivCheckChild.setClickable(true);
                                                        goodsBean.setChecked(((CheckBox) v).isChecked());
                                                        ivCheckChild.setChecked(((CheckBox) v).isChecked());
                                                        checkInterface.checkChild(i, i1,
                                                                ((CheckBox) v).isChecked());// 暴露子选接口
                                                    }
                                                }
                                            }
                                        }

        );
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

}
