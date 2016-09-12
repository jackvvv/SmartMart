package sinia.com.smartmart.view;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.smartmart.R;
import sinia.com.smartmart.utils.DialogUtils;

/**
 * Created by sunfusheng on 16/4/20.
 */
public class HeaderOperationViewView extends HeaderViewInterface<String> {

    @Bind(R.id.img)
    ImageView img;
    @Bind(R.id.imgFood)
    ImageView imgFood;
    @Bind(R.id.imgFarm)
    ImageView imgFarm;
    @Bind(R.id.imgJancai)
    ImageView imgJancai;
    @Bind(R.id.imgMore)
    ImageView imgMore;
    @Bind(R.id.img_jiazheng)
    ImageView imgJiazheng;
    @Bind(R.id.imgWash)
    ImageView imgWash;
    @Bind(R.id.imgClean)
    ImageView imgClean;

    public HeaderOperationViewView(Activity context) {
        super(context);
    }

    @Override
    protected void getView(String s, ListView listView) {
        View view = mInflate.inflate(R.layout.header_operation_layout, listView, false);
        ButterKnife.bind(this, view);
        listView.addHeaderView(view);
    }

    @OnClick({R.id.imgFood, R.id.imgFarm, R.id.imgJancai, R.id.imgMore, R.id.imgWash, R.id.imgClean})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgFood:
                DialogUtils.createFountionDevelopingTipsDialog(mContext, "生活服务正在完善中...");
                break;
            case R.id.imgJancai:
                DialogUtils.createFountionDevelopingTipsDialog(mContext, "生活服务正在完善中...");
                break;
            case R.id.imgMore:
                DialogUtils.createFountionDevelopingTipsDialog(mContext, "生活服务正在完善中...");
                break;
            case R.id.imgWash:
                DialogUtils.createFountionDevelopingTipsDialog(mContext, "洗衣服务正在完善中...");
                break;
            case R.id.imgClean:
                DialogUtils.createFountionDevelopingTipsDialog(mContext, "清洁服务正在完善中...");
                break;
        }
    }
}
