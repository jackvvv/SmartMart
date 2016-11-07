package sinia.com.smartmart.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by 忧郁的眼神 on 2016/8/5.
 */
public class BuildCommentAdapter extends BaseQuickAdapter<String> {

    private Context context;
    private List<String> data;

    public BuildCommentAdapter(Context context, int layoutResId, List<String> data) {
        super(context, layoutResId, data);
        this.data = data;
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, String s) {

    }
}
