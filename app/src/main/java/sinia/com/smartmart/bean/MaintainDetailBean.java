package sinia.com.smartmart.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 忧郁的眼神 on 2016/10/13.
 */

public class MaintainDetailBean implements Serializable {


    /**
     * content : 楼梯口灯坏了，抓紧时间来修吧！！！
     * repairmobile : 15651788424
     * address : 百花大道J1地块 14栋  2单元 15层 1523室
     * rescnt : {"list":[{"imageurl":"http://bmob-cdn-6779.b0.upaiyun.com/2016/10/13/2fc925f916e74c90a9986cc0f0033eee
     * .jpg","imageid":2},{"imageurl":"http://bmob-cdn-6779.b0.upaiyun
     * .com/2016/10/13/524580b428ec4eaa829274010598be9b.png","imageid":3},{"imageurl":"http://bmob-cdn-6779.b0
     * .upaiyun.com/2016/10/13/170c26117c32422aab3716a36784bffa.jpg","imageid":4},{"imageurl":"http://bmob-cdn-6779
     * .b0.upaiyun.com/2016/10/13/2e6fda46caad4bf38baad0009b3e5c2c.jpg","imageid":5}]}
     * createtime  : 2016-10-13 13:29:56
     * rescode : 0
     * repairid : 6
     * repairtype : 1
     * repairname : 老王
     */

    private String content;
    private String repairmobile;
    private String address;
    private MaintainImageRescntBean rescnt;
    private String createtime;
    private int rescode;
    private String repairid;
    private String repairtype;
    private String repairstatus;
    private String repairname;

    public String getRepairstatus() {
        return repairstatus;
    }

    public void setRepairstatus(String repairstatus) {
        this.repairstatus = repairstatus;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRepairmobile() {
        return repairmobile;
    }

    public void setRepairmobile(String repairmobile) {
        this.repairmobile = repairmobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public MaintainImageRescntBean getRescnt() {
        return rescnt;
    }

    public void setRescnt(MaintainImageRescntBean rescnt) {
        this.rescnt = rescnt;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public int getRescode() {
        return rescode;
    }

    public void setRescode(int rescode) {
        this.rescode = rescode;
    }

    public String getRepairid() {
        return repairid;
    }

    public void setRepairid(String repairid) {
        this.repairid = repairid;
    }

    public String getRepairtype() {
        return repairtype;
    }

    public void setRepairtype(String repairtype) {
        this.repairtype = repairtype;
    }

    public String getRepairname() {
        return repairname;
    }

    public void setRepairname(String repairname) {
        this.repairname = repairname;
    }

    public static class MaintainImageRescntBean {
        /**
         * imageurl : http://bmob-cdn-6779.b0.upaiyun.com/2016/10/13/2fc925f916e74c90a9986cc0f0033eee.jpg
         * imageid : 2
         */

        private List<MaintainImageListBean> list;

        public List<MaintainImageListBean> getList() {
            return list;
        }

        public void setList(List<MaintainImageListBean> list) {
            this.list = list;
        }

        public static class MaintainImageListBean {
            private String imageurl;
            private int imageid;

            public String getImageurl() {
                return imageurl;
            }

            public void setImageurl(String imageurl) {
                this.imageurl = imageurl;
            }

            public int getImageid() {
                return imageid;
            }

            public void setImageid(int imageid) {
                this.imageid = imageid;
            }
        }
    }
}
