package sinia.com.smartmart.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import sinia.com.smartmart.R;

/**
 * Created by 忧郁的眼神 on 2016/8/5.
 */
public class FoodCommentAdapter extends BaseQuickAdapter<String> {

    private Context context;
    private List<String> data;

    public FoodCommentAdapter(Context context, int layoutResId, List<String> data) {
        super(context, layoutResId, data);
        this.data = data;
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, String s) {

    }
}
