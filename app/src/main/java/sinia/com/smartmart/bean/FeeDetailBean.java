package sinia.com.smartmart.bean;

import java.io.Serializable;

/**
 * Created by 忧郁的眼神 on 2016/10/13.
 */

public class FeeDetailBean implements Serializable {


    /**
     * username : 老司机
     * rateno : 01293847124
     * address : 百花大道J1地块 14栋  2单元 15层 1523室
     * rescode : 0
     * ratecost : 200
     * companyname : 贵阳电力公司
     */

    private String username;
    private String rateno;
    private String address;
    private int rescode;
    private double ratecost;
    private String companyname;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRateno() {
        return rateno;
    }

    public void setRateno(String rateno) {
        this.rateno = rateno;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRescode() {
        return rescode;
    }

    public void setRescode(int rescode) {
        this.rescode = rescode;
    }

    public double getRatecost() {
        return ratecost;
    }

    public void setRatecost(double ratecost) {
        this.ratecost = ratecost;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }
}
