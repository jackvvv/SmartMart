package sinia.com.smartmart.bean;

/**
 * Created by 忧郁的眼神 on 2016/10/13.
 */

public class UserNoticeBean {

    private String noticedetail;//通知
    private String ratenum;//未读缴费通知数

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
}
