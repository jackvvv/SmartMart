package sinia.com.smartmart.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.smartmart.R;
import sinia.com.smartmart.adapter.MyExpandableListAdapter;
import sinia.com.smartmart.base.BaseActivity;
import sinia.com.smartmart.bean.CartBean;
import sinia.com.smartmart.mycallback.CheckInterface;
import sinia.com.smartmart.mycallback.ModifyCountInterface;
import sinia.com.smartmart.utils.StringUtil;
import sinia.com.smartmart.view.swiplayout.SwipeLayoutManager;

/**
 * Created by 忧郁的眼神 on 2016/11/10 0010.
 */

public class BuildCartActivity extends BaseActivity implements CheckInterface, ModifyCountInterface {

    @Bind(R.id.ivSelectAll)
    CheckBox ivSelectAll;
    @Bind(R.id.a)
    TextView a;
    @Bind(R.id.tv_all)
    TextView tvAll;
    @Bind(R.id.tv_countMoney)
    TextView tvCountMoney;
    @Bind(R.id.btnSettle)
    TextView btnSettle;
    @Bind(R.id.l)
    LinearLayout l;
    @Bind(R.id.rlBottomBar)
    RelativeLayout rlBottomBar;
    @Bind(R.id.expandableListView)
    ExpandableListView expandableListView;
    @Bind(R.id.rl_cart)
    RelativeLayout rlCart;
    private MyExpandableListAdapter adapter;
    private CartBean cartBean = new CartBean();
    private List<CartBean.MerchantitemsBean> groupList = new ArrayList<CartBean.MerchantitemsBean>();
    private List<CartBean.MerchantitemsBean.GoodsItemsBean> childList = new ArrayList<>();
    private boolean checkAll = false, isEdit = false;
    private double totalPrice = 0.00;// 购买的商品总价
    private int totalCount = 0;// 购买的商品总数量

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_cart, "购物车");
        ButterKnife.bind(this);
        getDoingView().setText("材料清单");
        initView();
        virtualData();
    }

    private void virtualData() {
        groupList = new ArrayList<>();
        childList = new ArrayList<>();
        cartBean = new CartBean();
        cartBean.getMerchantitems();
        for (int i = 0; i < 3; i++) {
            CartBean.MerchantitemsBean group = new CartBean.MerchantitemsBean();
            group.setChecked(false);
            group.setMerchantId(i + "");
            group.setMerName("莫干山" + i);
            for (int j = 0; j < 2; j++) {
                CartBean.MerchantitemsBean.GoodsItemsBean child = new CartBean.MerchantitemsBean.GoodsItemsBean();
                child.setChecked(false);
                child.setGoodId("" + j);
                child.setGoodImage("http://img1.imgtn.bdimg.com/it/u=3757060346,1737150315&fm=21&gp=0.jpg");
                child.setGoodName("生态杉木EO" + i);
                child.setId(j + "");
                child.setNum(1);
                child.setPrice(100);
                child.setUnit("张");
                child.setGoodStatus("1");
                child.setNormName("啦");
                childList.add(child);
                group.setItems(childList);
            }
            groupList.add(group);
        }

        for (CartBean.MerchantitemsBean bean : groupList) {
            adapter.groups.add(bean);
            List<CartBean.MerchantitemsBean.GoodsItemsBean> data = new ArrayList<CartBean.MerchantitemsBean
                    .GoodsItemsBean>();
            data.addAll(bean.getItems());
            adapter.childs.put(bean.getMerName(), data);
        }
        adapter.groups = groupList;

        if (checkAll) {
            ivSelectAll.setChecked(true);
            tvCountMoney.setText(StringUtil.formatePrice(allPrice()) + "");
            btnSettle.setText("去结算");
        } else {
            ivSelectAll.setChecked(false);
            tvCountMoney.setText(0.00 + "");
            btnSettle.setText("去结算" + "(" + 0 + ")");
        }
        adapter.notifyDataSetChanged();
        expandAllGroup();
    }

    private void initView() {
        SwipeLayoutManager.getInstance().closeOpenInstance();
        adapter = new MyExpandableListAdapter(this);
        adapter.setCheckInterface(this);
        adapter.setModifyCountInterface(this);
        expandableListView.setAdapter(adapter);
        expandableListView.setGroupIndicator(null);
        expandableListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // 如果listView跟随手机拖动，关闭已经打开的SwipeLayout
                SwipeLayoutManager.getInstance().closeOpenInstance();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    @Override
    public void doing() {
        super.doing();
        startActivityForNoIntent(BuildMeterialListActivity.class);
    }

    @OnClick({R.id.ivSelectAll, R.id.btnSettle})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivSelectAll:
                doCheckAll();
                break;
            case R.id.btnSettle:
                startActivityForNoIntent(BuildConfirmOrderActivity.class);
                break;
        }
    }

    /**
     * 全选与反选---结算
     */
    private void doCheckAll() {
        if (null != adapter.groups && 0 != adapter.groups.size() && null != adapter.childs && 0
                != adapter.childs.size()) {
            for (int i = 0; i < adapter.groups.size(); i++) {
                adapter.groups.get(i).setChecked(ivSelectAll.isChecked());
                CartBean.MerchantitemsBean group = adapter.groups.get(i);
                List<CartBean.MerchantitemsBean.GoodsItemsBean> childs = adapter.childs.get(group.getMerName());
                for (int j = 0; j < childs.size(); j++) {
                    //选中上架商品
                    if ("1".equals(childs.get(j).getGoodStatus())) {
                        childs.get(j).setChecked(ivSelectAll.isChecked());
                    }
                }
            }
            tvCountMoney.setText(allPrice() + "");
            btnSettle.setText("去结算");
            adapter.notifyDataSetChanged();
        }
    }

    // 选中的价格
    private double allPrice() {
        double allPrice = 0;
        if (null != adapter.childs && 0 != adapter.childs.size() && null != adapter.groups && 0
                != adapter.groups.size()) {
            for (int i = 0; i < adapter.groups.size(); i++) {
                String key = adapter.groups.get(i).getMerName();
                List<CartBean.MerchantitemsBean.GoodsItemsBean> data = adapter.childs.get(key);
                for (CartBean.MerchantitemsBean.GoodsItemsBean bean : data) {
                    if (bean.isChecked()) {
                        allPrice = allPrice + bean.getPrice() * bean.getNum();
                    }
                }
            }
        }
        return allPrice;
    }

    @Override
    public void checkGroup(int groupPosition, boolean isChecked) {
        CartBean.MerchantitemsBean groupBean = adapter.groups.get(groupPosition);
        List<CartBean.MerchantitemsBean.GoodsItemsBean> goodsList = adapter.childs.get(groupBean.getMerName());
        for (int i = 0; i < goodsList.size(); i++) {
            if ("1".equals(goodsList.get(i).getGoodStatus()) && !isEdit) {
                goodsList.get(i).setChecked(isChecked);
            } else if (isEdit) {
                goodsList.get(i).setChecked(isChecked);
            }
        }
        if (isAllCheck()) {
            ivSelectAll.setChecked(true);
        } else {
            ivSelectAll.setChecked(false);
        }
        adapter.notifyDataSetChanged();
        calculateMoneyAndNum();
    }

    @Override
    public void checkChild(int groupPosition, int childPosition, boolean isChecked) {
        boolean allChildSameState = true;// 判断改组下面的所有子元素是否是同一种状态
        CartBean.MerchantitemsBean groupBean = adapter.groups.get(groupPosition);
        List<CartBean.MerchantitemsBean.GoodsItemsBean> goodsList = adapter.childs.get(groupBean.getMerName());
        for (int i = 0; i < goodsList.size(); i++) {
            if (goodsList.get(i).isChecked() != isChecked) {
                allChildSameState = false;
                break;
            }
        }
        if (allChildSameState) {
            groupBean.setChecked(isChecked);// 如果所有子元素状态相同，那么对应的组元素被设为这种统一状态
        } else {
            groupBean.setChecked(false);// 否则，组元素一律设置为未选中状态
        }

        if (isAllCheck())
            ivSelectAll.setChecked(true);
        else
            ivSelectAll.setChecked(false);
        adapter.notifyDataSetChanged();
        calculateMoneyAndNum();
    }

    private boolean isAllCheck() {
        for (CartBean.MerchantitemsBean group : adapter.groups) {
            if (!group.isChecked())
                return false;
        }
        return true;
    }

    /**
     * 统计操作<br>
     * 1.先清空全局计数器<br>
     * 2.遍历所有子元素，只要是被选中状态的，就进行相关的计算操作<br>
     * 3.给底部的textView进行数据填充
     */
    private void calculateMoneyAndNum() {
        totalCount = 0;
        totalPrice = 0.00;
        for (int i = 0; i < adapter.groups.size(); i++) {
            CartBean.MerchantitemsBean groupBean = adapter.groups.get(i);
            List<CartBean.MerchantitemsBean.GoodsItemsBean> goodsList = adapter.childs.get(groupBean.getMerName());
            for (int j = 0; j < goodsList.size(); j++) {
                CartBean.MerchantitemsBean.GoodsItemsBean goods = goodsList.get(j);
                if (goods.isChecked()) {
                    totalCount++;
                    totalPrice += goods.getPrice() * goods.getNum();
                }
            }
        }
        tvCountMoney.setText(totalPrice + "");
        btnSettle.setText("去结算");
    }

    @Override
    public void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
        CartBean.MerchantitemsBean.GoodsItemsBean goods = (CartBean.MerchantitemsBean.GoodsItemsBean) adapter
                .getChild(groupPosition, childPosition);
        int currentCount = goods.getNum();
        currentCount++;
        goods.setNum(currentCount);
        ((TextView) showCountView).setText(currentCount + "");
        adapter.notifyDataSetChanged();
        calculateMoneyAndNum();
    }

    @Override
    public void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
        CartBean.MerchantitemsBean.GoodsItemsBean goods = (CartBean.MerchantitemsBean.GoodsItemsBean) adapter
                .getChild(groupPosition, childPosition);
        int currentCount = goods.getNum();
        if (currentCount == 1)
            return;
        currentCount--;
        goods.setNum(currentCount);
        ((TextView) showCountView).setText(currentCount + "");
        adapter.notifyDataSetChanged();
        calculateMoneyAndNum();
    }

    private void expandAllGroup() {
        for (int i = 0; i < groupList.size(); i++) {
            expandableListView.expandGroup(i);
        }
    }
}
