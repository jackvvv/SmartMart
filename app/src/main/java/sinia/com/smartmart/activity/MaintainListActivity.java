package sinia.com.smartmart.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import sinia.com.smartmart.R;
import sinia.com.smartmart.adapter.MaintainAdapter;
import sinia.com.smartmart.base.BaseActivity;
import sinia.com.smartmart.bean.JsonBean;
import sinia.com.smartmart.bean.MaintainListBean;
import sinia.com.smartmart.bean.VillageListBean;
import sinia.com.smartmart.utils.ActivityManager;
import sinia.com.smartmart.utils.AppInfoUtil;
import sinia.com.smartmart.utils.Constants;
import sinia.com.smartmart.utils.JsonUtil;
import sinia.com.smartmart.utils.MyApplication;
import sinia.com.smartmart.utils.Utils;
import sinia.com.smartmart.view.swipmenulistview.SwipeMenu;
import sinia.com.smartmart.view.swipmenulistview.SwipeMenuCreator;
import sinia.com.smartmart.view.swipmenulistview.SwipeMenuItem;
import sinia.com.smartmart.view.swipmenulistview.SwipeMenuListView;

import static android.R.id.list;

/**
 * Created by 忧郁的眼神 on 2016/9/6.
 */
public class MaintainListActivity extends BaseActivity {

    @Bind(R.id.listView)
    SwipeMenuListView listView;
    @Bind(R.id.ll_emptyView)
    LinearLayout llEmptyView;
    private MaintainAdapter adapter;
    private List<MaintainListBean.MaintainBean> list = new ArrayList<>();
    private AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintain_list, "物业报修");
        ButterKnife.bind(this);
        getImg_pic().setImageResource(R.drawable.icon_add);
        getImg_pic().setVisibility(View.VISIBLE);
        getDoingView().setVisibility(View.GONE);
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMaintainList();
    }

    private void getMaintainList() {
        showLoad("加载中...");
        RequestParams params = new RequestParams();
        params.put("memberid", MyApplication.getInstance().getUserInfo().getMemberid());
        Log.i("tag", Constants.BASE_URL + "repairlist");
        client.post(Constants.BASE_URL + "repairlist", params,
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
                            MaintainListBean bean = gson.fromJson(resultStr,
                                    MaintainListBean.class);
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
        adapter = new MaintainAdapter(this, list);
        listView.setAdapter(adapter);
        listView.setEmptyView(llEmptyView);
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xFF,
                        0xe8, 0xcd)));
                deleteItem.setWidth(AppInfoUtil.dip2px(MaintainListActivity.this, 90));
                deleteItem.setIcon(R.drawable.ic_delete);
                deleteItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        listView.setMenuCreator(creator);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu,
                                           int index) {
                switch (index) {
                    case 0:
                        String id = list.get(position).getRepairid();
                        delete(id, position);
                }
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("repairid", list.get(i).getRepairid());
                startActivityForIntent(MaintainDetailActivity.class, intent);
            }
        });
    }

    private void delete(String id, final int position) {
        showLoad("加载中...");
        RequestParams params = new RequestParams();
        params.put("memberid", MyApplication.getInstance().getUserInfo().getMemberid());
        params.put("repairid", id);
        Log.i("tag", Constants.BASE_URL + "delrepair");
        client.post(Constants.BASE_URL + "delrepair", params,
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
        startActivityForNoIntent(AddMaintainActivity.class);
    }
}
