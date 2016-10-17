package sinia.com.smartmart.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import sinia.com.smartmart.R;
import sinia.com.smartmart.adapter.AccountAdapter;
import sinia.com.smartmart.base.BaseActivity;
import sinia.com.smartmart.bean.BillListBean;
import sinia.com.smartmart.bean.FeeDetailBean;
import sinia.com.smartmart.bean.JsonBean;
import sinia.com.smartmart.utils.AppInfoUtil;
import sinia.com.smartmart.utils.Constants;
import sinia.com.smartmart.utils.JsonUtil;
import sinia.com.smartmart.utils.MyApplication;
import sinia.com.smartmart.utils.Utils;
import sinia.com.smartmart.view.swipmenulistview.SwipeMenu;
import sinia.com.smartmart.view.swipmenulistview.SwipeMenuCreator;
import sinia.com.smartmart.view.swipmenulistview.SwipeMenuItem;
import sinia.com.smartmart.view.swipmenulistview.SwipeMenuListView;

import static sinia.com.smartmart.R.color.view_bg;
import static sinia.com.smartmart.R.id.tv_ele;
import static sinia.com.smartmart.R.id.v;

/**
 * Created by 忧郁的眼神 on 2016/9/22.
 */

public class MyAccountActivity extends BaseActivity {
    @Bind(R.id.listView)
    SwipeMenuListView listView;
    @Bind(R.id.view_line)
    View view_line;

    private AccountAdapter adapter;
    private boolean isFilter = false;
    private PopupWindow popWindow;
    private AsyncHttpClient client = new AsyncHttpClient();
    private List<BillListBean.RescntBean.BillBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account, "我的账单");
        ButterKnife.bind(this);
        getDoingView().setText("筛选");
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getBillList("-1");
    }

    private void getBillList(String type) {
        showLoad("加载中...");
        RequestParams params = new RequestParams();
        params.put("memberid", MyApplication.getInstance().getUserInfo().getMemberid());
        params.put("type", type);
        Log.i("tag", Constants.BASE_URL + "mybilllist" + params);
        client.post(Constants.BASE_URL + "mybilllist", params,
                new AsyncHttpResponseHandler() {

                    @Override
                    public void onFailure(Throwable arg0, String arg1) {
                        super.onFailure(arg0, arg1);
                    }

                    @Override
                    public void onSuccess(int arg0, String s) {
                        dismiss();
                        String resultStr = Utils
                                .getStrVal(new String(s));
                        JsonBean json = JsonUtil.getJsonBean(resultStr);
                        Gson gson = new Gson();
                        int rescode = json.getRescode();
                        if (0 == rescode) {
                            BillListBean bean = gson.fromJson(resultStr, BillListBean.class);
                            list.clear();
                            list.addAll(bean.getRescnt().getList());
                            adapter.notifyDataSetChanged();
                        } else {
                            showToast((String) json.getRescnt());
                        }
                    }
                });
    }

    private void initData() {
        adapter = new AccountAdapter(this, list);
        listView.setAdapter(adapter);
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xFF,
                        0xe8, 0xcd)));
                deleteItem.setWidth(AppInfoUtil.dip2px(MyAccountActivity.this, 90));
                deleteItem.setIcon(R.drawable.ic_delete);
                deleteItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(deleteItem);
            }
        };
        listView.setMenuCreator(creator);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu,
                                           int index) {
                switch (index) {
                    case 0:
                        String id = list.get(position).getBillid();
                        delete(id, position);
                }
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivityForNoIntent(AccountDetailActivity.class);
            }
        });
    }

    private void delete(String id, final int position) {
        showLoad("加载中...");
        RequestParams params = new RequestParams();
        params.put("memberid", MyApplication.getInstance().getUserInfo().getMemberid());
        params.put("billid", id);
        Log.i("tag", Constants.BASE_URL + "deletebill");
        client.post(Constants.BASE_URL + "deletebill", params,
                new AsyncHttpResponseHandler() {

                    @Override
                    public void onFailure(Throwable arg0, String arg1) {
                        super.onFailure(arg0, arg1);
                    }

                    @Override
                    public void onSuccess(int arg0, String s) {
                        dismiss();
                        String resultStr = Utils
                                .getStrVal(new String(s));
                        JsonBean json = JsonUtil.getJsonBean(resultStr);
                        int rescode = json.getRescode();
                        if (0 == rescode) {
                            showToast((String) json.getRescnt());
                            list.remove(position);
                            adapter.notifyDataSetChanged();
                        } else {
                            showToast((String) json.getRescnt());
                        }
                    }
                });
    }

    @Override
    public void doing() {
        super.doing();
        if (isFilter) {
            getDoingView().setText("筛选");
            isFilter = false;
            if (popWindow != null && popWindow.isShowing()) {
                popWindow.dismiss();
            }
        } else {
            getDoingView().setText("取消");
            isFilter = true;
            showFilterView();
        }
    }

    private void showFilterView() {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.view_filter_account,
                null);
        popWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        popWindow.setAnimationStyle(R.style.ActionSheetDialogAnimation2);
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(false);
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        popWindow.showAsDropDown(view_line, 0, 0);
        WindowManager.LayoutParams lp = getWindow()
                .getAttributes();
        getWindow().setAttributes(lp);
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                getDoingView().setText("筛选");
                isFilter = false;
            }
        });

        final TextView tv_water = (TextView) view.findViewById(R.id.tv_water);
        final TextView tv_ele = (TextView) view.findViewById(R.id.tv_ele);
        final TextView tv_gas = (TextView) view.findViewById(R.id.tv_gas);
        final TextView tv_property = (TextView) view.findViewById(R.id.tv_property);
        tv_water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_water.setBackgroundResource(R.drawable.bg_filter_btn);
                tv_ele.setBackgroundResource(0);
                tv_gas.setBackgroundResource(0);
                tv_property.setBackgroundResource(0);
                getDoingView().setText("筛选");
                isFilter = false;
                popWindow.dismiss();
                getBillList("1");
            }
        });
        tv_ele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_ele.setBackgroundResource(R.drawable.bg_filter_btn);
                tv_water.setBackgroundResource(0);
                tv_gas.setBackgroundResource(0);
                tv_property.setBackgroundResource(0);
                getDoingView().setText("筛选");
                isFilter = false;
                popWindow.dismiss();
                getBillList("2");
            }
        });
        tv_gas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_gas.setBackgroundResource(R.drawable.bg_filter_btn);
                tv_ele.setBackgroundResource(0);
                tv_water.setBackgroundResource(0);
                tv_property.setBackgroundResource(0);
                getDoingView().setText("筛选");
                isFilter = false;
                popWindow.dismiss();
                getBillList("3");
            }
        });
        tv_property.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_property.setBackgroundResource(R.drawable.bg_filter_btn);
                tv_ele.setBackgroundResource(0);
                tv_gas.setBackgroundResource(0);
                tv_water.setBackgroundResource(0);
                getDoingView().setText("筛选");
                isFilter = false;
                popWindow.dismiss();
                getBillList("4");
            }
        });
    }
}
