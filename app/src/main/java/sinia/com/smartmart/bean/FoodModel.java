package sinia.com.smartmart.bean;

/**
 * Created by 忧郁的眼神 on 2016/10/28 0028.
 */

public class FoodModel {

    private String id;
    private String name;
    private double price;
    private int count;
    private boolean isChecked = false;//是否加入过购物车

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
