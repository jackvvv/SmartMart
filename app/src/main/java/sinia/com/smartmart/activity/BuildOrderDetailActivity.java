package sinia.com.smartmart.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.materialdialog.MaterialDialog;
import sinia.com.smartmart.R;
import sinia.com.smartmart.actionsheetdialog.ActionSheetDialog;
import sinia.com.smartmart.adapter.BuildOrderGoodsAdapter;
import sinia.com.smartmart.base.BaseActivity;
import sinia.com.smartmart.utils.Utility;

/**
 * Created by 忧郁的眼神 on 2016/11/4 0004.
 */

public class BuildOrderDetailActivity extends BaseActivity {

    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_tel)
    TextView tvTel;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.tv_pay_type)
    TextView tvPayType;
    @Bind(R.id.tv_order_no)
    TextView tvOrderNo;
    @Bind(R.id.tv_status)
    TextView tvStatus;
    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.tv_sendTime)
    TextView tvSendTime;
    @Bind(R.id.tv_num)
    TextView tvNum;
    @Bind(R.id.tv_money)
    TextView tvMoney;
    @Bind(R.id.tv_couponed)
    TextView tvCoupons;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_realMoney)
    TextView tvRealMoney;
    @Bind(R.id.tv_cancle)
    TextView tvCancle;
    @Bind(R.id.tv_call)
    TextView tvCall;

    private BuildOrderGoodsAdapter goodsAdapter;
    private String cancleType;
    private MaterialDialog materialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_order_detail, "订单详情");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
        initData();
    }

    private void initData() {
        goodsAdapter = new BuildOrderGoodsAdapter(this, true);
        listView.setAdapter(goodsAdapter);
        Utility.setListViewHeightBasedOnChildren(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivityForNoIntent(BuildGoodsDetailActivity.class);
            }
        });
    }

    @OnClick({R.id.tv_cancle, R.id.tv_call})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancle:
                createTypeDialog(this);
                break;
            case R.id.tv_call:
                callService();
                break;
        }
    }

    private void createTypeDialog(Context context) {
        new ActionSheetDialog(context)
                .builder()
                .setCancelable(true)
                .setTitle("选择退回订单的原因")
                .setCanceledOnTouchOutside(true)
                .addSheetItem("订单下错了", ActionSheetDialog.SheetItemColor.BLACK,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                cancleType = "1";
                            }
                        })
                .addSheetItem("不想买了", ActionSheetDialog.SheetItemColor.BLACK,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                cancleType = "2";
                            }
                        })
                .addSheetItem("其他", ActionSheetDialog.SheetItemColor.BLACK,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                cancleType = "3";
                            }
                        }).show();
    }

    private void callService() {
        materialDialog = new MaterialDialog(this);
        materialDialog.setTitle("联系商家");
        materialDialog.setMessage("400-20392888");
        materialDialog.setPositiveButton("呼叫", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "400-20392888"));
                if (ActivityCompat.checkSelfPermission(BuildOrderDetailActivity.this, Manifest.permission.CALL_PHONE)
                        != PackageManager
                        .PERMISSION_GRANTED) {
                    return;
                }
                startActivity(intent);
                materialDialog.dismiss();
            }
        });
        materialDialog.setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDialog.dismiss();
            }
        });
        materialDialog.show();
    }
}
