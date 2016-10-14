package sinia.com.smartmart.bean;

import java.util.List;

/**
 * Created by 忧郁的眼神 on 2016/10/14.
 */

public class BillListBean {
    private BillListBean.RescntBean rescnt;

    private int rescode;

    public BillListBean.RescntBean getRescnt() {
        return rescnt;
    }

    public void setRescnt(BillListBean.RescntBean rescnt) {
        this.rescnt = rescnt;
    }

    public int getRescode() {
        return rescode;
    }

    public void setRescode(int rescode) {
        this.rescode = rescode;
    }

    public static class RescntBean {
        /**
         * username : 15093403946
         * address : 0
         * readtype : 1
         * messagetitle : 水费缴纳提醒
         * createtime  : 2016-10-13 14:57:44
         * rateid : 1
         * ratecost : 200.0
         * type : 1
         */

        private List<BillListBean.RescntBean.BillBean> list;

        public List<BillListBean.RescntBean.BillBean> getList() {
            return list;
        }

        public void setList(List<BillListBean.RescntBean.BillBean> list) {
            this.list = list;
        }

        public static class BillBean {
            private String billstatus;//账单状态 1未支付 2已支付 3已删除
            private String createtime;
            private String billid;
            private double billcost;
            private String billtype;//1水费 2电费 3 煤气费 4 物业费
            private String rateno;

            public String getBillstatus() {
                return billstatus;
            }

            public void setBillstatus(String billstatus) {
                this.billstatus = billstatus;
            }

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }

            public String getBillid() {
                return billid;
            }

            public void setBillid(String billid) {
                this.billid = billid;
            }

            public double getBillcost() {
                return billcost;
            }

            public void setBillcost(double billcost) {
                this.billcost = billcost;
            }

            public String getBilltype() {
                return billtype;
            }

            public void setBilltype(String billtype) {
                this.billtype = billtype;
            }

            public String getRateno() {
                return rateno;
            }

            public void setRateno(String rateno) {
                this.rateno = rateno;
            }
        }
    }
}
