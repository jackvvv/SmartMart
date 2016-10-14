package sinia.com.smartmart.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 忧郁的眼神 on 2016/10/12.
 */

public class MaintainListBean implements Serializable {

    private MaintainList rescnt;
    private int rescode;

    public int getRescode() {
        return rescode;
    }

    public void setRescode(int rescode) {
        this.rescode = rescode;
    }

    public MaintainList getRescnt() {
        return rescnt;
    }

    public void setRescnt(MaintainList rescnt) {
        this.rescnt = rescnt;
    }

    public class MaintainList {
        private List<MaintainBean> list;

        public List<MaintainBean> getList() {
            return list;
        }

        public void setList(List<MaintainBean> list) {
            this.list = list;
        }
    }

    public class MaintainBean {
        private String repairid;
        private String repairtype;
        private String repairstatus;
        private String content;
        private String createtime;

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

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }
    }
}
