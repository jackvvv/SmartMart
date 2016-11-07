package sinia.com.smartmart.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.ABaseTransformer;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.smartmart.R;
import sinia.com.smartmart.activity.BuildAllCommentActivity;
import sinia.com.smartmart.adapter.BuildGoodsColorAdapter;
import sinia.com.smartmart.adapter.BuildGoodsNormAdapter;
import sinia.com.smartmart.base.BaseFragment;
import sinia.com.smartmart.utils.AppInfoUtil;
import sinia.com.smartmart.view.CircleImageView;
import sinia.com.smartmart.view.LocalImageHolderView;
import sinia.com.smartmart.view.MyGridView;

import static sinia.com.smartmart.R.id.gv_color;
import static sinia.com.smartmart.R.id.gv_norm;

/**
 * Created by 忧郁的眼神 on 2016/11/7 0007.
 */

public class BuildGoodsDetailFragment extends BaseFragment {

    @Bind(R.id.convenientBanner)
    ConvenientBanner convenientBanner;
    @Bind(R.id.tv_goodsname)
    TextView tvGoodsname;
    @Bind(R.id.tv_old_price)
    TextView tvOldPrice;
    @Bind(R.id.tv_sall_num)
    TextView tvSallNum;
    @Bind(R.id.tv_new_price)
    TextView tvNewPrice;
    @Bind(R.id.tv_service)
    TextView tvService;
    @Bind(R.id.tv_color_norm)
    TextView tvColorNorm;
    @Bind(R.id.tv_num)
    TextView tvNum;
    @Bind(R.id.tv_commentNum)
    TextView tvCommentNum;
    @Bind(R.id.img_head)
    CircleImageView imgHead;
    @Bind(R.id.tv_tel)
    TextView tvTel;
    @Bind(R.id.ratingBar)
    RatingBar ratingBar;
    @Bind(R.id.tv_rating)
    TextView tvRating;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.view_anchor)
    View view_anchor;
    @Bind(R.id.ll_comment)
    LinearLayout llComment;
    //    @Bind(R.id.lv_comment)
//    ListView lvComment;
    private View rootView;
    private ArrayList<Integer> localImages = new ArrayList<Integer>();
    private PopupWindow normPopupWindow, paramsPoupWindow;
    private BuildGoodsNormAdapter normAdapter;
    private BuildGoodsColorAdapter colorAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_build_goods_detail, null);
        ButterKnife.bind(this, rootView);
        initData();
        return rootView;
    }

    public static BuildGoodsDetailFragment newInstance() {
        BuildGoodsDetailFragment fragment = new BuildGoodsDetailFragment();
        return fragment;
    }

    private void initData() {
        localImages.add(AppInfoUtil.getResId("img_wood_default", R.drawable.class));
        localImages.add(AppInfoUtil.getResId("img_wood_default", R.drawable.class));
        int h = AppInfoUtil.getScreenWidth(getActivity()) * 650 / 750;
        convenientBanner.getLayoutParams().height = h;
        String transforemerName = "StackTransformer";
        ABaseTransformer transforemer = null;
        try {
            Class cls = Class.forName("com.ToxicBakery.viewpager.transforms." + transforemerName);
            transforemer = (ABaseTransformer) cls.newInstance();
            convenientBanner.setPages(
                    new CBViewHolderCreator<LocalImageHolderView>() {
                        @Override
                        public LocalImageHolderView createHolder() {
                            return new LocalImageHolderView();
                        }
                    }, localImages).startTurning(3000).setPageIndicator(new int[]{R.drawable.carousel_point1, R.drawable
                    .carousel_point_select1})
                    .getViewPager().setPageTransformer(true, transforemer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
    }

    @OnClick({R.id.rl_standard, R.id.ll_params, R.id.tv_look_all})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.rl_standard:
                showNormWindow();
                break;
            case R.id.ll_params:
                showParamsWindow();
                break;
            case R.id.tv_look_all:
                intent = new Intent(getActivity(), BuildAllCommentActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void showNormWindow() {
        LayoutInflater inflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popw_build_goods_norm, null);
        ImageView img = (ImageView) view.findViewById(R.id.img);
        ImageView img_colse = (ImageView) view.findViewById(R.id.img_colse);
        TextView tv_num = (TextView) view.findViewById(R.id.tv_num);
        TextView tv_goods_name = (TextView) view.findViewById(R.id.tv_goods_name);
        TextView tv_storeNum = (TextView) view.findViewById(R.id.tv_storeNum);
        TextView tv_singlePrice = (TextView) view.findViewById(R.id.tv_singlePrice);
        TextView tv_money = (TextView) view.findViewById(R.id.tv_money);
        TextView tv_cart = (TextView) view.findViewById(R.id.tv_cart);
        MyGridView gv_norm = (MyGridView) view.findViewById(R.id.gv_norm);
        MyGridView gv_color = (MyGridView) view.findViewById(R.id.gv_color);
        RelativeLayout rl_jian = (RelativeLayout) view.findViewById(R.id.rl_jian);
        RelativeLayout rl_jia = (RelativeLayout) view.findViewById(R.id.rl_jia);

        normAdapter = new BuildGoodsNormAdapter(getActivity());
        normAdapter.selectPosition = 0;
        gv_norm.setAdapter(normAdapter);
        gv_norm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                normAdapter.selectPosition = position;
                normAdapter.notifyDataSetChanged();
            }
        });

        colorAdapter = new BuildGoodsColorAdapter(getActivity());
        colorAdapter.selectPosition = 0;
        gv_color.setAdapter(colorAdapter);
        gv_color.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                colorAdapter.selectPosition = position;
                colorAdapter.notifyDataSetChanged();
            }
        });

        normPopupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams
                .WRAP_CONTENT);
        normPopupWindow.setAnimationStyle(R.style.ActionSheetDialogAnimation);
        normPopupWindow.setFocusable(true);
        normPopupWindow.setOutsideTouchable(false);// 设置允许在外点击消失
        ColorDrawable dw = new ColorDrawable(0x50000000);
        normPopupWindow.setBackgroundDrawable(dw);
        normPopupWindow.showAtLocation(view_anchor, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        img_colse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                normPopupWindow.dismiss();
            }
        });
        rl_jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        rl_jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        tv_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                normPopupWindow.dismiss();
            }
        });
    }

    private void showParamsWindow() {
        LayoutInflater inflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popw_build_goods_params, null);
        ImageView img_colse = (ImageView) view.findViewById(R.id.img_colse);
        TextView tv_grade = (TextView) view.findViewById(R.id.tv_grade);
        TextView tv_level = (TextView) view.findViewById(R.id.tv_level);
        TextView tv_material = (TextView) view.findViewById(R.id.tv_material);
        TextView tv_size = (TextView) view.findViewById(R.id.tv_size);

        paramsPoupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams
                .WRAP_CONTENT);
        paramsPoupWindow.setAnimationStyle(R.style.ActionSheetDialogAnimation);
        paramsPoupWindow.setFocusable(true);
        paramsPoupWindow.setOutsideTouchable(false);// 设置允许在外点击消失
        ColorDrawable dw = new ColorDrawable(0x50000000);
        paramsPoupWindow.setBackgroundDrawable(dw);
        paramsPoupWindow.showAtLocation(view_anchor, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        img_colse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paramsPoupWindow.dismiss();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
