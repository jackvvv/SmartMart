package sinia.com.smartmart.bean;

import java.io.Serializable;

/**
 * Created by 忧郁的眼神 on 2016/10/12.
 */

public class UserBean implements Serializable {

    private UserInfo rescnt;
    private String noticedetail;//通知
    private String ratenum;//未读缴费通知数
    private int rescode;

    public String getNoticedetail() {
        return noticedetail;
    }

    public void setNoticedetail(String noticedetail) {
        this.noticedetail = noticedetail;
    }

    public String getRatenum() {
        return ratenum;
    }

    public void setRatenum(String ratenum) {
        this.ratenum = ratenum;
    }

    public int getRescode() {
        return rescode;
    }

    public void setRescode(int rescode) {
        this.rescode = rescode;
    }

    public UserInfo getRescnt() {
        return rescnt;
    }

    public void setRescnt(UserInfo rescnt) {
        this.rescnt = rescnt;
    }

}
