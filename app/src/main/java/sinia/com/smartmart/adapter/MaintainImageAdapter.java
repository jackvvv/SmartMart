package sinia.com.smartmart.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import sinia.com.smartmart.R;
import sinia.com.smartmart.activity.PreViewImageActivity;
import sinia.com.smartmart.bean.MaintainDetailBean;
import sinia.com.smartmart.utils.ViewHolder;

import static sinia.com.smartmart.R.id.img_goods;

/**
 * Created by 忧郁的眼神 on 2016/8/5.
 */
public class MaintainImageAdapter extends BaseAdapter {

    private Context context;

    private List<MaintainDetailBean.MaintainImageRescntBean.MaintainImageListBean> list;

    public MaintainImageAdapter(Context context, List<MaintainDetailBean.MaintainImageRescntBean
            .MaintainImageListBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_maintain_img, null);
        }
        ImageView img = ViewHolder.get(view, R.id.img);
        Glide.with(context).load(list.get(i).getImageurl()).crossFade().placeholder(R.drawable.ic_launcher).into(img);
        final String url = list.get(i).getImageurl();
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,
                        PreViewImageActivity.class);
                intent.putExtra("picUrl", url);
                context.startActivity(intent);
            }
        });
        return view;
    }
}
