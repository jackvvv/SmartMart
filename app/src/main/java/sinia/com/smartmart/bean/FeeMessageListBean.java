package sinia.com.smartmart.bean;

import java.util.List;

/**
 * Created by 忧郁的眼神 on 2016/10/13.
 */

public class FeeMessageListBean {


    private RescntBean rescnt;
    /**
     * rescnt : {"list":[{"username":"15093403946","address":"0","readtype":1,"messagetitle":"水费缴纳提醒","createtime
     * ":"2016-10-13 14:57:44","rateid":1,"ratecost":200,"type":1},{"username":"15093403946","address":"0",
     * "readtype":1,"messagetitle":"电费缴纳提醒","createtime ":"2016-10-13 14:57:44","rateid":2,"ratecost":300,"type":2},
     * {"username":"15093403946","rateno":"0.0","address":"0","readtype":1,"messagetitle":"煤气费缴纳提醒","createtime
     * ":"2016-10-13 14:57:44","rateid":3,"ratecost":200,"type":3},{"username":"老司机","address":"百花大道J1地块 14栋  2单元 15层
     * 1523室","readtype":1,"messagetitle":"物业费缴纳提醒","createtime ":"2016-10-13 14:57:45","rateid":4,"ratecost":1560,
     * "type":4,"companyname":"测试公司"}]}
     * rescode : 0
     */

    private int rescode;

    public RescntBean getRescnt() {
        return rescnt;
    }

    public void setRescnt(RescntBean rescnt) {
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

        private List<FeeMessageBean> list;

        public List<FeeMessageBean> getList() {
            return list;
        }

        public void setList(List<FeeMessageBean> list) {
            this.list = list;
        }

        public static class FeeMessageBean {
            private String username;
            private String address;
            private int readtype;
            private String messagetitle;
            private String createtime;
            private String rateid;
            private double ratecost;
            private String type;
            private String companyname;
            private String rateno;

            public String getCompanyname() {
                return companyname;
            }

            public void setCompanyname(String companyname) {
                this.companyname = companyname;
            }

            public String getRateno() {
                return rateno;
            }

            public void setRateno(String rateno) {
                this.rateno = rateno;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getReadtype() {
                return readtype;
            }

            public void setReadtype(int readtype) {
                this.readtype = readtype;
            }

            public String getMessagetitle() {
                return messagetitle;
            }

            public void setMessagetitle(String messagetitle) {
                this.messagetitle = messagetitle;
            }

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }

            public String getRateid() {
                return rateid;
            }

            public void setRateid(String rateid) {
                this.rateid = rateid;
            }

            public double getRatecost() {
                return ratecost;
            }

            public void setRatecost(double ratecost) {
                this.ratecost = ratecost;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
