package sinia.com.smartmart.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 忧郁的眼神 on 2016/10/12.
 */

public class VillageListBean implements Serializable {

    private VillageList rescnt;
    private int rescode;

    public int getRescode() {
        return rescode;
    }

    public void setRescode(int rescode) {
        this.rescode = rescode;
    }

    public VillageList getRescnt() {
        return rescnt;
    }

    public void setRescnt(VillageList rescnt) {
        this.rescnt = rescnt;
    }

    public class VillageList {
        private List<VillageBean> list;

        public List<VillageBean> getList() {
            return list;
        }

        public void setList(List<VillageBean> list) {
            this.list = list;
        }
    }

    public class VillageBean {
        private String villageid;
        private String villagename;

        public String getVillageid() {
            return villageid;
        }

        public void setVillageid(String villageid) {
            this.villageid = villageid;
        }

        public String getVillagename() {
            return villagename;
        }

        public void setVillagename(String villagename) {
            this.villagename = villagename;
        }
    }
}
