package sinia.com.smartmart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import sinia.com.smartmart.R;
import sinia.com.smartmart.adapter.FeeMessageAdapter;
import sinia.com.smartmart.adapter.OrderMessageAdapter;
import sinia.com.smartmart.base.BaseActivity;
import sinia.com.smartmart.bean.FeeMessageListBean;
import sinia.com.smartmart.bean.JsonBean;
import sinia.com.smartmart.bean.MaintainDetailBean;
import sinia.com.smartmart.utils.Constants;
import sinia.com.smartmart.utils.JsonUtil;
import sinia.com.smartmart.utils.MyApplication;
import sinia.com.smartmart.utils.Utils;

/**
 * Created by 忧郁的眼神 on 2016/9/14.
 */
public class FeeMessageActivity extends BaseActivity {

    @Bind(R.id.listView)
    ListView listView;
    private FeeMessageAdapter adapter;
    private List<FeeMessageListBean.RescntBean.FeeMessageBean> feeList = new ArrayList<>();
    private AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_message, "缴费提醒");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getOrderList();
    }

    private void getOrderList() {
        showLoad("加载中...");
        RequestParams params = new RequestParams();
        params.put("memberid", MyApplication.getInstance().getUserInfo().getMemberid());
        Log.i("tag", Constants.BASE_URL + "ratemessagelist" + params);
        client.post(Constants.BASE_URL + "ratemessagelist", params,
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
                            FeeMessageListBean detailBean = gson.fromJson(resultStr, FeeMessageListBean.class);
                            feeList.clear();
                            feeList.addAll(detailBean.getRescnt().getList());
                            adapter.notifyDataSetChanged();
                        } else {
                            showToast((String) json.getRescnt());
                        }
                    }
                });
    }

    private void initData() {
        adapter = new FeeMessageAdapter(this, feeList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("fee_type", feeList.get(i).getType());
                intent.putExtra("rateid", feeList.get(i).getRateid());
                intent.putExtra("isFromProperty", "2");
                if (feeList.get(i).getType().equals("4")) {
                    startActivityForIntent(PayPropertyFeeActivity.class, intent);
                } else {
                    startActivityForIntent(PayFeeActivity.class, intent);
                }
            }
        });
    }
}
