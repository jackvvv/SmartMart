package sinia.com.smartmart.bean;

import java.io.Serializable;

/**
 * Created by 忧郁的眼神 on 2016/10/13.
 */

public class BillDetailBean implements Serializable {


    /**
     * username : 老司机
     * rateno : 01293847124
     * address : 百花大道J1地块 14栋  2单元 15层 1523室
     * rescode : 0
     * ratecost : 200
     * companyname : 贵阳电力公司
     */

    private String paytype;//支付方式 1支付宝 2微信支付
    private String paymentinstruction;
    private String paymentno;
    private int rescode;
    private double cost;
    private String createtime;
    private String orderno;
    private String billstatus;//订单状态 1未支付 2已支付

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public String getPaymentinstruction() {
        return paymentinstruction;
    }

    public void setPaymentinstruction(String paymentinstruction) {
        this.paymentinstruction = paymentinstruction;
    }

    public String getPaymentno() {
        return paymentno;
    }

    public void setPaymentno(String paymentno) {
        this.paymentno = paymentno;
    }

    public int getRescode() {
        return rescode;
    }

    public void setRescode(int rescode) {
        this.rescode = rescode;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getBillstatus() {
        return billstatus;
    }

    public void setBillstatus(String billstatus) {
        this.billstatus = billstatus;
    }
}
