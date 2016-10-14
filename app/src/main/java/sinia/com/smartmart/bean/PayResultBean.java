package sinia.com.smartmart.bean;

/**
 * Created by 忧郁的眼神 on 2016/10/13.
 */

public class PayResultBean {


    /**
     * rescnt : 已生成账单
     * rescode : 0
     * orderno : 20161013163218585966
     */

    private String rescnt;
    private int rescode;
    private String orderno;

    public String getRescnt() {
        return rescnt;
    }

    public void setRescnt(String rescnt) {
        this.rescnt = rescnt;
    }

    public int getRescode() {
        return rescode;
    }

    public void setRescode(int rescode) {
        this.rescode = rescode;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }
}
