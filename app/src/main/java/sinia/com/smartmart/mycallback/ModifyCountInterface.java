package sinia.com.smartmart.mycallback;

import android.view.View;

/**
 * Created by 忧郁的眼神 on 2016/8/12.
 */
public interface ModifyCountInterface {

    /**
     * 增加操作
     *
     * @param groupPosition 组元素位置
     * @param childPosition 子元素位置
     * @param showCountView 用于展示变化后数量的View
     * @param isChecked     子元素选中与否
     */
    public void doIncrease(int groupPosition, int childPosition,
                           View showCountView, boolean isChecked);

    /**
     * 删减操作
     *
     * @param groupPosition 组元素位置
     * @param childPosition 子元素位置
     * @param showCountView 用于展示变化后数量的View
     * @param isChecked     子元素选中与否
     */
    public void doDecrease(int groupPosition, int childPosition,
                           View showCountView, boolean isChecked);
}
