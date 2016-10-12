package sinia.com.smartmart.bean;

import java.io.Serializable;

/**
 * Created by 忧郁的眼神 on 2016/10/12.
 */

public class JsonBean implements Serializable {

    private int rescode;
    private Object rescnt;

    public int getRescode() {
        return rescode;
    }

    public void setRescode(int rescode) {
        this.rescode = rescode;
    }

    public Object getRescnt() {
        return rescnt;
    }

    public void setRescnt(Object rescnt) {
        this.rescnt = rescnt;
    }
}
