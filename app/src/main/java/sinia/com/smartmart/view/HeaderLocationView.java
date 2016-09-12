package sinia.com.smartmart.view;

import android.app.Activity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.smartmart.R;

/**
 * Created by sunfusheng on 16/4/20.
 */
public class HeaderLocationView extends HeaderViewInterface<String> {


    @Bind(R.id.tv_address)
    TextView tvAddress;

    public HeaderLocationView(Activity context) {
        super(context);
    }

    @Override
    protected void getView(String address, ListView listView) {
        View view = mInflate.inflate(R.layout.header_location_layout, listView, false);
        ButterKnife.bind(this, view);
        dealWithTheView(address);
        listView.addHeaderView(view);
    }

    private void dealWithTheView(String address) {
        tvAddress.setText(address);
    }

    @OnClick(R.id.tv_address)
    public void onClick() {

    }
}
